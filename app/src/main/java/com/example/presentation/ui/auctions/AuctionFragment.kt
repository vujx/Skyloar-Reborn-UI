package com.example.presentation.ui.auctions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.R
import com.example.databinding.FragmentAuctionBinding


class AuctionFragment : Fragment(R.layout.fragment_auction) {

    private lateinit var binding: FragmentAuctionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_auction, null, false)
        return binding.root
    }
}
