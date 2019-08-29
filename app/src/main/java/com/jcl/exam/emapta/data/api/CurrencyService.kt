package com.jcl.exam.emapta.data.api

import com.jcl.exam.emapta.data.api.response.ConvertResponse
import com.jcl.exam.emapta.util.scheduler.AppSchedulerProvider
import io.reactivex.Single


class CurrencyService (private val apiInterface: ApiInterface, private val schedulerProvider: AppSchedulerProvider) {

    fun convert(amount: Double, fromCurrency: String, toCurrency: String): Single<ConvertResponse> {
        return apiInterface.convert(amount.toString(), fromCurrency, toCurrency)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
    }
}