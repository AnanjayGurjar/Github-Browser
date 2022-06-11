package com.ananjay.githubbrowser.models.commit

data class Verification(
    val payload: String,
    val reason: String,
    val signature: String,
    val verified: Boolean
)