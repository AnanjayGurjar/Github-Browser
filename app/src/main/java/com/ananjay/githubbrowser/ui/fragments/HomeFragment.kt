package com.ananjay.githubbrowser.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ananjay.githubbrowser.R
import com.ananjay.githubbrowser.adapters.ClickListener
import com.ananjay.githubbrowser.adapters.HomeFragmentAdapter
import com.ananjay.githubbrowser.database.MyRepoModel
import com.ananjay.githubbrowser.databinding.FragmentHomeBinding
import com.ananjay.githubbrowser.ui.viewmodels.RepositoryViewModel


class HomeFragment : Fragment() , ClickListener{
    private val TAG = "HomeFragment"
    private lateinit var binding: FragmentHomeBinding

    var ownerName = ""
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(RepositoryViewModel::class.java)
    }
    lateinit var  adapter : HomeFragmentAdapter

    var currList = ArrayList<MyRepoModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var name = arguments?.getString("name")
        var repoName = arguments?.getString("repoName")
        if(name != null && repoName !=null){
            viewModel.getRepo(name.toString().trim(), repoName.toString().trim())
        }



        adapter = HomeFragmentAdapter(currList, this){
            val name = currList[it].repoName
            val ownerName = currList[it].ownerName
            var bundle = bundleOf("name" to name, "ownerName" to ownerName)

            findNavController().navigate(
                R.id.action_homeFragment_to_detailFragment, bundle
            )
        }
        initRecycler()

        binding.btnAddNewRepo.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addRepoFragment)
        }

        viewModel.allOfflineRepos.observe(requireActivity()){
            currList.clear()
            currList.addAll(it)
            Log.d(TAG, "onViewCreated: ${currList}")
            adapter.notifyDataSetChanged()
        }

    }

    private fun initRecycler(){


        binding.apply {
            rvFavoriteRepo.adapter = adapter
            rvFavoriteRepo.layoutManager = LinearLayoutManager(requireContext())
        }

//        lifecycleScope.launchWhenStarted {
//            viewModel.allOfflineRepos.observe(requireActivity()){
//                currList.addAll(it)
//                Log.d(TAG, "initRecycler: ${currList.toString()}")
//                adapter.notifyDataSetChanged()
//                launch(Dispatchers.Main){
//                    updateView()
//                }
//            }
//        }

    }

    private fun shareRepository(repoName: String, repoDesc: String?, repoUrl: String){


        val shareIntent = Intent(Intent.ACTION_SEND)

        shareIntent.putExtra(Intent.EXTRA_TEXT, repoName)
        shareIntent.putExtra(Intent.EXTRA_TEXT, repoDesc)
        shareIntent.putExtra(Intent.EXTRA_TEXT, repoUrl)
        shareIntent.type = "text/plain"

        startActivity(Intent.createChooser(shareIntent, "Share the Repository using..."))
    }

    override fun onShareClicked(repoModel: MyRepoModel) {
        shareRepository(repoModel.repoName, repoModel.repoDescription, repoModel.repoUrl)
    }


}