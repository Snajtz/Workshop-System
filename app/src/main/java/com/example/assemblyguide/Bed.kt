package com.example.assemblyguide

import java.io.Serializable
import java.util.Collections.list

class Bed : Serializable{
    var id : Int
    var artNr = ""
    var notes = ""
    var images: List<String>

    constructor(id : Int, artNr : String, notes : String, images : String = ""){
        this.id = id
        this.artNr = artNr
        this.notes = notes
        this.images = images.split("%")

    }

    fun stringifyImages(): String{
        var result = ""
        var first = true
        images.forEach(){
            if (first) {
                result.plus(it)
            }
            else{
                result.plus("%$it")
            }
        }
        return result
    }
}