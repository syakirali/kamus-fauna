package com.example.sam_pc.mydict

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main2.*
import okhttp3.OkHttpClient
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.textChangedListener

class MainActivity : AppCompatActivity() {
    private var mode: Int = 1;
    private lateinit var adp: item_list_adapter
    private var res: MutableList<kamus> = mutableListOf()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        val isFirstTime = getPreferences(Context.MODE_PRIVATE).getBoolean("isFirstRun", true)

        super.onCreate(savedInstanceState)
        Stetho.initializeWithDefaults(this)

        OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()


        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (isFirstTime) {
            getPreferences(Context.MODE_PRIVATE).edit().putBoolean("isFirstRun", false).apply()
            migrateDatabase()
        }
        recycler.layoutManager = LinearLayoutManager(this)
        adp = item_list_adapter(mode,res)
        recycler.adapter = adp
        getKamus()
        input_search.textChangedListener {
            afterTextChanged {
                val temp: Collection<kamus>

                if (mode == 1)
                    temp = res.filter {
                        it.nama_hewan.decapitalize().contains(input_search.text.toString().decapitalize())
                    }
                else
                    temp = res.filter {
                        it.nama_latin.decapitalize().contains(input_search.text.toString().decapitalize())
                    }
                adp = item_list_adapter(mode,temp)
                recycler.adapter = adp
            }
        }
        b_switch.onClick {
            mode = (mode+1)%2
            if (mode == 1) {
                label1.text = resources.getString(R.string.label_nama_binatang)
                label2.text = resources.getString(R.string.label_nama_latin)
            } else {
                label1.text = resources.getString(R.string.label_nama_latin)
                label2.text = resources.getString(R.string.label_nama_binatang)
            }
            var temp = res.filter {
                it.nama_hewan.contains(input_search.text.toString())
            }
            adp = item_list_adapter(mode,temp)
            recycler.adapter = adp
        }

    }

    fun getKamus(){
        val res2 = database.use {
            select(kamus.KAMUS).parseList(classParser<kamus>())
        }
        res.clear()
        res.addAll(res2)
        adp.notifyDataSetChanged()
    }

    fun migrateDatabase(){
        applicationContext.assets.open("databasenamalatinfauna.csv").bufferedReader().use {
            val iterator = it.lineSequence().iterator()
            while(iterator.hasNext()) {
                val line = iterator.next()
                val data = line.split(",")
                database.use {
                    insert(kamus.KAMUS,
                        kamus.nama_hewan to data[1],
                        kamus.nama_latin to data[2]
                    )
                }
            }
        }

    }

}
