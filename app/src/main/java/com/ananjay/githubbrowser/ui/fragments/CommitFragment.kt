package com.ananjay.githubbrowser.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ananjay.githubbrowser.R
import com.ananjay.githubbrowser.adapters.CommitFragmentAdapter
import com.ananjay.githubbrowser.adapters.IssueFragmentAdapter
import com.ananjay.githubbrowser.databinding.FragmentAddRepoBinding
import com.ananjay.githubbrowser.databinding.FragmentCommitBinding
import com.ananjay.githubbrowser.models.commit.CommitModel
import com.ananjay.githubbrowser.ui.viewmodels.RepositoryViewModel

class CommitFragment : Fragment() {

    private lateinit var binding: FragmentCommitBinding

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(RepositoryViewModel::class.java)
    }
    lateinit var adapter: CommitFragmentAdapter
    var commits = ArrayList<CommitModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommitBinding.inflate(inflater, container, false)
        adapter = CommitFragmentAdapter(commits, requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var repoName = arguments?.getString("name")
        var ownerName = arguments?.getString("ownerName")
        var branchName = arguments?.getString("branch")

        binding.tvBranch.text = branchName
        initRecycler()
        if(repoName != null && ownerName != null && branchName != null){
            viewModel.getAllCommits(ownerName, repoName, branchName)
            viewModel.allCommits.observe(requireActivity()){
                commits.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }


    }

    private fun initRecycler(){
        binding.apply {
            rvCommits.adapter = adapter
            rvCommits.layoutManager = LinearLayoutManager(requireContext())
        }
    }


}