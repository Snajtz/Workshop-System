package com.example.assemblyguide

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity

class ImageListViewAdapter(private val activity: MainActivity, imageList: List<Bitmap>?) : BaseAdapter(){
    private var imageList = ArrayList<Bitmap>()

    init {
        if (imageList != null) {
            this.imageList = imageList as ArrayList
        }
        else{
            this.imageList = ArrayList()
        }

    }


    override fun getCount(): Int {
        return imageList.size
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
        vi = inflater.inflate(R.layout.imagelistviewitem, null)
        val imageView : ImageView = vi.findViewById(R.id.listItemImage)

/*
        vi.setOnClickListener{
            val intent = Intent(activity, ObjectActivity::class.java)
            intent.putExtra("bed", bedsList[i])
            activity.startActivity(intent)
        }
*/

        return vi
    }
}