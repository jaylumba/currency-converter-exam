package com.jcl.exam.emapta.data.db.dao

import androidx.room.*
import com.jcl.exam.emapta.data.db.entities.Account
import com.jcl.exam.emapta.data.db.entities.AccountCurrency
import io.reactivex.Flowable

@Dao
interface AccountDao {

    @Query("SELECT * FROM tblAccount LEFT JOIN tblCurrency ON tblCurrency.id = tblAccount.currency_id ORDER BY tblAccount.id")
    fun selectAll(): Flowable<List<AccountCurrency>>

    @Query("SELECT * FROM tblAccount  LEFT JOIN tblCurrency ON tblCurrency.id = tblAccount.currency_id WHERE tblAccount.id = :id")
    fun select(id: Long): AccountCurrency

    @Query("SELECT * FROM tblAccount WHERE currency_id = :id")
    fun selectByCurrencyId(id: Long): Account

    @Query("SELECT * FROM tblAccount WHERE id = :currencyId")
    fun getBalance(currencyId: Long): Account

    @Query("DELETE FROM tblAccount")
    fun truncate()

    @Transaction
    fun replace(samples: List<Account>) {
        val firstId: Long = samples.firstOrNull()?.id ?: run {
            0L
        }

        val lastId = samples.lastOrNull()?.id ?: run {
            Long.MAX_VALUE
        }

        deleteRange(firstId, lastId)
        insert(samples)
    }

    @Query("DELETE FROM tblAccount WHERE id BETWEEN :firstId AND :lastId")
    fun deleteRange(firstId: Long, lastId: Long)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(t: Account): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ts: List<Account>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIgnore(entity: Account) : Long

    @Update
    fun update(entity: Account)

    @Transaction
    fun insertOrUpdate(entity: Account) {
        if (insertIgnore(entity) == -1L) {
            update(entity)
        }
    }
}