package com.example.raitha.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.raitha.data.local.AppDatabase
import com.example.raitha.data.local.TipDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        lateinit var database: AppDatabase

        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "raitha_database"
        ).addCallback(
            object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    applicationScope.launch {
                        database.tipDao().insertAll(AppDatabase.seedTips())
                    }
                }
            }
        ).build().also {
            database = it
        }
    }

    @Provides
    fun provideTipDao(appDatabase: AppDatabase): TipDao {
        return appDatabase.tipDao()
    }
}
