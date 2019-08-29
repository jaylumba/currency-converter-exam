package com.jcl.exam.emapta.data.db.entities

import androidx.room.*

@Entity(tableName = "tblAccount")
data class Account(@ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long,
                   @ColumnInfo(name = "currency_id") val currencyId: Long,
                   @ColumnInfo(name = "amount") var amount: Double) {
    constructor(currencyId: Long, amount: Double) : this(0,currencyId,amount)
}