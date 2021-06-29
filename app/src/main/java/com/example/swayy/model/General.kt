package com.example.swayy.model

class General {
    private var title: String = ""
    private var description: String = ""
    private var website: String = ""
    private var location: String = ""
    private var category: String = ""
    private var image: String = ""
    private var uid: String = ""
    private var time: String = ""
    private var storeid: String = ""

    constructor()
    constructor(
        title: String,
        description: String,
        website: String,
        location: String,
        category: String,
        image: String,
        uid: String,
        time: String,
        storeid: String
    ) {
        this.title = title
        this.description = description
        this.website = website
        this.location = location
        this.category = category
        this.image = image
        this.uid = uid
        this.time = time
        this.storeid = storeid
    }
    fun getStoreid(): String
    {
        return storeid
    }

    fun setStoreid(storeid: String)
    {
        this.storeid = storeid
    }
    fun getCategory(): String
    {
        return category
    }

    fun setCategory(category: String)
    {
        this.category = category
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
    fun getDescription(): String
    {
        return description
    }

    fun setDescription(description: String)
    {
        this.description = description
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
    fun getWebsite(): String
    {
        return website
    }

    fun setWebsite(website: String)
    {
        this.website = website
    }
}