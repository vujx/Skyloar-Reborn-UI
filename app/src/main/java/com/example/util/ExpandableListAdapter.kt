package com.example.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.example.R


class ExpandableListAdapter(
    private val ctx: Context,
    private val listDataHeader: List<String>,
    private val listDataChild: HashMap<String, List<String>>
) : BaseExpandableListAdapter() {

    override fun getChild(groupPosition: Int, childPosititon: Int): Any {
        listDataChild[listDataHeader[groupPosition]]?.let {
            return it[childPosititon]
        }
        return 0
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    @SuppressLint("InflateParams")
    override fun getChildView(
        groupPosition: Int, childPosition: Int,
        isLastChild: Boolean, convertView: View?, parent: ViewGroup?
    ): View {
        var chgConvertView: View? = convertView
        val childText = getChild(groupPosition, childPosition) as String
        if (chgConvertView == null) {
            val infalInflater = ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            chgConvertView = infalInflater.inflate(R.layout.list_navdrawer, null)
        }
        val txtListChild = chgConvertView
            ?.findViewById(R.id.lblListItem) as TextView
        txtListChild.text = childText
        return chgConvertView
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        listDataChild[listDataHeader[groupPosition]]?.let {
            return it.size
        }
        return 0
    }

    override fun getGroup(groupPosition: Int): Any {
        return listDataHeader[groupPosition]
    }

    override fun getGroupCount(): Int {
        return listDataHeader.size
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    @SuppressLint("InflateParams")
    override fun getGroupView(
        groupPosition: Int, isExpanded: Boolean,
        convertView: View?, parent: ViewGroup?
    ): View? {
        var chgConvertView: View? = convertView
        val headerTitle = getGroup(groupPosition) as String
        if (chgConvertView == null) {
            val inflate = ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            chgConvertView = inflate.inflate(R.layout.list_group_navdrawer, null)
        }
        val lblListHeader = chgConvertView?.let {
            it.findViewById(R.id.lblListHeader) as TextView
        }

        lblListHeader?.setTypeface(null, Typeface.BOLD)
        lblListHeader?.text = headerTitle
        return chgConvertView
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return groupPosition == 2
    }
}