package com.jcl.exam.emapta.data.db.entities

data class AccountCurrency(val id: Long,
                           val currency_id: Long,
                           val desc: String,
                           val symbol: String,
                           var amount: Double)