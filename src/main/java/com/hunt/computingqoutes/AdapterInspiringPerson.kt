package com.hunt.computingqoutes

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class AdapterInspiringPerson() : RecyclerView.Adapter<AdapterInspiringPerson.HolderPerson>() {

    private var context: Context? = null
    private var personList: ArrayList<InspiringPerson>? = null

    constructor(context: Context?, personList: ArrayList<InspiringPerson>?) : this() {
        this.context = context
        this.personList = personList
    }


    inner class HolderPerson(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var picIv: ImageView = itemView.findViewById(R.id.picIv)
        var nameTv: TextView = itemView.findViewById(R.id.tvName)
        var dobTv: TextView = itemView.findViewById(R.id.tvDob)
        var deathTv: TextView = itemView.findViewById(R.id.tvDeath)
        var descriptionTv: TextView = itemView.findViewById(R.id.tvDescripition)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderPerson {
        return HolderPerson(
            LayoutInflater.from(context).inflate(R.layout.item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: HolderPerson, position: Int) {
        val model = personList!![position]
        val id = model.id
        val name = model.name
        val image = model.image
        val dob = model.dob
        val death = model.death
        val description = model.qoute
        val qoute = model.description
        holder.nameTv.text = "Name: "+name
        holder.dobTv.text = "DOB: "+dob
        holder.deathTv.text = "Death Date: "+death
        holder.descriptionTv.text = "Description: "+description
        if (image == "null")
        {
            holder.picIv.setImageResource(R.drawable.ic_baseline_person_24)

        }
        else{
            holder.picIv.setImageURI(Uri.parse(image))
        }
        holder.itemView.setOnClickListener {
            Toast.makeText(context,"Quote: "+qoute,Toast.LENGTH_SHORT).show()
        }


    }

    override fun getItemCount(): Int {
        return personList!!.size
    }
}