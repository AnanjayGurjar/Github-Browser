package com.ananjay.githubbrowser.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ananjay.githubbrowser.R
import com.ananjay.githubbrowser.adapters.BranchFragmentAdapter
import com.ananjay.githubbrowser.adapters.IssueFragmentAdapter
import com.ananjay.githubbrowser.databinding.FragmentAddRepoBinding
import com.ananjay.githubbrowser.databinding.FragmentBranchBinding
import com.ananjay.githubbrowser.models.branch.BranchModel
import com.ananjay.githubbrowser.ui.viewmodels.RepositoryViewModel


class BranchFragment : Fragment() {

    private lateinit var binding: FragmentBranchBinding
    private lateinit var adapter: BranchFragmentAdapter
    private var branches = ArrayList<BranchModel>()
    var repoName: String? = null
    var ownerName: String? = null

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(RepositoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBranchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pageSetup()

        adapter = BranchFragmentAdapter(branches){
            var branchName = branches[it].name
            var bundle = bundleOf("name" to repoName, "ownerName" to ownerName, "branch" to branchName)
            findNavController().navigate(R.id.action_branchFragment_to_commitFragment, bundle)
        }

        binding.apply {
            rvBranches.adapter = adapter
            rvBranches.layoutManager = LinearLayoutManager(requireContext(), )
        }
    }

    private fun pageSetup(){
        repoName = arguments?.getString("name")
        ownerName = arguments?.getString("ownerName")
        if(ownerName != null && repoName != null){
            viewModel.getAllBranches(ownerName!!, repoName!!)
            viewModel.allBranches.observe(requireActivity()){
                branches.clear()
                branches.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }
    }
}