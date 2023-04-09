package com.example.githubuserapp.ui.follow

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.ui.main.MainActivity
import com.example.githubuserapp.databinding.FragmentFollowBinding
import com.example.githubuserapp.ui.detail.DetailActivity

class FollowFragment : Fragment() {
    companion object{
        const val TAB_TITLES = "tab_titles"
        const val GIT_FOLLOWER = "Followers"
        const val GIT_FOLLOWING = "Following"
        var USERNAME = "username"
    }

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[FollowViewModel::class.java]
        val userFollow = arguments?.getString(USERNAME)
        Log.d("userfollow", "onViewCreated: $userFollow")
        val userTab = arguments?.getString(TAB_TITLES)
        if (userTab == GIT_FOLLOWER){
            userFollow?.let {
                viewModel.getUserFollower(it)
            }
        }else if (userTab == GIT_FOLLOWING){
            userFollow?.let { viewModel.getUserFollowing(it) }
        }

        viewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        viewModel.listFollow.observe(viewLifecycleOwner){
            val adapter = FollowAdapter(it)
            binding.rvFollow.layoutManager = LinearLayoutManager(activity)
            binding.rvFollow.adapter = adapter
        }

        viewModel.error.observe(viewLifecycleOwner){
            Toast.makeText(context, "Data Not Found", Toast.LENGTH_SHORT).show()
            viewModel.doneToastError()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading){
            binding.loadingFollow.visibility = View.VISIBLE
        }else {
            binding.loadingFollow.visibility = View.GONE
        }

    }

}