package com.example.swayy.model

class Nunua {

    private var storeid: String = ""
    private var title: String = ""
    private var location: String = ""
    private var type: String = ""
    private var condition: String = ""
    private var description: String = ""
    private var price: String = ""
    private var image: String = ""
    private var uid: String = ""
    private var time: String = ""
    private var postid: String = ""

    constructor()
    constructor(
        storeid: String,
        title: String,
        location: String,
        type: String,
        condition: String,
        description: String,
        price: String,
        image: String,
        uid: String,
        time: String,
        postid:String
    ) {
        this.storeid = storeid
        this.title = title
        this.location = location
        this.type = type
        this.condition = condition
        this.description = description
        this.price = price
        this.image = image
        this.uid = uid
        this.time = time
        this.postid = postid
    }

    fun getPostid(): String{
        return postid
    }
    fun setPostid(postid: String)
    {
        this.postid = postid
    }
    fun getStoreid(): String
    {
        return storeid
    }

    fun setStoreid(storeid: String)
    {
        this.storeid = storeid
    }

    fun getTitle(): String
    {
        return title
    }

    fun setTitle(title: String)
    {
        this.title = title
    }
    fun getLocation(): String
    {
        return location
    }

    fun setLocation(location: String)
    {
        this.location = location
    }
    fun getType(): String
    {
        return type
    }

    fun setType(type: String)
    {
        this.type = type
    }
    fun getCondition(): String
    {
        return condition
    }

    fun setCondition(condition: String)
    {
        this.condition = condition
    }
    fun getDescription(): String
    {
        return description
    }

    fun setDescription(description: String)
    {
        this.description = description
    }
    fun getPrice(): String
    {
        return price
    }

    fun setPrice(price: String)
    {
        this.price = price
    }
    fun getImage(): String
    {
        return image
    }

    fun setImage(image: String)
    {
        this.image = image
    }
    fun getUid(): String
    {
        return uid
    }

    fun setUid(uid: String)
    {
        this.uid = uid
    }
    fun getTime(): String
    {
        return time
    }

    fun setTime(time: String)
    {
        this.time = time
    }
}