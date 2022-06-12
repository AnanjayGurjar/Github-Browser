package com.ananjay.githubbrowser.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ananjay.githubbrowser.R
import com.ananjay.githubbrowser.adapters.BranchFragmentAdapter
import com.ananjay.githubbrowser.adapters.IssueFragmentAdapter
import com.ananjay.githubbrowser.databinding.FragmentAddRepoBinding
import com.ananjay.githubbrowser.databinding.FragmentIssueBinding
import com.ananjay.githubbrowser.models.issue.IssueModel
import com.ananjay.githubbrowser.ui.viewmodels.RepositoryViewModel

class IssueFragment : Fragment() {

    private lateinit var binding: FragmentIssueBinding

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(RepositoryViewModel::class.java)
    }
    var issues = ArrayList<IssueModel>()
    lateinit var adapter: IssueFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIssueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var repoName = arguments?.getString("name")
        var ownerName = arguments?.getString("ownerName")
        adapter = IssueFragmentAdapter(issues, requireContext())
        initRecycler()
        if(ownerName != null && repoName != null){
            viewModel.getOpenIssues(ownerName, repoName)
            viewModel.allIssues.observe(requireActivity()){
                issues.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }



    }

    private fun initRecycler(){
        binding.apply {
            rvIssues.adapter = adapter
            rvIssues.layoutManager = LinearLayoutManager(requireContext())
        }
    }


}