package com.jcl.exam.emapta.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.jcl.exam.emapta.data.db.entities.Currency
import io.reactivex.Flowable

@Dao
interface CurrencyDao : BaseDao<Currency> {

    @Query("SELECT id FROM tblCurrency WHERE `desc` = :desc")
    fun getIdByDesc(desc: String): Long

    @Query("SELECT * FROM tblCurrency ORDER BY id")
    override fun selectAll(): Flowable<List<Currency>>

    @Query("SELECT * FROM tblCurrency WHERE id = :id")
    override fun select(id: Long): Flowable<Currency>

    @Query("DELETE FROM tblCurrency")
    override fun truncate()

    @Transaction
    fun replace(samples: List<Currency>) {
        val firstId: Long = samples.firstOrNull()?.id ?: run {
            0L
        }

        val lastId = samples.lastOrNull()?.id ?: run {
            Long.MAX_VALUE
        }

        deleteRange(firstId, lastId)
        insert(samples)
    }

    @Query("DELETE FROM tblCurrency WHERE id BETWEEN :firstId AND :lastId")
    fun deleteRange(firstId: Long, lastId: Long)
}