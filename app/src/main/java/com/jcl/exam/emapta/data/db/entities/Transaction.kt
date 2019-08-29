package com.jcl.exam.emapta.data.db.entities

import androidx.room.*
import java.util.*

@Entity(tableName = "tblTransaction")
data class Transaction(@ColumnInfo(name = "id", index = true) @PrimaryKey(autoGenerate = true) val id: Long,
                       @ColumnInfo(name = "from_currency_id") val fromCurrencyId: Long,
                       @ColumnInfo(name = "amount") val amount: Double,
                       @ColumnInfo(name = "to_currency_id") val toCurrencyId: Long,
                       @ColumnInfo(name = "convertedAmount") val convertedAmount: Double,
                       @ColumnInfo(name = "fee") val fee: Double,
                       @ColumnInfo(name = "transaction_date") val transactionDate: Date){
    constructor(fromCurrencyId: Long, amount: Double, toCurrencyId: Long,
                convertedAmount: Double, fee: Double, transactionDate: Date) :
            this(0, fromCurrencyId, amount, toCurrencyId, convertedAmount, fee, transactionDate)

}