package com.ananjay.githubbrowser.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RepositoryDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepo(repo: MyRepoModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun isnertRestaurants(repos: List<MyRepoModel>)

    @Query("SELECT * FROM repositories")
    fun getAllRepo () : LiveData<List<MyRepoModel>>
//    fun getAllRepo () : Flow<List<MyRepoModel>>

    @Query("DELETE FROM repositories")
    suspend fun deleteAllRepos()
}