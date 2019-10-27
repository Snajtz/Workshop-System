package com.example.assemblyguide

import java.io.Serializable

class Bed : Serializable{
    var id : Int
    var artNr = ""
    var notes = ""
    var images = ""

    constructor(id : Int, artNr : String, notes : String, images : String = ""){
        this.id = id
        this.artNr = artNr
        this.notes = notes
        this.images = images

    }
}