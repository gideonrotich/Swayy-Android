package com.example.swayy.model

class Saved {

    private var searchtext: String = ""
    private var uid: String = ""
    private var category = ""
    private var subcategory = ""

    constructor()
    constructor(searchtext: String, uid: String,category: String,subcategory: String) {
        this.searchtext = searchtext
        this.uid = uid
        this.category = category
        this.subcategory = subcategory
    }
    fun getCategory(): String
    {
        return category
    }

    fun getCategory(category: String)
    {
        this.category = category
    }
    fun getSubcategory(): String
    {
        return subcategory
    }

    fun getSubcategory(subcategory: String)
    {
        this.subcategory = subcategory
    }

    fun getSearchtext(): String
    {
        return searchtext
    }

    fun getSearchtext(searchtext: String)
    {
        this.searchtext = searchtext
    }

    fun getUid(): String
    {
        return uid
    }

    fun setUid(uid: String)
    {
        this.uid = uid
    }

}