package com.jcl.exam.emapta.base

import android.content.DialogInterface
import io.reactivex.annotations.NonNull

/**
 * Created by jaylumba on 05/16/2018.
 */

interface BaseView {

    fun isNetworkAvailable(): Boolean

    fun setLoadingIndicator(active: Boolean)

    fun showToastSuccess(message: String)

    fun showToastError(message: String)

    fun showToastWarning(message: String)

    fun showToastInfo(message: String)

    fun showToastNormal(message: String)

    fun setToolbarTitle(title: String)

    fun updateToolbarTitle(title: String)

    fun isValidEmail(email: String): Boolean

    fun getStringResource(stringId: Int): String

    fun showAlertDialog(title: String, @NonNull message: String
                        , @NonNull positiveText:String, @NonNull positiveClickListener: DialogInterface.OnClickListener
                        , @NonNull negativeText:String, @NonNull negaviteClickListener: DialogInterface.OnClickListener)

    fun showAlertDialog(title: String, @NonNull message: String, @NonNull positiveText:String, @NonNull positiveClickListener: DialogInterface.OnClickListener)

    fun dismissAlertDialog()
}
