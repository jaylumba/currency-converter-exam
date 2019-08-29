package com.jcl.exam.emapta.base

import android.content.DialogInterface
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

/**
 * Created by jaylumba on 05/16/2018.
 */
open class BaseFragment : Fragment(), BaseView {

    lateinit var baseActivity: BaseActivity
    lateinit var containerView: View
    lateinit var fragmentBitmat: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseActivity = activity as BaseActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setLoadingIndicator(active: Boolean) {
        baseActivity.setLoadingIndicator(active)
    }

    override fun showToastSuccess(message: String) {
        baseActivity.showToastSuccess(message)
    }

    override fun showToastError(message: String) {
        baseActivity.showToastError(message)
    }

    override fun showToastWarning(message: String) {
        baseActivity.showToastWarning(message)
    }

    override fun showToastInfo(message: String) {
        baseActivity.showToastInfo(message)

    }

    override fun showToastNormal(message: String) {
        baseActivity.showToastNormal(message)
    }

    override fun setToolbarTitle(title: String) {
        baseActivity.setToolbarTitle(title)
    }

    override fun updateToolbarTitle(title: String) {
        baseActivity.updateToolbarTitle(title)
    }

    override fun isNetworkAvailable(): Boolean {
        return baseActivity.isNetworkAvailable()
    }

    override fun isValidEmail(email: String): Boolean {
        return baseActivity.isValidEmail(email)
    }

    override fun getStringResource(stringId: Int): String {
        return baseActivity.getStringResource(stringId)
    }

    override fun showAlertDialog(title: String, message: String, positiveText: String,
                                 positiveClickListener: DialogInterface.OnClickListener,
                                 negativeText: String,
                                 negaviteClickListener: DialogInterface.OnClickListener) {
        baseActivity.showAlertDialog(title, message, positiveText, positiveClickListener,
                negativeText, negaviteClickListener)
    }

    override fun showAlertDialog(title: String, message: String, positiveText: String,
                                 positiveClickListener: DialogInterface.OnClickListener) {
        baseActivity.showAlertDialog(title, message, positiveText, positiveClickListener)
    }

    override fun dismissAlertDialog() {
        baseActivity.dismissAlertDialog()
    }
}