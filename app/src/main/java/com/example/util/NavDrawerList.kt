package com.algebra.soccernewtry.navdrawer

import android.view.View
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.R
import com.example.util.ExpandableListAdapter

class NavDrawerList(private val activity: AppCompatActivity) {

    private var listAdapter: ExpandableListAdapter? = null
    private var expListView: ExpandableListView? = null
    private var listDataHeader: MutableList<String>? = null
    private var listDataChild: HashMap<String, List<String>>? = null

    fun setUpValues() {
        expListView = activity.findViewById<View>(R.id.lvExp) as ExpandableListView
        prepareListData()
        listAdapter = listDataHeader?.let {
            listDataChild?.let { it1 ->
                ExpandableListAdapter(
                    activity,
                    it,
                    it1
                )
            }
        }

        expListView!!.setAdapter(listAdapter)
    }

    fun prepareListData() {
        listDataHeader = ArrayList()
        listDataChild = HashMap()

        (listDataHeader as ArrayList<String>).add("Auctions")
        (listDataHeader as ArrayList<String>).add("Statistics")
        (listDataHeader as ArrayList<String>).add("LeaderBoards")

        val auctions: MutableList<String> = ArrayList()
        auctions.add("Auctions")
        val stat: MutableList<String> = ArrayList()
        stat.add("Statistics")

        val leaderboards: MutableList<String> = ArrayList()
        leaderboards.add("1vs1")
        leaderboards.add("2vs2")
        leaderboards.add("1P PvE")
        leaderboards.add("2P PvE")
        leaderboards.add("4P PvE")
        leaderboards.add("12P PvE")

        listDataChild!![(listDataHeader as ArrayList<String>)[0]] = auctions
        listDataChild!![(listDataHeader as ArrayList<String>)[1]] = stat
        listDataChild!![(listDataHeader as ArrayList<String>)[2]] = leaderboards

        expListView?.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
            when (listDataChild!![listDataHeader!![groupPosition]]!![childPosition]) {
                "Auctions" -> {

                }
            }
            false
        }
    }
}
