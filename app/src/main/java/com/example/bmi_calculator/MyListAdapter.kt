package com.example.bmi_calculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.bmi_calculator.databinding.MyListItemsBinding

class MyListAdapter(var list:ArrayList<UserData>) : BaseAdapter() {
    override fun getCount(): Int {
       return  list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var myView = LayoutInflater.from(parent!!.context).inflate(R.layout.my_list_items,parent,false)
        var bindingList = MyListItemsBinding.bind(myView)
        var person = list[position]
        bindingList.Name.text= person.name
        bindingList.mobile.text = person.mobile
        return myView
    }
}