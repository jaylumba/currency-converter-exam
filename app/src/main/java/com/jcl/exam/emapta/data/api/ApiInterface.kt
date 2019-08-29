package com.jcl.exam.emapta.data.api

import com.jcl.exam.emapta.data.api.response.ConvertResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by jaylumba on 05/16/2018.
 */

interface ApiInterface {
    @GET("currency/commercial/exchange/{fromAmount}-{fromCurrency}/{toCurrency}/latest")
    fun convert(@Path("fromAmount") fromAmount: String,
                @Path("fromCurrency") fromCurrency: String,
                @Path("toCurrency") toCurrency: String): Single<ConvertResponse>
}
