package com.ananjay.githubbrowser.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "repositories",  indices = arrayOf(Index(value = ["repoName"], unique = true)))
data class MyRepoModel (

    val repoName: String,
    val repoDescription: String?,
    val ownerName: String,
    val repoUrl: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
    )