package com.example.presentation.ui.doc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.databinding.FragmentDocBinding
import com.example.presentation.ui.doc.model.Doc

class DocFragment : Fragment() {

  private var _binding: FragmentDocBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentDocBinding.inflate(inflater, container, false)

    binding.docAuction.bind(Doc("Auction", "Docs #1", "Click here to open docs in new tab."))
    binding.docStat.bind(Doc("Statistics", "Docs #2", "Click here to open docs in new tab."))
    binding.docLeader.bind(Doc("Leaderboards", "Docs #3", "Click here to open docs in new tab."))
    binding.docInfo.bind(Doc("Info", "Docs #4", "Click here to open docs in new tab."))
    return binding.root
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }
}
