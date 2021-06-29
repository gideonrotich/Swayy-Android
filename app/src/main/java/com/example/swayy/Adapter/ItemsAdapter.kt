package com.example.swayy.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.swayy.CustomLoginFragment
import com.example.swayy.R
import com.example.swayy.categories.saledetailFragment
import com.example.swayy.message.LatestMessagesActivity
import com.example.swayy.model.Items
import com.example.swayy.settings.saveditemsActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class ItemsAdapter(private var mContext: Context,
                   private  var mItem: List<Items>,
                   private var isFragment: Boolean = false): RecyclerView.Adapter<ItemsAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsAdapter.ViewHolder {
        val view  = LayoutInflater.from(mContext).inflate(R.layout.sale_item_layout, parent, false)
        return ItemsAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mItem.size
    }

    override fun onBindViewHolder(holder: ItemsAdapter.ViewHolder, position: Int) {
        val item = mItem[position]
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
        holder.itemView.setOnLongClickListener(View.OnLongClickListener {
            Toast.makeText(mContext,"Product clicked successfully", Toast.LENGTH_LONG).show()
            true
        })


        holder.savejobbutton.setOnClickListener {
            holder.savejobbutton.setImageResource(R.drawable.ic_baseline_star_24)
            Toast.makeText(mContext,"Product Saved successfully", Toast.LENGTH_LONG).show()

            val category = item.getCategory()
            val subcategory = item.getSubcategory()
            val title = item.getTitle()
            val location = item.getLocation()
            val type = item.getType()
            val condition = item.getCondition()
            val description = item.getDescription()
            val price = item.getPrice()
            val image = item.getImage()
            val uid = item.getUid()
            val time = item.getTime()
            val postid = item.getPostid()

            saveInfo(category,subcategory,title,location,type,condition,description,price,image,uid,time,postid)

        }
    }

    private fun saveInfo(
        category: String,
        subcategory: String,
        title: String,
        location: String,
        type: String,
        condition: String,
        description: String,
        price: String,
        image: String,
        uid: String,
        time: String,
        postid: String
    ) {
        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val jobsRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("New").child(currentUserID)
        val timee = System.currentTimeMillis().toString()

        val userMap = HashMap<String, Any>()
        userMap["category"] = category
        userMap["subcategory"] = subcategory
        userMap["title"] = title
        userMap["location"] = location
        userMap["type"] = type
        userMap["condition"] = condition
        userMap["description"] = description
        userMap["price"] = price
        userMap["image"] = image
        userMap["uid"] = uid
        userMap["time"] = time
        userMap["postid"] = postid
        userMap["userid"] = currentUserID
        userMap["timee"] = timee

        jobsRef.child(timee).setValue(userMap)

    }

    class ViewHolder(@NonNull itemView : View): RecyclerView.ViewHolder(itemView)
    {
        var imagelogo: ImageView = itemView.findViewById(R.id.dia)
        var saletext: TextView = itemView.findViewById(R.id.diatext)
        var salelocation: TextView = itemView.findViewById(R.id.diatextlocation)
        var saleprice: TextView = itemView.findViewById(R.id.diatextprice)
        var savejobbutton:ImageView = itemView.findViewById(R.id.ukweli)
    }

}

