package com.jcl.exam.emapta.ui.converter.mvp;

import com.jcl.exam.emapta.base.BaseView
import com.jcl.exam.emapta.data.db.entities.Currency


interface ConverterContract {
    interface View : BaseView {
        fun setListeners()
        fun initDropdowns(currencies: List<Currency>)
        fun displaySuccess(message: String)
    }

    interface Presenter {
        fun onLoad()
        fun onConvert(amount: String, sourceCurrency: Currency?, destinationCurrency: Currency?)
    }
}