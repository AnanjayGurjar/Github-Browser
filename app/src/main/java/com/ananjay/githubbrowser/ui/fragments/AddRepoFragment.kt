package com.ananjay.githubbrowser.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ananjay.githubbrowser.R
import com.ananjay.githubbrowser.database.MyRepoModel
import com.ananjay.githubbrowser.databinding.FragmentAddRepoBinding
import com.ananjay.githubbrowser.databinding.FragmentHomeBinding
import com.ananjay.githubbrowser.ui.viewmodels.RepositoryViewModel

class AddRepoFragment : Fragment() {

    private val TAG = "AddRepoFragment"
    private lateinit var binding: FragmentAddRepoBinding
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
        binding = FragmentAddRepoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddRepo.setOnClickListener{
            val ownerName = binding.etOwner.editText?.text
            val repo = binding.etRepoName.editText?.text
            if(ownerName.isNullOrEmpty()){
                binding.etOwner.editText?.error = "please enter a valid owner name"
            }else if(repo.isNullOrEmpty()){
                binding.etOwner.editText?.error = "please enter a valid repo name"
            }else{
                val bundle = bundleOf("name" to ownerName.toString(), "repoName" to  repo.toString())
                findNavController().navigate(R.id.action_addRepoFragment_to_homeFragment, bundle)


//                viewModel.getRepo(ownerName.toString().trim(), repo.toString().trim())
//                findNavController().navigate(R.id.action_addRepoFragment_to_homeFragment)
            }
        }


    }


}