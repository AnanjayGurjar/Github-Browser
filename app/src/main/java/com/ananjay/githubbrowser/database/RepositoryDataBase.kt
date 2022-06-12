package com.ananjay.githubbrowser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [MyRepoModel::class], version = 4,exportSchema = false )
abstract class RepositoryDataBase : RoomDatabase() {

    abstract fun repoDao(): RepositoryDao

    companion object {
        @Volatile
        private var instance: RepositoryDataBase? = null

        fun getDataBase(context: Context) = instance ?: synchronized(this) {
            Room.databaseBuilder(
                context.applicationContext,
                RepositoryDataBase::class.java,
                "repositories"
            ).fallbackToDestructiveMigration().build().also { instance = it }
        }
    }
}