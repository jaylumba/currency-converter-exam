package com.jcl.exam.emapta.ui.converter

import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.jcl.exam.emapta.R
import com.jcl.exam.emapta.base.BaseActivity
import com.jcl.exam.emapta.data.db.entities.Currency
import com.jcl.exam.emapta.ui.converter.mvp.ConverterContract
import com.jcl.exam.emapta.ui.converter.mvp.ConverterPresenter
import com.jcl.exam.emapta.util.onClick
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_converter.*
import kotlinx.android.synthetic.main.include_app_bar.*
import javax.inject.Inject



class ConverterActivity : BaseActivity(), ConverterContract.View {

    @Inject
    lateinit var presenter: ConverterPresenter
    private lateinit var currencies: List<Currency>
    private var sourceCurrency: Currency? = null
    private var destinationCurrency: Currency? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_converter)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.title_convert_currency)
        presenter.onLoad()
    }

    override fun initDropdowns(currencies: List<Currency>){
        this.currencies = currencies
        val adapter = CustomAdapter(this,
                R.layout.item_currency, R.id.title, currencies)
        spnSourceCurrency.adapter = adapter
        spnDestinationCurrency.adapter = adapter
    }

    override fun setListeners() {
        spnSourceCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                sourceCurrency = currencies[position]
            }
        }

        spnDestinationCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                 destinationCurrency = currencies[position]
            }
        }

        btnConvert.onClick {
            presenter.onConvert(etAmount.text.toString(), sourceCurrency, destinationCurrency )
        }
    }

    override fun displaySuccess(message: String) {
        showAlertDialog(getString(R.string.convert_success), message, getString(R.string.ok),
                DialogInterface.OnClickListener { dialog, _ ->
                    dialog.dismiss()
                    setResult(Activity.RESULT_OK)
                    finishActivity()
                })
    }
}
