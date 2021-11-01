package com.example.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

fun <T> RecyclerView.withMultipleTypes(
  initial: List<T>,
  definition: MultiTypeAdapter<T>.() -> Unit
) = MultiTypeAdapter(initial.toMutableList()).apply {
  definition(this)
}.let {
  this.adapter = it
  it
}

typealias MultiAdapter<T> = RecyclerView.Adapter<MultiTypeAdapter<T>.SimpleHolder>

class MultiTypeAdapter<T>(internal val items: MutableList<T>) :
  RecyclerView.Adapter<MultiTypeAdapter<T>.SimpleHolder>() {

  object YouForgotToDefineAnythingException :
    IllegalStateException("You got to define something the adapter can bind to")

  private var onItem: (T) -> ComponentBuilder = {
    ComponentBuilder(0) { t, i ->
      throw YouForgotToDefineAnythingException
    }
  }

  inner class ComponentBuilder(
    val layout: Int,
    val bind: View.(item: T, index: Int) -> Unit
  )

  @InternalMultitypeSyntax
  fun showView(
    layout: Int,
    bind: View.(item: T, index: Int) -> Unit
  ) = ComponentBuilder(layout, bind).also {
  }

  @InternalMultitypeSyntax
  inline fun showView(layout: Int, crossinline bind: View.(item: T) -> Unit) =
    ComponentBuilder(layout) { it, _ ->
      bind(it)
    }

  @InternalMultitypeSyntax
  inline fun showView(layout: Int, crossinline bind: View.() -> Unit) =
    ComponentBuilder(layout) { it, _ ->
      bind()
    }

  @DslMarker
  annotation class InternalMultitypeSyntax

  @InternalMultitypeSyntax
  fun forItem(onItem: (T) -> ComponentBuilder) {
    this.onItem = onItem
    notifyDataSetChanged()
  }

  val list: List<T> = items.toList()

  fun update(items: List<T>) {
    this.items.clear()
    this.items.addAll(items)
    notifyDataSetChanged()
  }

  override fun getItemId(position: Int): Long {
    return items[position].hashCode().toLong()
  }

  override fun getItemViewType(position: Int): Int = onItem(items[position]).layout

  inner class SimpleHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SimpleHolder(
    LayoutInflater.from(parent.context).inflate(viewType, parent, false)
  )

  override fun getItemCount(): Int = items.size

  override fun onBindViewHolder(holder: SimpleHolder, position: Int) {
    onItem(items[position]).bind(holder.itemView, items[position], position)
  }
}
