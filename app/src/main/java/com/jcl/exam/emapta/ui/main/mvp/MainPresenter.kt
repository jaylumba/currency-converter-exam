package com.jcl.exam.emapta.ui.main.mvp;

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jcl.exam.emapta.base.BasePresenter
import com.jcl.exam.emapta.data.db.dao.AccountDao
import com.jcl.exam.emapta.data.mapper.toDomain
import com.jcl.exam.emapta.ui.main.MainActivity
import com.jcl.exam.emapta.util.scheduler.SchedulerProvider
import io.reactivex.Observable
import timber.log.Timber


class MainPresenter(private val schedulerProvider: SchedulerProvider, view: MainContract.View,
                    private val accountDao: AccountDao) :
        BasePresenter<MainContract.View>(view), MainContract.Presenter {

    private val TAG = MainActivity::class.java.simpleName
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
        view.initAccountsRecyclerView()
    }

    override fun getAccounts() {
        addDisposable(accountDao.selectAll()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .map { it.map { account -> account.toDomain() } }
                .subscribe {
                    view.displayAccounts(it.filter { account ->  account.amount > 0})
                })
    }

    override fun onButtonConvert() {
        view.goToConverterActivity()
    }
}