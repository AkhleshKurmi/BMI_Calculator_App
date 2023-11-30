package com.example.bmi_calculator

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import com.example.bmi_calculator.databinding.ActivitySavedHistoryBinding
import com.example.bmi_calculator.databinding.DialogDataViewBinding

class SavedHistory : AppCompatActivity(), AdapterView.OnItemClickListener,
    AdapterView.OnItemLongClickListener {
    lateinit var bindingSaved: ActivitySavedHistoryBinding
    lateinit var myListAdapter: MyListAdapter
    lateinit var myDbAdapter: MyDbAdapter
    lateinit var details: UserData
     var rowId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingSaved = ActivitySavedHistoryBinding.inflate(layoutInflater)
        setContentView(bindingSaved.root)

        myDbAdapter = MyDbAdapter(this)

        myListAdapter = MyListAdapter(myDbAdapter.fetchData())

        bindingSaved.listView.adapter = myListAdapter

        bindingSaved.listView.onItemClickListener = this
        bindingSaved.listView.onItemLongClickListener = this

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_history, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onResume() {
        super.onResume()
        myListAdapter = MyListAdapter(myDbAdapter.fetchData())
        bindingSaved.listView.adapter = myListAdapter
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        details = parent!!.getItemAtPosition(position) as UserData

        var dialog = Dialog(this)
        var binding = DialogDataViewBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)
        var layoutManager = WindowManager.LayoutParams()
        layoutManager.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutManager.width = WindowManager.LayoutParams.MATCH_PARENT
        dialog.window!!.attributes = layoutManager
        dialog.show()
        binding.btnCancle.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                dialog.dismiss()
            }

        })
        binding.tvName.text = "Name    :  ${details.name}"
        binding.tvBmiStatus.text = "BMI Status    :  ${details.BmiStatus}"
        binding.tvBmiValue.text = "BMI Value     :  ${details.BmiValue}"
        binding.tvHeight.text = "Height     :  ${details.height}"
        binding.tvWeight.text = "Weight     :  ${details.weight}"
        binding.tvMobile.text = "Mobile     :  ${details.mobile}"
    }

    override fun onItemLongClick(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ): Boolean {
        details = myListAdapter.getItem(position) as UserData
        rowId = details.id
        registerForContextMenu(bindingSaved.listView)
        return false
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {


        when (item.itemId) {

            R.id.delete -> {

                myDbAdapter.deleteSingleRow(rowId)
                myListAdapter = MyListAdapter(myDbAdapter.fetchData())
                bindingSaved.listView.adapter = myListAdapter


            }
        }
        return super.onContextItemSelected(item)
    }
}