package com.example.assemblyguide

class Bed {

    var artNr = ""
    var notes = ""
    var images = ""

    constructor(artNr : String, notes : String, images : String = ""){
        this.artNr = artNr
        this.notes = notes
        this.images = images

    }
}