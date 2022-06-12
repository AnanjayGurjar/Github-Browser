package com.ananjay.githubbrowser.remote

import com.ananjay.githubbrowser.models.branch.BranchModel
import com.ananjay.githubbrowser.models.commit.CommitModel
import com.ananjay.githubbrowser.models.issue.IssueModel
import com.ananjay.githubbrowser.models.repository.RepositoryModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetroiftService {
    @GET("{owner}/{repo}")
    suspend fun getRepository(
        @Path("owner") ownerName: String,
        @Path("repo") repoName: String
    ) : Response<RepositoryModel>

    @GET("{owner}/{repo}/branches")
    suspend fun getAllBranches(
        @Path("owner") ownerName: String,
        @Path("repo") repoName: String
    ) : Response<List<BranchModel>>

    @GET("{owner}/{repo}/commits?sha=main")
    suspend fun getAllCommits(
        @Path("owner") ownerName: String,
        @Path("repo") repoName: String,
        @Query("sha") branch: String
    ) : Response<List<CommitModel>>

    @GET("{owner}/{repo}/issues?state=open")
    suspend fun getOpenIssues(
        @Path("owner") ownerName: String,
        @Path("repo") repoName: String
    ) : Response<List<IssueModel>>
}