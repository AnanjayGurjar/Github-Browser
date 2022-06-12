package com.ananjay.githubbrowser.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ananjay.githubbrowser.R
import com.ananjay.githubbrowser.databinding.FragmentAddRepoBinding
import com.ananjay.githubbrowser.databinding.FragmentDetailBinding
import com.ananjay.githubbrowser.ui.viewmodels.RepositoryViewModel

class DetailFragment : Fragment() {

    private val TAG = "DetailFragment"

    private lateinit var binding: FragmentDetailBinding
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
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var repoName = arguments?.getString("name")
        var ownerName = arguments?.getString("ownerName")

        if(repoName != null && ownerName != null){
            viewModel.getRepo(ownerName = ownerName,repoName =  repoName)
            viewModel.myRepo.observe(requireActivity()){
                binding.tvRepoName.text = it.name
                binding.tvRepoDescription.text = it.description
                binding.btnIssues.text = "Issues(${it.open_issues_count})"
            }
        }


        binding.btnIssues.setOnClickListener {
            var bundle = bundleOf("name" to repoName, "ownerName" to ownerName)
            findNavController().navigate(R.id.action_detailFragment_to_issueFragment, bundle)
        }
        binding.btnBranches.setOnClickListener {
            var bundle = bundleOf("name" to repoName, "ownerName" to ownerName)
            findNavController().navigate(R.id.action_detailFragment_to_branchFragment, bundle)
        }
    }

}