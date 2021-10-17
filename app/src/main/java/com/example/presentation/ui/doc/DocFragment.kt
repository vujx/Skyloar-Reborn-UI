package com.example.presentation.ui.doc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.R
import com.example.databinding.FragmentDocBinding
import com.example.presentation.ui.BaseFragment
import com.example.presentation.ui.doc.model.Doc

class DocFragment : BaseFragment(R.layout.fragment_doc) {

  companion object {

    const val DOC_AUCTION = "http://185.203.18.254:7750/api/docs/auctions/"
    const val DOC_STAT = "http://185.203.18.254:7750/api/docs/stats/"
    const val DOC_LEADERBOARDS = "http://185.203.18.254:7750/api/docs/leaderboards/"
    const val DOC_INFO = "http://185.203.18.254:7750/api/docs/info/"
  }

  private var _binding: FragmentDocBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
    _binding = FragmentDocBinding.inflate(inflater, container, false)

    clickListeners()
    return binding.root
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }

  private fun clickListeners() {
    binding.firstCard.setOnClickListener {
      openBrowser(DOC_AUCTION)
    }

    binding.secondCard.setOnClickListener {
      openBrowser(DOC_STAT)
    }

    binding.thirdCard.setOnClickListener {
      openBrowser(DOC_LEADERBOARDS)
    }

    binding.fourthCard.setOnClickListener {
      openBrowser(DOC_INFO)
    }
  }
}
