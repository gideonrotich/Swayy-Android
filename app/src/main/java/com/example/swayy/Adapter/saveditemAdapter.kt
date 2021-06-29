package com.example.swayy.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.swayy.R
import com.example.swayy.categories.saledetailFragment
import com.example.swayy.model.Saveditem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class saveditemAdapter(private var mContext: Context,
                       private  var mSaveditem: List<Saveditem>,
                       private var isFragment: Boolean = false ): RecyclerView.Adapter<saveditemAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): saveditemAdapter.ViewHolder {
        val view  = LayoutInflater.from(mContext).inflate(R.layout.sale_item_layout, parent, false)
        return saveditemAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mSaveditem.size
    }

    override fun onBindViewHolder(holder: saveditemAdapter.ViewHolder, position: Int) {
        val item = mSaveditem[position]
//        Picasso.get().load(item.getImage()).into(holder.imagelogo)
        Glide.with(mContext)  //2
            .load(item.getImage()) //3
            .centerCrop() //4
            .into(holder.imagelogo)
        holder.saletext.text = item.getTitle()
        holder.salelocation.text = item.getLocation()
        holder.saleprice.text = "Ksh " + item.getPrice()

        holder.itemView.setOnClickListener(View.OnClickListener {
            val pref = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
            pref.putString("profileId",item.getTime())
            pref.putString("lawama",item.getCategory())
            pref.putString("giddy",item.getSubcategory())
            pref.apply()

            (mContext as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, saledetailFragment()).commit()
        })

        holder.savejobbutton.setImageResource(R.drawable.ic_baseline_delete_24)

        holder.savejobbutton.setOnClickListener(View.OnClickListener {
            val firebaseUser: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
            val ref = FirebaseDatabase.getInstance().reference.child("New").child(firebaseUser.uid)
            ref.removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful)
                {
                    Toast.makeText(mContext,"Job deleted successfully..", Toast.LENGTH_LONG).show()


                }
            }
        })
    }

    class ViewHolder(@NonNull itemView : View): RecyclerView.ViewHolder(itemView)
    {
        var imagelogo: ImageView = itemView.findViewById(R.id.dia)
        var saletext: TextView = itemView.findViewById(R.id.diatext)
        var salelocation: TextView = itemView.findViewById(R.id.diatextlocation)
        var saleprice: TextView = itemView.findViewById(R.id.diatextprice)
        var savejobbutton: ImageView = itemView.findViewById(R.id.ukweli)





    }

}