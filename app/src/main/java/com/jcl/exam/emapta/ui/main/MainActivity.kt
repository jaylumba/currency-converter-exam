package com.jcl.exam.emapta.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jcl.exam.emapta.R
import com.jcl.exam.emapta.base.BaseActivity
import com.jcl.exam.emapta.domain.Account
import com.jcl.exam.emapta.ui.converter.ConverterActivity
import com.jcl.exam.emapta.ui.main.mvp.MainContract
import com.jcl.exam.emapta.ui.main.mvp.MainPresenter
import com.jcl.exam.emapta.util.onClick
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : BaseActivity(), MainContract.View {

    @Inject
    lateinit var presenter: MainPresenter

    private val REQUEST_CODE = 101

    private var adapter: AccountAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_main)
        presenter.onLoad()
        presenter.getAccounts()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (REQUEST_CODE == requestCode && Activity.RESULT_OK == resultCode) {
            presenter.getAccounts()
        }
    }

    override fun setListeners() {
        btnConvert.onClick {
            presenter.onButtonConvert()
        }
    }

    override fun initAccountsRecyclerView() {
        adapter = AccountAdapter(arrayListOf(),
                object : AccountAdapter.OnItemSelected {
                    override fun onSelected(item: Account, pos: Int) {

                    }
                })
        rvAccounts.layoutManager = LinearLayoutManager(this)
        rvAccounts.adapter = adapter
    }

    override fun displayAccounts(accounts: List<Account>) {
        adapter?.setAccounts(accounts)
    }

    override fun goToConverterActivity() {
        moveToOtherActivityForResultWithSharedElements(ConverterActivity::class.java, REQUEST_CODE,
                btnConvert, "btnConvertTransition")
    }
}
