package com.example.presentation.ui.stat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.R
import com.example.databinding.FragmentStatBinding

class StatFragment : Fragment(R.layout.fragment_stat) {

    private lateinit var binding: FragmentStatBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stat, null, false)
        return binding.root
    }

}