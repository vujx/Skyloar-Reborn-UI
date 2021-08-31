package com.example.presentation.ui.leaderboards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.R
import com.example.databinding.FragmentLeaderboardBinding

class LeaderboardFragment : Fragment(R.layout.fragment_leaderboard) {

    private lateinit var binding: FragmentLeaderboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_leaderboard, null, false)
        return binding.root
    }

}