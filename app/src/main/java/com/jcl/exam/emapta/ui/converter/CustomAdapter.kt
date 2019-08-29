package com.jcl.exam.emapta.ui.converter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

import com.jcl.exam.emapta.R
import com.jcl.exam.emapta.data.db.entities.Currency

class CustomAdapter(context: Activity, resouceId: Int, textviewId: Int, list: List<Currency>) :
        ArrayAdapter<Currency>(context, resouceId, textviewId, list) {

    lateinit var flater: LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        return rowview(convertView, position)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return rowview(convertView, position)
    }

    private fun rowview(convertView: View?, position: Int): View {

        val rowItem = getItem(position)

        val holder: ViewHolder
        var rowview = convertView
        if (rowview == null) {
            holder = ViewHolder()
            flater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            rowview = flater.inflate(R.layout.item_currency, null, false)

            holder.tvCurrency = rowview!!.findViewById<View>(R.id.tvCurrency) as TextView
            holder.tvSymbol = rowview.findViewById<View>(R.id.tvSymbol) as TextView
            rowview.tag = holder
        } else {
            holder = rowview.tag as ViewHolder
        }

        holder.tvSymbol!!.text = rowItem!!.symbol
        holder.tvCurrency!!.text = rowItem.desc

        return rowview
    }

    private inner class ViewHolder {
        internal var tvCurrency: TextView? = null
        internal var tvSymbol: TextView? = null
    }
}