package com.jcl.exam.emapta.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jcl.exam.emapta.R
import com.jcl.exam.emapta.domain.Account
import com.jcl.exam.emapta.util.formatTwoDecimal

class AccountAdapter(
        private val data: ArrayList<Account>,
        private val onItemSelected: OnItemSelected
) : RecyclerView.Adapter<AccountAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_account, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvCurrency.text = data[position].currency
        holder.tvSymbol.text = data[position].symbol
        holder.tvAmount.text = data[position].amount.formatTwoDecimal()

        holder.itemView.setOnClickListener {
            onItemSelected.onSelected(data[position], position)
        }
    }

    fun setAccounts(accounts: List<Account>) {
        data.clear()
        data.addAll(accounts)
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCurrency = itemView.findViewById<TextView>(R.id.tvCurrency)!!
        val tvSymbol = itemView.findViewById<TextView>(R.id.tvSymbol)!!
        val tvAmount = itemView.findViewById<TextView>(R.id.tvAmount)!!
    }

    interface OnItemSelected {
        fun onSelected(item: Account, pos: Int)
    }
}