package com.jcl.exam.emapta.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "tblCurrency")
data class Currency(@ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long,
                  @ColumnInfo(name = "desc") val desc: String,
                  @ColumnInfo(name = "symbol") val symbol: String) {
    constructor(desc: String, symbol: String) : this(0, desc, symbol)
}