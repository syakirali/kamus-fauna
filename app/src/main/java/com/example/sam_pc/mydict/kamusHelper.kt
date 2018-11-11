package com.example.sam_pc.mydict

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper
import org.jetbrains.anko.db.TEXT
import org.jetbrains.anko.db.createTable
import org.jetbrains.anko.db.dropIndex

class kamusHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "kamus.db") {
    companion object {
        private var instance: kamusHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): kamusHelper {
            if (instance == null) {
                instance = kamusHelper(ctx.applicationContext)
            }
            return instance as kamusHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(kamus.KAMUS, true,
            kamus.nama_hewan to TEXT,
            kamus.nama_latin to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropIndex(kamus.KAMUS, true)
    }
}

val Context.database: kamusHelper
    get() = kamusHelper.getInstance(applicationContext)