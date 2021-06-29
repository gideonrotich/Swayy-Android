package com.example.swayy.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.swayy.R
import com.example.swayy.categories.saledetailFragment
import com.example.swayy.model.Items
import com.example.swayy.model.Store
import com.example.swayy.stores.storedetailFragment
import com.squareup.picasso.Picasso

class StoreAdapter(private var mContext: Context,
                   private  var mStore: List<Store>,
                   private var isFragment: Boolean = false) : RecyclerView.Adapter<StoreAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreAdapter.ViewHolder {
        val view  = LayoutInflater.from(mContext).inflate(R.layout.store_item_layout, parent, false)
        return StoreAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mStore.size
    }

    override fun onBindViewHolder(holder: StoreAdapter.ViewHolder, position: Int) {
        val store = mStore[position]
        Glide.with(mContext)  //2
            .load(store.getImage()) //3
            .centerCrop() //4
            .into(holder.storelogo)
        holder.coffee.text = store.getTitle()
        holder.tea.text = store.getLocation()

        holder.itemView.setOnClickListener(View.OnClickListener {
            val pref = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
            pref.putString("profileId", store.getTime().toString())
            pref.putString("lawama",store.getCategory())
            pref.putString("swayy",store.getUid())
            pref.putString("wamocho",store.getStoreid())

            pref.apply()

            (mContext as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, storedetailFragment()).commit()
        })
    }

    class ViewHolder(@NonNull itemView : View): RecyclerView.ViewHolder(itemView)
    {
        var storelogo: ImageView = itemView.findViewById(R.id.diame)
        var coffee: TextView = itemView.findViewById(R.id.ghafla)
        var tea: TextView = itemView.findViewById(R.id.mie)
    }

}