package com.example.bmi_calculator

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDbAdapter(var context: Context) {

    var DB_NAME = "BMIdb"
    var DB_VIRSON = 1
    var TABLE_NAME = "SavedBMI"
    var ID = "id"
    var NAME = "name"
    var MOBILE = "mobile"
    var HEIGHT = "height"
    var WEIGHT = "weight"
    var BMI_VALUE = "bmiValue"
    var BMI_STATUS ="bmiStatus"

    var CREATE_TABLE ="CREATE TABLE $TABLE_NAME($ID INTEGER PRIMARY KEY AUTOINCREMENT , $NAME TEXT, $MOBILE TEXT, $HEIGHT TEXT, $WEIGHT TEXT,$BMI_VALUE TEXT, $BMI_STATUS TEXT)"
     var myopenHelper :MyopenHelper =MyopenHelper(context)
    var sqLiteDatabase =myopenHelper.writableDatabase


    fun SaveData(name:String,mobile :String,height:String,weight:String,bmiValue:String,bmiStatus:String){
        var values = ContentValues()
        values.put(NAME,name)
        values.put(MOBILE,mobile)
        values.put(HEIGHT,height)
        values.put(WEIGHT,weight)
        values.put(BMI_VALUE,bmiValue)
        values.put(BMI_STATUS,bmiStatus)

        var rowId = sqLiteDatabase.insert(TABLE_NAME,null,values)

        if (rowId>0){
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show()
        }
    }

    fun fetchData() : ArrayList<UserData>{

        var allData = ArrayList<UserData>()

        var cursor = sqLiteDatabase.rawQuery("SELECT * FROM $TABLE_NAME",null,null)

        if (cursor.count > 0){
            cursor.moveToFirst()
            do {
              var id = cursor.getInt(0)
               var name = cursor.getString(1)
                var mobile = cursor.getString(2)
                var height = cursor.getString(3)
                var weight  = cursor.getString(4)
                var bmiValue = cursor.getString(5)
                var bmiStatus = cursor.getString(6)
               allData.add(UserData(id,name,mobile,height,weight,bmiValue,bmiStatus))

            }while (cursor.moveToNext())

        }
        else {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
        return allData
    }

    fun deleteSingleRow(rowId : Int){
        var deleteRow = sqLiteDatabase.delete(TABLE_NAME,"$ID = $rowId",null)
        if (deleteRow > 0 ){
            Toast.makeText(context, "$deleteRow Deleted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show()
        }
    }


    inner class MyopenHelper(context: Context) : SQLiteOpenHelper(context,DB_NAME,null,DB_VIRSON) {
        override fun onCreate(db: SQLiteDatabase?) {
           db!!.execSQL(CREATE_TABLE)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            TODO("Not yet implemented")
        }
    }
}