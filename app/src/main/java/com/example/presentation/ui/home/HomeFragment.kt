package com.example.presentation.ui.home

import android.graphics.Typeface
import android.icu.lang.UProperty
import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.databinding.FragmentHomeBinding
import com.example.presentation.ui.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.StringBuilder
import android.text.Spannable

import android.icu.lang.UProperty.INT_START
import android.text.style.StyleSpan

class HomeFragment : Fragment() {

  private val homeViewModel: HomeViewModel by viewModel()

  private var _binding: FragmentHomeBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
    _binding = FragmentHomeBinding.inflate(inflater, container, false)

    bind()

    return binding.root
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }

  private fun bind() {
    homeViewModel.getIntroText.observe(viewLifecycleOwner, { it ->
      val stringBuilder = SpannableStringBuilder()
      var counter = 0
      it.forEach { char ->
        if(char != '#') {
          stringBuilder.append(char)
        }
      }
      for(word in it.split(' ')) {

      }

      binding.tvIntroText.text = stringBuilder.toString()
    })
  }
}
