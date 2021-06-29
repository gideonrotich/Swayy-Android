package com.example.swayy.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Userr(val fullname: String, val email:String,val mobile:String,val uid:String,val image:String):
    Parcelable
{
    constructor(): this("","","","","")
}