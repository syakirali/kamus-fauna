package com.example.sam_pc.mydict

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "kamus")
data class KamusEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val nama_hewan: String,
    val nama_latin: String
)