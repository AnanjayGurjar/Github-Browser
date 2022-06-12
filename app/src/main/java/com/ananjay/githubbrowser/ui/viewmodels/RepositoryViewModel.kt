package com.ananjay.githubbrowser.ui.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RepositoryViewModel(application: Application) : AndroidViewModel(application) {
    private  val TAG = "RepositoryViewModel"

    private val repoDao =
        RepositoryDataBase.getDataBase(application).repoDao()


    private val repository = GithubRepoRepository(repoDao)

    var myRepo  =  MutableLiveData<RepositoryModel>()
    var allBranches = MutableLiveData<List<BranchModel>>()
    var allCommits = MutableLiveData<List<CommitModel>>()
    var allIssues = MutableLiveData<List<IssueModel>>()

    var allOfflineRepos  = repository.allRepos

    /********OPERATIONS ON REMOTE********/

    fun getRepo(ownerName: String, repoName: String)  {
        viewModelScope.launch {
            val response  = repository.getRepository(ownerName, repoName)
            if (response.isSuccessful && response.body() != null) {
                Log.d(TAG, "getRepo: ${response.body()!!.url}")
                myRepo.postValue(response.body())
                var repository = response.body()
                insertRepoToDb(MyRepoModel(repository!!.name, repository.description, repository.owner.login, repository.html_url))
            }
            else{
                Toast.makeText(getApplication(), "Entered search is not found", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "getRepo: ${response.errorBody()}")
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

    fun getAllCommits(ownerName: String, repoName: String, branch: String){
        viewModelScope.launch {
            val response = repository.getAllCommits(ownerName, repoName, branch)
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

    fun getAllRepoFromDb() : LiveData<List<MyRepoModel>> {
        return repository.getAllRepoFromDb()
    }

    fun deleteRepo(repoModelId: Int){
        viewModelScope.launch {
            repository.deleteFromDb(repoModelId)
            Log.d(TAG, "deleteRepo:   , is it deleting")
        }
    }

    fun delete(repo: MyRepoModel){
        viewModelScope.launch {
            repository.delete(repo)
        }
    }

}