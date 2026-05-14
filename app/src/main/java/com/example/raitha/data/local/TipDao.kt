package com.example.raitha.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TipDao {
    @Query("SELECT * FROM tips ORDER BY cropType, isKannada, id")
    fun getAllTips(): Flow<List<TipEntity>>

    @Query("SELECT * FROM tips WHERE cropType = :cropType ORDER BY isKannada, id")
    fun getTipsByCrop(cropType: String): Flow<List<TipEntity>>

    @Query("SELECT * FROM tips ORDER BY RANDOM() LIMIT 1")
    fun getDailyTip(): Flow<TipEntity?>

    @Query("SELECT * FROM tips WHERE cropType = :cropType ORDER BY isKannada, id LIMIT 1")
    suspend fun getFirstTipByCrop(cropType: String): TipEntity?

    @Query("SELECT * FROM tips ORDER BY isKannada, id LIMIT 1")
    suspend fun getFirstTip(): TipEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tips: List<TipEntity>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTip(tip: TipEntity)
}
