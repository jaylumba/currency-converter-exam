package com.jcl.exam.emapta.base

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import com.google.android.material.textfield.TextInputLayout
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.text.InputType
import android.util.Patterns
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.jcl.exam.emapta.R
import com.jcl.exam.emapta.progressdialog.ProgressDialogFragment
import es.dmoral.toasty.Toasty
import io.reactivex.annotations.NonNull
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/**
 * Created by jaylumba on 05/16/2018.
 */
open class
BaseActivity : AppCompatActivity(), BaseView {

    lateinit var alertDialog: AlertDialog
    lateinit var progressDialogFragment: ProgressDialogFragment

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    public override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    public override fun onPause() {
        super.onPause()
    }

    public override fun onResume() {
        super.onResume()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishActivity()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val view = currentFocus
        if (view != null && (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) && view is EditText && !view.javaClass.getName().startsWith("android.webkit.")) {
            val scrcoords = IntArray(2)
            view.getLocationOnScreen(scrcoords)
            val x = ev.rawX + view.left - scrcoords[0]
            val y = ev.rawY + view.top - scrcoords[1]
            if (x < view.left || x > view.right || y < view.top || y > view.bottom)
                (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(this.window.decorView.applicationWindowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun setLoadingIndicator(active: Boolean) {
        if (active) {
            progressDialogFragment = ProgressDialogFragment.newInstance()
            progressDialogFragment.isCancelable = false
            progressDialogFragment.show(supportFragmentManager,"custom progress")
        } else {
            progressDialogFragment.dismiss()
        }
    }

    override fun showToastSuccess(message: String) {
        Toasty.success(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showToastError(message: String) {
        Toasty.error(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showToastWarning(message: String) {
        Toasty.warning(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showToastInfo(message: String) {
        Toasty.info(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showToastNormal(message: String) {
        Toasty.normal(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun showAlertDialog(title: String, @NonNull message: String
                        , @NonNull positiveText:String, @NonNull positiveClickListener: DialogInterface.OnClickListener
                        , @NonNull negativeText:String, @NonNull negaviteClickListener: DialogInterface.OnClickListener) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setPositiveButton(positiveText, positiveClickListener)
        alertDialogBuilder.setNegativeButton(negativeText, negaviteClickListener)
        alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

   override fun showAlertDialog(title: String, @NonNull message: String, @NonNull positiveText:String, @NonNull positiveClickListener: DialogInterface.OnClickListener) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setPositiveButton(positiveText, positiveClickListener)
        alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun dismissAlertDialog() {
        alertDialog.dismiss()
    }

    /**
     * check network connection availability
     */
    override fun isNetworkAvailable(): Boolean {
        val networkTypes = intArrayOf(ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI)
        try {
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            for (networkType in networkTypes) {
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                if (activeNetworkInfo != null && activeNetworkInfo.type == networkType)
                    return true
            }
        } catch (e: Exception) {
            return false
        }
        return false
    }

    override fun setToolbarTitle(title: String) {
        supportActionBar!!.title = title
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun updateToolbarTitle(title: String) {
        supportActionBar!!.title = title
    }

    override fun getStringResource(stringId: Int): String {
        return resources.getString(stringId)
    }

    protected fun animateToLeft(activity: Activity) {
        activity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left)
    }

    protected fun animateToRight(activity: Activity) {
        activity.overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right)
    }

    protected fun setError(textInputLayout: TextInputLayout, message: String) {
        textInputLayout.error = message
        textInputLayout.isErrorEnabled = true
        textInputLayout.editText!!.requestFocus()
    }

    protected fun getImageById(id: Int): Drawable? {
        return ContextCompat.getDrawable(this, id)
    }

    fun setAsPasswordField(textInputLayout: TextInputLayout){
        val face = Typeface.createFromAsset(assets, CalligraphyConfig.get().fontPath)

        textInputLayout.editText?.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        textInputLayout.editText?.setTypeface(face, Typeface.NORMAL)
    }

    fun moveToOtherActivity(clz: Class<*>) {
        startActivity(Intent(this, clz))
        animateToLeft(this)
    }

    fun moveToOtherActivityForResult(clz: Class<*>, requestCode: Int) {
        startActivityForResult(Intent(this, clz), requestCode)
        animateToLeft(this)
    }

    fun moveToOtherActivityForResultWithSharedElements(clz: Class<*>, requestCode: Int, view: View, transitionName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, transitionName)
            startActivityForResult(Intent(this, clz), requestCode, options.toBundle())
        } else {
            startActivityForResult(Intent(this, clz), requestCode)
        }
        animateToLeft(this)
    }

    fun moveToOtherActivityWithSharedElements(clz: Class<*>, view: View, transitionName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, transitionName)
            startActivity(Intent(this, clz), options.toBundle())
        } else {
            startActivity(Intent(this, clz))
        }
    }

    fun moveToOtherActivityWithSharedElements(clz: Class<*>, options: ActivityOptionsCompat) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(Intent(this, clz), options.toBundle())
        } else {
            startActivity(Intent(this, clz))
        }
    }

    fun finishActivity(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition()
        } else {
            finish()
            animateToRight(this)
        }
    }
}