package com.narcissus.marketplace.model

sealed class DetailsAbout {
    class Type(val data:String):DetailsAbout()
    class Color(val data:String):DetailsAbout()
    class Material(val data:String):DetailsAbout()
    class Description(val data:String):DetailsAbout()
}
