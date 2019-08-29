package com.jcl.exam.emapta.customviews

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.jcl.exam.emapta.R
import com.jcl.exam.emapta.exceptions.ProgressLayoutException
import com.jcl.exam.emapta.interfaces.OnRetryListener
import com.wang.avi.AVLoadingIndicatorView

/**
 * Created by jaylumba on 05/16/2018.
 */

class ProgressLayout : FrameLayout {

    private var content: View? = null
    private var aviProgress: AVLoadingIndicatorView? = null
    private var llRetry: LinearLayout? = null
    private var onRetryListener: OnRetryListener? = null

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        inflateProgressBar()
    }

    private fun inflateProgressBar() {
        if (this.childCount == 0)
            throw ProgressLayoutException(
                    Exception(context.getString(R.string.msg_error_progresslayoutnochild)))
        else if (this.childCount > 1)
            throw ProgressLayoutException(
                    Exception(context.getString(R.string.msg_error_progresslayoutmultiplechild)))

        content = this.getChildAt(0)

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val progressRetry = inflater.inflate(R.layout.include_loading_retry,
                this, true)

        aviProgress = progressRetry.findViewById(R.id.avi_progress)
        llRetry = progressRetry.findViewById(R.id.ll_retry)
        llRetry!!.visibility = View.GONE
        aviProgress!!.hide()

        llRetry!!.setOnClickListener {
            if (onRetryListener != null)
                onRetryListener!!.retry()
        }
    }

    fun setOnRetryListener(onRetryListener: OnRetryListener) {
        this.onRetryListener = onRetryListener
    }

    fun showProgress() {
        llRetry!!.visibility = View.GONE
        aviProgress!!.visibility = View.VISIBLE
        content!!.visibility = View.INVISIBLE
        aviProgress!!.show()
    }

    fun showRetry() {
        llRetry!!.visibility = View.VISIBLE
        aviProgress!!.visibility = View.GONE
        content!!.visibility = View.INVISIBLE
        aviProgress!!.hide()
    }

    fun showContent() {
        llRetry!!.visibility = View.GONE
        aviProgress!!.visibility = View.GONE
        content!!.visibility = View.VISIBLE
        aviProgress!!.hide()
    }
}
