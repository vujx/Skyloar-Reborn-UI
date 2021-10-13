package com.example.presentation.ui.doc.adapter

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.databinding.ItemDocBinding
import com.example.presentation.ui.doc.model.Doc

class DocItem @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

  private val binding: ItemDocBinding = ItemDocBinding.inflate(
    LayoutInflater.from(context),
    this,
  )

  init {
    orientation = VERTICAL
  }

  fun bind(doc: Doc) {
    binding.tvDocTitle.text = doc.title
    binding.tvDocDescTitle.text = doc.titleDesc
    binding.tvDocDesc.text = doc.description
  }
}