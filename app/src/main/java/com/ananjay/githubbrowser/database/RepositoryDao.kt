package com.ananjay.githubbrowser.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RepositoryDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertRepo(repo: MyRepoModel)

    @Query("SELECT * FROM repositories")
    fun getAllRepo () : LiveData<List<MyRepoModel>>
}