package com.example.swayy.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.swayy.R
import com.example.swayy.Search.fuataFragment
import com.example.swayy.categories.saledetailFragment
import com.example.swayy.model.Saved

class SearchAdapter(private var mContext: Context,
                    private  var mSaved: List<Saved>,
                    private var isFragment: Boolean = false) : RecyclerView.Adapter<SearchAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ViewHolder {
        val view  = LayoutInflater.from(mContext).inflate(R.layout.search_item_layout, parent, false)
        return SearchAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) {
        val search = mSaved[position]
        holder.searchtextview.text = search.getSearchtext()

        holder.itemView.setOnClickListener(View.OnClickListener {
            val pref = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
            pref.putString("profileId",search.getSearchtext())
            pref.putString("swala",search.getCategory())
            pref.putString("lao",search.getSubcategory())

            pref.apply()
            (mContext as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, fuataFragment()).commit()


        })
    }

    override fun getItemCount(): Int {
        return mSaved.size
    }

    class ViewHolder(@NonNull itemView : View): RecyclerView.ViewHolder(itemView)
    {
        var searchtextview: TextView = itemView.findViewById(R.id.mwao)

    }
}