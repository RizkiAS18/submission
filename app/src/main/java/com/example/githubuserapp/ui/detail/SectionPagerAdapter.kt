package com.example.githubuserapp.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuserapp.ui.follow.FollowFragment

class SectionPagerAdapter(activity: AppCompatActivity):FragmentStateAdapter(activity) {

    var username: String =""
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()

        val gitTabs: String = when (position){
            0 -> FollowFragment.GIT_FOLLOWER
            1 -> FollowFragment.GIT_FOLLOWING
            else -> ""
        }
        fragment.arguments = Bundle().apply {
            putString(FollowFragment.TAB_TITLES, gitTabs)
            putString(FollowFragment.USERNAME, username)
        }
        return fragment
    }

}