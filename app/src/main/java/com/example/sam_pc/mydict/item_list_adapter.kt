package com.example.sam_pc.mydict

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.jetbrains.anko.AnkoLogger

class item_list_adapter(val mode: Int, val list:List<kamus>):RecyclerView.Adapter<item_list_adapter.ViewHolder>(), AnkoLogger {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): item_list_adapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: item_list_adapter.ViewHolder, position: Int) {
        holder.bind_item(list[position], mode)
    }

    class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
        private val res: TextView = view.findViewById(R.id.item_list)
        private val res3: TextView = view.findViewById(R.id.item_list3)

        fun bind_item(kamus: kamus, mode : Int) {
            if (mode == 1) {
                res.text = kamus.nama_hewan
                res3.text = kamus.nama_latin
            }
            else {
                res.text = kamus.nama_latin
                res3.text = kamus.nama_hewan
            }
        }
    }
}