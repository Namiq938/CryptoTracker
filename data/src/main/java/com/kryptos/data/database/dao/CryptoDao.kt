package com.kryptos.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kryptos.data.database.entities.CryptoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CryptoDao {

    @Query("select * from crypto_items")
    fun getAllData(): Flow<List<CryptoEntity>>

    @Query("select * from crypto_items where id == :itemId")
    fun getItemById(itemId: Long): Flow<CryptoEntity?>

    @Query("SELECT * FROM crypto_items ORDER BY updatedAt DESC LIMIT 1")
    fun getLastItem(): Flow<CryptoEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cryptoEntity: CryptoEntity)

    @Delete
    fun delete(cryptoEntity: CryptoEntity)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(cryptoEntity: CryptoEntity)
}