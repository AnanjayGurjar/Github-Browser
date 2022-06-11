package com.ananjay.githubbrowser.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositories")
data class MyRepoModel (
    val repoName: String,
    val repoDescription: String,
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0
    )