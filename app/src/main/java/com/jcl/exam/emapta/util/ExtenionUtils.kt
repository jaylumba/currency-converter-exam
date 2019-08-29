package com.jcl.exam.emapta.util

import android.graphics.Bitmap
import android.graphics.Matrix
import android.view.View
import com.jcl.exam.emapta.customviews.ProgressLayout
import com.jcl.exam.emapta.interfaces.OnRetryListener
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * Created by jaylumba on 30/08/2018.
 */
fun Bitmap.rotate(degrees: Float): Bitmap {
    val matrix = Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

internal infix fun View.onClick(function: () -> Unit) {
    setOnClickListener { function() }
}

internal infix fun ProgressLayout.onRetry(function: () -> Unit) {
    setOnRetryListener(object : OnRetryListener {
        override fun retry() {
            function()
        }
    })
}

fun Double.formatTwoDecimal(): String{
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.CEILING

    return String.format("%,.2f", df.format(this).toDouble())
}

fun String.formatTwoDecimal(): String{
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.CEILING

    return String.format("%,.2f", df.format(this.toDouble()).toDouble())
}

