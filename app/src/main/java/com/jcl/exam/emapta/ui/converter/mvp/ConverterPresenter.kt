package com.jcl.exam.emapta.ui.converter.mvp;

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jcl.exam.emapta.base.BasePresenter
import com.jcl.exam.emapta.data.api.CurrencyService
import com.jcl.exam.emapta.data.db.MyDatabase
import com.jcl.exam.emapta.data.db.entities.Currency
import com.jcl.exam.emapta.data.db.entities.Transaction
import com.jcl.exam.emapta.util.formatTwoDecimal
import com.jcl.exam.emapta.util.scheduler.SchedulerProvider
import io.reactivex.Observable
import timber.log.Timber
import java.util.*


class ConverterPresenter(private val schedulerProvider: SchedulerProvider, view: ConverterContract.View,
                         private val currencyService: CurrencyService,
                         private val myDatabase: MyDatabase) :
        BasePresenter<ConverterContract.View>(view), ConverterContract.Presenter {

    private val requestStateObserver = BehaviorRelay.createDefault(RequestState.IDLE)

    init {
        observeRequestState()
    }

    private fun publishRequestState(requestState: RequestState) {
        addDisposable(Observable.just(requestState)
                .observeOn(schedulerProvider.ui())
                .subscribe(requestStateObserver))
    }

    private fun observeRequestState() {
        addDisposable(requestStateObserver.subscribe({ requestState ->
            when (requestState) {
                RequestState.IDLE -> {
                }
                RequestState.LOADING -> view.setLoadingIndicator(true)
                RequestState.COMPLETE -> view.setLoadingIndicator(false)
                RequestState.ERROR -> view.setLoadingIndicator(false)
                else -> {
                }
            }
        }, { Timber.e(it) }))
    }

    override fun onLoad() {
        view.setListeners()
        addDisposable(myDatabase.currencyDao().selectAll()
                .doOnSubscribe { publishRequestState(RequestState.LOADING) }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe {
                    publishRequestState(RequestState.COMPLETE)
                    view.initDropdowns(it)
                })
    }

    override fun onConvert(amount: String, sourceCurrency: Currency?, destinationCurrency: Currency?) {
        when {
            amount.isEmpty()  || (amount.isNotEmpty() && amount.toDouble() <= 0) -> {
                view.showToastError("Please enter amount to convert")
                return
            }
            sourceCurrency == null -> {
                view.showToastError("Please select source currency")
                return
            }
            destinationCurrency == null -> {
                view.showToastError("Please select destination currency")
                return
            }

            sourceCurrency == destinationCurrency -> {
                view.showToastError("Please select a different destination currency")
                return
            }
        }

        addDisposable(currencyService.convert(amount.toDouble(), sourceCurrency!!.desc, destinationCurrency!!.desc)
                .doOnSubscribe { publishRequestState(RequestState.LOADING) }
                .subscribe({ response ->
                    publishRequestState(RequestState.COMPLETE)
                    val fee = computeFee(amount)
                    if (isSufficientBalance(amount, sourceCurrency, fee.toString())){
                        //Update data
                        updateDb(amount, sourceCurrency, response.amount, destinationCurrency, fee)
                        //Display message
                        showMessage(amount, sourceCurrency.desc, response.amount, response.currency)
                    }else{
                        view.showToastError("Insufficient balance")
                    }
                }, { error ->
                    publishRequestState(RequestState.ERROR)
                    Timber.e(error)
                    view.showToastError(error.localizedMessage)
                }))
    }

    private fun computeFee(amount: String): Double {
        //First five conversion for FREE
        return if (myDatabase.transactionDao().getTransactionCount() <= 5) {
            0.0
        } else {
            amount.toDouble() * 0.007
        }
    }

    private fun isSufficientBalance(amount: String, sourceCurrency: Currency, fee: String): Boolean {
        val balance = myDatabase.accountDao().getBalance(sourceCurrency.id).amount
        return balance >= amount.toDouble() + fee.toDouble()
    }

    private fun updateDb(amount: String, sourceCurrency: Currency, converterAmount: String, destinationCurrency: Currency, fee: Double){
        //Update source account
        val sourceAccount = myDatabase.accountDao().selectByCurrencyId(sourceCurrency.id)
        sourceAccount.amount = sourceAccount.amount - amount.toDouble() + fee
        myDatabase.accountDao().insertOrUpdate(sourceAccount)

        //Update destination account
        val destAccount = myDatabase.accountDao().selectByCurrencyId(destinationCurrency.id)
        destAccount.amount = destAccount.amount + converterAmount.toDouble()
        myDatabase.accountDao().insertOrUpdate(destAccount)

        //Update transaction history
        val transaction = Transaction(sourceCurrency.id, amount.toDouble(),
                destinationCurrency.id, converterAmount.toDouble(),
                fee, Calendar.getInstance().time)
        myDatabase.transactionDao().insert(transaction)
    }

    private fun showMessage(amount: String, sourceCurrency: String, destAmount: String, destCurrency: String) {
        val fee = computeFee(amount)
        val message = "You have converted ${amount.formatTwoDecimal()} $sourceCurrency to " +
                "${destAmount.formatTwoDecimal()} $destCurrency. " +
                "Commission Fee - ${fee.formatTwoDecimal()} $sourceCurrency."
        view.displaySuccess(message)
    }
}