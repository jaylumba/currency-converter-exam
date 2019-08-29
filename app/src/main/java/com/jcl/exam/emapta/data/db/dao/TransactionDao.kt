package com.jcl.exam.emapta.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Flowable

@Dao
interface TransactionDao : BaseDao<com.jcl.exam.emapta.data.db.entities.Transaction> {

    @Query("SELECT * FROM tblTransaction ORDER BY id")
    override fun selectAll(): Flowable<List<com.jcl.exam.emapta.data.db.entities.Transaction>>

    @Query("SELECT * FROM tblTransaction WHERE id = :id")
    override fun select(id: Long): Flowable<com.jcl.exam.emapta.data.db.entities.Transaction>

    @Query("SELECT COUNT(id) FROM tblTransaction")
    fun getTransactionCount(): Int

    @Query("DELETE FROM tblTransaction")
    override fun truncate()

    @Transaction
    fun replace(samples: List<com.jcl.exam.emapta.data.db.entities.Transaction>) {
        val firstId: Long = samples.firstOrNull()?.id ?: run {
            0L
        }

        val lastId = samples.lastOrNull()?.id ?: run {
            Long.MAX_VALUE
        }

        deleteRange(firstId, lastId)
        insert(samples)
    }

    @Query("DELETE FROM tblTransaction WHERE id BETWEEN :firstId AND :lastId")
    fun deleteRange(firstId: Long, lastId: Long)
}