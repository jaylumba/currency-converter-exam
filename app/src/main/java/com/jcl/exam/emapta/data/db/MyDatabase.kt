package com.jcl.exam.emapta.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jcl.exam.emapta.data.db.dao.AccountDao
import com.jcl.exam.emapta.data.db.dao.CurrencyDao
import com.jcl.exam.emapta.data.db.dao.TransactionDao
import com.jcl.exam.emapta.data.db.entities.Account
import com.jcl.exam.emapta.data.db.entities.Currency
import com.jcl.exam.emapta.data.db.entities.Transaction
import com.jcl.exam.emapta.util.DateConverter

/**
 * Database class with all of its dao classes
 */
@Database(entities = [Currency::class, Account::class, Transaction::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class MyDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao


    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = createInstance(context)
                }
                return INSTANCE!!
            }
        }

        private fun createInstance(context: Context) =
                Room.databaseBuilder(context.applicationContext, MyDatabase::class.java, "currencyConverter.db")
                        .allowMainThreadQueries()
                        .addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                Thread(Runnable { prepopulateDb(getInstance(context)) }).start()
                            }
                        })
                        .fallbackToDestructiveMigration()
                        .build()

        private fun prepopulateDb(db: MyDatabase) {

            val currencies = arrayListOf<Currency>()
            currencies.add(Currency("EUR", "€"))
            currencies.add(Currency("USD", "$"))
            currencies.add(Currency("JPY", "¥"))
            db.currencyDao().insert(currencies)

            val euroId = db.currencyDao().getIdByDesc("EUR")
            val usdId = db.currencyDao().getIdByDesc("USD")
            val jpyId = db.currencyDao().getIdByDesc("JPY")

            val accounts = arrayListOf<Account>()
            accounts.add(Account(euroId, 1000.0))
            accounts.add(Account(usdId, 0.0))
            accounts.add(Account(jpyId, 0.0))

            db.accountDao().insert(accounts)
        }
    }

}