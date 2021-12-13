package com.kryptos.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kryptos.data.database.dao.CryptoDao
import com.kryptos.data.database.entities.CryptoEntity
import com.kryptos.data.util.Constants

@Database(
    entities = [CryptoEntity::class], version = 2
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cryptoDao(): CryptoDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: run {
                Room.databaseBuilder(context, AppDatabase::class.java, Constants.COIN_TRACKER_DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build().also {
                        INSTANCE = it
                    }
            }
        }
    }

}