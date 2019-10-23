package com.example.assemblyguide

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class BedListViewAdapter(private val activity: MainActivity, bedsList: List<Bed>) : BaseAdapter(){
    private var bedsList = ArrayList<Bed>()

    init {
        this.bedsList = bedsList as ArrayList
    }

    override fun getCount(): Int {
        return bedsList.size
    }

    override fun getItem(i: Int): Any {
        return i
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    @SuppressLint("InflateParams", "ViewHolder")
    override fun getView(i: Int, convertView: View?, viewGroup: ViewGroup): View {
        var vi: View
        if (convertView != null) {
            var vi: View = convertView as View
        }
        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        vi = inflater.inflate(R.layout.listviewitem, null)
        val artNr : TextView = vi.findViewById(R.id.listItemArtNr)
        val genre : TextView = vi.findViewById(R.id.listItemNotes)
        artNr.text = bedsList[i].artNr
        genre.text = bedsList[i].notes
        return vi
    }
}