package com.ananjay.githubbrowser.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ananjay.githubbrowser.database.MyRepoModel
import com.ananjay.githubbrowser.database.RepositoryDao
import com.ananjay.githubbrowser.database.RepositoryDataBase
import com.ananjay.githubbrowser.models.branch.BranchModel
import com.ananjay.githubbrowser.models.commit.CommitModel
import com.ananjay.githubbrowser.models.issue.IssueModel
import com.ananjay.githubbrowser.models.repository.RepositoryModel
import com.ananjay.githubbrowser.repositories.GithubRepoRepository
import kotlinx.coroutines.launch

class RepositoryViewModel(application: Application) : AndroidViewModel(application) {


    private val repoDao =
        RepositoryDataBase.getDataBase(application).repoDao()


    private val repository = GithubRepoRepository(repoDao)

    var myRepo  =  MutableLiveData<RepositoryModel>()
    var allBranches = MutableLiveData<List<BranchModel>>()
    var allCommits = MutableLiveData<List<CommitModel>>()
    var allIssues = MutableLiveData<List<IssueModel>>()

    val allOfflineRepos  = repository.allRepos

    /********OPERATIONS ON REMOTE********/

    fun getRepo(ownerName: String, repoName: String)  {
        viewModelScope.launch {
            val response  = repository.getRepository(ownerName, repoName)
            if (response.isSuccessful) {
                myRepo.postValue(response.body())
            }
        }
    }

    fun getAllBranches(ownerName: String, repoName: String){
        viewModelScope.launch {
            val response = repository.getAllBranches(ownerName, repoName)
            if(response.isSuccessful && !response.body().isNullOrEmpty()){
                val myAllBranches = response.body()
                allBranches.postValue(myAllBranches!!)
            }
        }
    }

    fun getAllCommits(ownerName: String, repoName: String){
        viewModelScope.launch {
            val response = repository.getAllCommits(ownerName, repoName)
            if(response.isSuccessful && !response.body().isNullOrEmpty()){
                allCommits.postValue(response.body())
            }
        }
    }

    fun getOpenIssues(ownerName: String, repoName: String){
        viewModelScope.launch {
            val response = repository.getOpenIssues(ownerName, repoName)
            if(response.isSuccessful && !response.body().isNullOrEmpty()){
                allIssues.postValue(response.body())
            }
        }
    }


    /****** CRUD OPERATIONS ON DATABASE ********/
    fun insertRepoToDb(repo: MyRepoModel){
        viewModelScope.launch {
            repository.insertRepositoryInDb(repo)
        }
    }

//    fun getAllRepoFromDb(repo: MyRepoModel){
//        viewModelScope.launch {
//            var allrepos = repository.getAllRepoFromDb(repo)
//            allOfflineRepos.postValue(allrepos)
//        }
//    }

}