package com.ananjay.githubbrowser.repositories

import androidx.lifecycle.LiveData
import com.ananjay.githubbrowser.database.MyRepoModel
import com.ananjay.githubbrowser.database.RepositoryDao
import com.ananjay.githubbrowser.models.branch.BranchModel
import com.ananjay.githubbrowser.models.commit.CommitModel
import com.ananjay.githubbrowser.models.issue.IssueModel
import com.ananjay.githubbrowser.models.repository.RepositoryModel
import com.ananjay.githubbrowser.remote.RetrofitBuilder
import retrofit2.Response

class GithubRepoRepository(private val repositoryDao: RepositoryDao) {

    val retrofitInstance by lazy {
        RetrofitBuilder.apiService
    }
    val allRepos : LiveData<List<MyRepoModel>> = repositoryDao.getAllRepo()

    suspend fun getRepository(ownerName: String, repoName: String): Response<RepositoryModel>{
        return retrofitInstance.getRepository(ownerName, repoName)
    }

    suspend fun getAllBranches(ownerName: String, repoName: String): Response<List<BranchModel>>{
        return retrofitInstance.getAllBranches(ownerName, repoName)
    }

    suspend fun getAllCommits(ownerName: String, repoName: String): Response<List<CommitModel>>{
        return retrofitInstance.getAllCommits(ownerName, repoName)
    }

    suspend fun getOpenIssues(ownerName: String, repoName: String): Response<List<IssueModel>>{
        return retrofitInstance.getOpenIssues(ownerName, repoName)
    }

    suspend fun insertRepositoryInDb(repo: MyRepoModel){
        return repositoryDao.insertRepo(repo)
    }

    suspend fun getAllRepoFromDb(repo: MyRepoModel) : LiveData<List<MyRepoModel>>{
        return repositoryDao.getAllRepo()
    }
}