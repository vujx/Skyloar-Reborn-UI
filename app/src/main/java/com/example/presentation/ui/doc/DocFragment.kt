package com.example.presentation.ui.doc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.R
import com.example.databinding.FragmentDocBinding
import com.example.presentation.ui.BaseFragment
import com.example.presentation.ui.leaderboards.leaderboards.LeaderBoards
import com.example.presentation.ui.leaderboards.leaderboards.LeaderboardsAdapter

class DocFragment : BaseFragment(R.layout.fragment_doc) {

  private var _binding: FragmentDocBinding? = null
  private val binding get() = _binding!!
  private val adapter = LeaderboardsAdapter()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
    _binding = FragmentDocBinding.inflate(inflater, container, false)

    adapter.onDocClick = { docUrl ->
      openBrowser(docUrl)
    }

    setUpRecyclerView()
    return binding.root
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }

  private fun setUpRecyclerView() {
    binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    binding.recyclerView.adapter = adapter
    adapter.setList(
      listOf(
        LeaderBoards("Auctions", R.drawable.doc_pic, "Docs #1", "Click here to open docs in new tab."),
        LeaderBoards("Statistics", R.drawable.doc_pic, "Docs #2", "Click here to open docs in new tab."),
        LeaderBoards("Leaderboards", R.drawable.doc_pic, "Docs #3", "Click here to open docs in new tab."),
        LeaderBoards("Info", R.drawable.doc_pic, "Docs #4", "Click here to open docs in new tab."),
      )
    )
  }
}
