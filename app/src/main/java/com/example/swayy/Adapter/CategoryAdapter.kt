package com.example.swayy.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.swayy.R
import com.example.swayy.model.Category
import com.example.swayy.model.Fashion
import com.example.swayy.semifinal.finalFragment
import com.example.swayy.stores.storedetailFragment

class CategoryAdapter(private var mContext: Context,
                      private  var mCategory: List<Category>,
                      private var isFragment: Boolean = false): RecyclerView.Adapter<CategoryAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val view  = LayoutInflater.from(mContext).inflate(R.layout.categoryiten_layout, parent, false)
        return CategoryAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        val store = mCategory[position]

        holder.itemcategory.text = store.getCategoryitem()


        holder.itemView.setOnClickListener(View.OnClickListener {
            val pref = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
            pref.putString("nextId",store.getCategoryitem())
            pref.putString("profileId",store.getMaincategory())

            pref.apply()

            (mContext as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        })
    }

    override fun getItemCount(): Int {
        return mCategory.size
    }

    class ViewHolder(@NonNull itemView : View): RecyclerView.ViewHolder(itemView)
    {
        var itemcategory: TextView = itemView.findViewById(R.id.myitem)

    }

}