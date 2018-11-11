package com.example.sam_pc.mydict

data class kamus(val nama_hewan: String, val nama_latin: String){
    companion object {
        const val KAMUS: String = "KAMUS"
        const val nama_hewan: String = "nama_hewan"
        const val nama_latin: String = "nama_latin"
    }
}