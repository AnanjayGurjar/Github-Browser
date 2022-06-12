package com.ananjay.githubbrowser.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RepositoryDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepo(repo: MyRepoModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun isnertRestaurants(repos: List<MyRepoModel>)

    @Query("SELECT * FROM repositories")
    fun getAllRepo () : LiveData<List<MyRepoModel>>

    @Query("DELETE FROM repositories WHERE id = :repoModelId")
    suspend fun deleteRepo(repoModelId: Int)

    @Delete
    suspend fun delete(repo: MyRepoModel)
}