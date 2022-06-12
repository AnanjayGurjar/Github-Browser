package com.ananjay.githubbrowser.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ananjay.githubbrowser.R
import com.ananjay.githubbrowser.database.MyRepoModel
import com.ananjay.githubbrowser.databinding.FragmentDetailBinding
import com.ananjay.githubbrowser.ui.viewmodels.RepositoryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailFragment : Fragment() {

    private val TAG = "DetailFragment"

    private lateinit var binding: FragmentDetailBinding
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(RepositoryViewModel::class.java)
    }
    var url = ""
    lateinit var currRepo: MyRepoModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repoName = arguments?.getString("name")
        val ownerName = arguments?.getString("ownerName")
        val id = arguments?.getInt("id")


        if(repoName != null && ownerName != null){
            viewModel.getRepo(ownerName = ownerName,repoName =  repoName)
            viewModel.myRepo.observe(requireActivity()){
                binding.tvRepoName.text = it.name
                binding.tvRepoDescription.text = it.description
                binding.btnIssues.text = "Issues(${it.open_issues_count})"
                url = it.html_url
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

        binding.ivDelete.setOnClickListener {
            if(id != null){
                CoroutineScope(Dispatchers.IO).launch{
                    viewModel.deleteRepo(id)
                    Log.d(TAG, "onViewCreated: $id  , is it deleting")
                    launch(Dispatchers.Main){
                        findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
                    }
                }
            }else{
                Toast.makeText(requireContext(), "couldn't delete the data", Toast.LENGTH_SHORT).show()
            }

        }

        binding.ivOpenBrowser.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }
    }

}