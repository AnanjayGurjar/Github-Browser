package com.ananjay.githubbrowser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [MyRepoModel::class], version = 1,exportSchema = false )
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
/**fun getDatabase(context: Context): WordRoomDatabase {
// if the INSTANCE is not null, then return it,
// if it is, then create the database
return INSTANCE ?: synchronized(this) {
val instance = Room.databaseBuilder(
context.applicationContext,
WordRoomDatabase::class.java,
"word_database"
).build()
INSTANCE = instance
// return instance
instance
}
}
 **/