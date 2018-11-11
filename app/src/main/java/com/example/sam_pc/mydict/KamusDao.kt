package com.example.sam_pc.mydict

import android.arch.persistence.room.*

@Dao
interface KamusDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGender(kamus: KamusEntity)

    @Update
    fun updateGender(kamus: KamusEntity)

    @Delete
    fun deleteGender(kamus: KamusEntity)

    @Query("SELECT * FROM kamus WHERE nama_hewan == :name")
    fun cariLatin(name: String): List<KamusEntity>

    @Query("SELECT * FROM kamus WHERE nama_latin == :name")
    fun cariHewan(name: String): List<KamusEntity>

    @Query("SELECT * FROM kamus")
    fun getKamus(): List<KamusEntity>
}