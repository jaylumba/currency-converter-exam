package com.jcl.exam.emapta.ui.main.mvp;

import com.jcl.exam.emapta.base.BaseView
import com.jcl.exam.emapta.domain.Account


interface MainContract {
    interface View : BaseView {
        fun setListeners()
        fun initAccountsRecyclerView()
        fun displayAccounts(accounts: List<Account>)
        fun goToConverterActivity()
    }

    interface Presenter {
        fun onLoad()
        fun getAccounts()
        fun onButtonConvert()
    }
}