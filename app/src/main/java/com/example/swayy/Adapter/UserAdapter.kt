package com.example.swayy.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.swayy.R
import com.example.swayy.model.User
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_profile.view.*

class UserAdapter (private var mContext: Context,
                   private  var mUser: List<User>): RecyclerView.Adapter<UserAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        val view  = LayoutInflater.from(mContext).inflate(R.layout.activity_profile, parent, false)
        return UserAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mUser.size
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        val user = mUser[position]
        holder.fullnametextview.text = user.getFullname()
        holder.emailtextview.text = user.getEmail()
        holder.mobiletextview.text = user.getMobile()
        Picasso.get().load(user.getImage()).into(holder.im)
    }

    class ViewHolder(@NonNull itemView : View): RecyclerView.ViewHolder(itemView)
    {
        var fullnametextview: TextView = itemView.findViewById(R.id.etFirstNameone)
        var emailtextview: TextView = itemView.findViewById(R.id.etEmailone)
        var mobiletextview: TextView = itemView.findViewById(R.id.etLastNameone)
        var im:ImageView = itemView.findViewById(R.id.profile_image_settings)



    }


}