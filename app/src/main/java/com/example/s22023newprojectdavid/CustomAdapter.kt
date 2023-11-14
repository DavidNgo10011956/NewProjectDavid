package com.example.s22023newprojectdavid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView


class CustomAdapter(private  val context: Context, private val arrayList:ArrayList<Person> ): BaseAdapter() {
    private lateinit var tvName:TextView
    private lateinit var tvAddress:TextView
    private lateinit var tvMobile:TextView
    private lateinit var tvEmail:TextView
    private lateinit var ivImageFile:ImageView

    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, p1: View?, parent: ViewGroup): View {
        val cView = LayoutInflater.from(context).inflate(R.layout.row_item,parent,false)
        tvName=cView.findViewById(R.id.tvName)
        tvAddress=cView.findViewById(R.id.tvAddress)
        tvEmail=cView.findViewById(R.id.tvEmail)
        tvMobile=cView.findViewById(R.id.tvMobile)
        ivImageFile=cView.findViewById(R.id.ivImageFile)

        tvName.text=arrayList[position].name
        tvAddress.text=arrayList[position].address
        tvMobile.text=arrayList[position].mobile
        tvEmail.text=arrayList[position].email
        ivImageFile.setImageResource(context.resources.getIdentifier(arrayList[position].imageFile,"drawable","com.example.s22023newprojectdavid"))

        return cView

    }
}