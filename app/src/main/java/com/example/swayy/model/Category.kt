package com.example.swayy.model

class Category
{

    private var categoryitem: String = ""
    private var maincategory: String = ""

    constructor()

    constructor(categoryitem: String,maincategory: String) {
        this.categoryitem = categoryitem
        this.maincategory = maincategory
    }
    fun getCategoryitem(): String{
        return categoryitem
    }
    fun getMaincategory(): String{
        return maincategory
    }
    fun setCategoryitem(categoryitem: String){
        this.categoryitem = categoryitem
    }
    fun SetMaincategory(maincategory: String){
        this.maincategory = maincategory
    }
}