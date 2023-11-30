package com.example.bmi_calculator

import android.Manifest
import android.app.ActionBar.LayoutParams
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.bmi_calculator.databinding.ActivityMainBinding
import com.example.bmi_calculator.databinding.DialogConactUsBinding
import com.example.bmi_calculator.databinding.DialogSaveBinding
import java.nio.file.attribute.AclEntry.Builder
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var bindingMain: ActivityMainBinding
    lateinit var dialogToSave : AlertDialog
    lateinit var myDbAdapter: MyDbAdapter
    lateinit var height : String
    lateinit var weight :String
    lateinit var BmiValue : String
    lateinit var BmiStatus : String

    var spinnerItems = listOf<String>("Select a Unit","MKS","FPS")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)
        bindingMain.llFeetInches.visibility = View.GONE
        bindingMain.etHieght.visibility =View.GONE
        bindingMain.etWeight.visibility = View.GONE
        bindingMain.btnSubmit.visibility = View.GONE
        bindingMain.btnClear.visibility = View.GONE
        bindingMain.etWeightFps.visibility = View.GONE

        var arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, spinnerItems)

        bindingMain.spinner.adapter = arrayAdapter

        bindingMain.spinner.onItemSelectedListener = this
        myDbAdapter = MyDbAdapter(this)



    }



    fun Validation() {
        if (bindingMain.etHieght.text.toString().isEmpty()) {
            bindingMain.etHieght.error = "This Field is required"
        }
        if (bindingMain.etWeight.text.toString().isEmpty()) {
            bindingMain.etWeight.error = "This Field is required"
        }
        if (bindingMain.etHieght.text.toString().isNotEmpty() &&
            bindingMain.etWeight.text.toString().isNotEmpty()
        ) {
            Calculation()
            saveDialog()
            bindingMain.btnSubmit.visibility = View.GONE
            bindingMain.btnClear.visibility = View.VISIBLE

        }

    }

    fun Calculation() {
        height = "Cm - ${bindingMain.etHieght.text.toString()}"
        weight = "Kg - ${bindingMain.etWeight.text.toString()}"
        var Hieght = bindingMain.etHieght.text.toString().toFloat()
        var Weight = bindingMain.etWeight.text.toString().toFloat()
        var totleHieght = Hieght * Hieght


        var Bmi = (Weight / totleHieght) * 10000

        var RoundBmi = (Bmi * 10.0).roundToInt() / 10.0

        bindingMain.tvBmi.text = "BMI Value   :  $RoundBmi"
        BmiValue = "MKS- $RoundBmi"


        if (RoundBmi <= 18.5) {
            bindingMain.tvBmiStatus.text = "BMI Status  :  Underweight"
            BmiStatus = "Underweiht"
        } else if (RoundBmi > 18.5 && RoundBmi <= 24.9) {
            bindingMain.tvBmiStatus.text = "BMI Status  :  Normal"
            BmiStatus = "Normal"

        } else if (RoundBmi >= 25 && RoundBmi <= 29.9) {
            bindingMain.tvBmiStatus.text = "BMI Status  :  Overweight"
            BmiStatus = "Overweight"

        } else {
            bindingMain.tvBmiStatus.text = "BMI Status  :  Obesity"
            BmiStatus ="Obesity"

        }

    }


    fun ClearAllFeilds() {

        bindingMain.etHieght.text!!.clear()
        bindingMain.etWeight.text!!.clear()
        bindingMain.etHieghtFeet.text!!.clear()
        bindingMain.etHieghtInch.text!!.clear()
        bindingMain.etWeightFps.text!!.clear()
        bindingMain.tvBmi.text = ""
        bindingMain.tvBmiStatus.text = ""
    }
    fun ClearAllFeildsFps() {

        bindingMain.etHieghtFeet.text!!.clear()
        bindingMain.etHieghtInch.text!!.clear()
        bindingMain.etWeightFps.text!!.clear()
        bindingMain.tvBmi.text = ""
        bindingMain.tvBmiStatus.text = ""
    }
    fun ClearAllFeildsMks() {

        bindingMain.etHieght.text!!.clear()
        bindingMain.etWeight.text!!.clear()
        bindingMain.tvBmi.text = ""
        bindingMain.tvBmiStatus.text = ""
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.option_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item!!.itemId) {

            R.id.about_bmi -> {

                var intentAboutBMI = Intent(this@MainActivity, AboutBMI::class.java)
                startActivity(intentAboutBMI)
                //                Toast.makeText(this, "About BMI", Toast.LENGTH_SHORT).show()
            }

            R.id.bmi_chart -> {
//                Toast.makeText(this, "BMI Chart", Toast.LENGTH_SHORT).show()

                var intentBmiChart = Intent(this, BmiChart::class.java)
                startActivity(intentBmiChart)
            }

            R.id.about_developer -> {
//                Toast.makeText(this, "About_developer", Toast.LENGTH_SHORT).show()

                var intentAboutDev = Intent(this , AboutDeveloper::class.java)
                startActivity(intentAboutDev)
            }

            R.id.contact_us -> {

                contactUs()

            }

            R.id.history ->{
//                Toast.makeText(this, "History", Toast.LENGTH_SHORT).show()
                var intentHistory = Intent(this,SavedHistory::class.java)
                startActivity(intentHistory)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        var dialog = AlertDialog.Builder(this)
        dialog.setTitle("Exit")
        dialog.setMessage("Do you want to exit")
        dialog.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                finish()
//                Toast.makeText(this@MainActivity, "i am positive", Toast.LENGTH_SHORT).show()
            }

        })

        dialog.setNegativeButton("No", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
//                Toast.makeText(this@MainActivity, "i am negative", Toast.LENGTH_SHORT).show()
            }

        })


        dialog.setCancelable(false)
        var alertdialog = dialog.create()
        alertdialog.show()

    }

    fun contactUs() {
        var bindingDialog: DialogConactUsBinding = DialogConactUsBinding.inflate(layoutInflater)
        var contactDialog = Dialog(this@MainActivity)
        contactDialog.setContentView(bindingDialog.root)
        contactDialog.show()
        var layoutManager = WindowManager.LayoutParams()
        layoutManager.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutManager.width = WindowManager.LayoutParams.MATCH_PARENT

        contactDialog.window!!.attributes = layoutManager
        contactDialog.setCancelable(false)

        bindingDialog.btnCall.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                Calling()
                contactDialog.dismiss()

            }
        })
        bindingDialog.btnDail.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var intentDial = Intent(Intent.ACTION_DIAL, Uri.parse("tel: 9720207824"))
                startActivity(intentDial)
                contactDialog.dismiss()
            }
        })

        bindingDialog.closeBox.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                contactDialog.dismiss()
            }
        })
    }

    fun Calling() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            makeCall()
        } else {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.CALL_PHONE),
                101
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 101 && grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            makeCall()
        } else {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.CALL_PHONE),
                101
            )

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun makeCall() {
        var intentCall = Intent(Intent.ACTION_CALL, Uri.parse("tel: 9720207824"))
        startActivity(intentCall)
    }

    override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, p3: Long) {
        if (adapter!!.getItemAtPosition(position) == "MKS"){
            bindingMain.etHieght.hint = "Height (Cm)"
            bindingMain.etWeight.hint = "Weight (Kg)"
            bindingMain.etWeight.visibility = View.VISIBLE
            bindingMain.etHieght.visibility = View.VISIBLE
            bindingMain.etHieghtInch.visibility = View.GONE
            bindingMain.etHieghtFeet.visibility = View.GONE
            bindingMain.etWeightFps.visibility = View.GONE
            bindingMain.btnSubmit.visibility = View.VISIBLE
            bindingMain.btnClear.visibility = View.GONE
            ClearAllFeildsFps()
            bindingMain.btnSubmit.setOnClickListener {
                Validation()

            }
            bindingMain.btnClear.setOnClickListener {
                bindingMain.btnSubmit.visibility = View.VISIBLE
                bindingMain.btnClear.visibility = View.GONE
                ClearAllFeilds()
            }
        }
        else if (adapter!!.getItemAtPosition(position) == "FPS"){
            bindingMain.etWeight.visibility = View.GONE
            bindingMain.llFeetInches.visibility = View.VISIBLE
            bindingMain.etWeightFps.visibility = View.VISIBLE
            bindingMain.etHieght.visibility = View.GONE
            bindingMain.etHieghtFeet.visibility = View.VISIBLE
            bindingMain.etHieghtInch.visibility = View.VISIBLE
            bindingMain.btnSubmit.visibility = View.VISIBLE
            bindingMain.btnClear.visibility = View.GONE
            bindingMain.etHieghtFeet.hint = "Height (Feet)"
            bindingMain.etHieghtInch.hint = "Height (Inches)"
            bindingMain.etWeightFps.hint = "Weight (Pound)"
            ClearAllFeildsMks()
            bindingMain.btnSubmit.setOnClickListener {

                ValidationFPS()

            }
            bindingMain.btnClear.setOnClickListener {
                bindingMain.btnSubmit.visibility = View.VISIBLE
                bindingMain.btnClear.visibility = View.GONE
                ClearAllFeilds()
            }
        }
        else{
            bindingMain.llFeetInches.visibility = View.GONE
            bindingMain.etHieght.visibility =View.GONE
            bindingMain.etWeight.visibility = View.GONE
            bindingMain.btnSubmit.visibility = View.GONE
            bindingMain.btnClear.visibility = View.GONE
            bindingMain.etWeightFps.visibility = View.GONE
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    fun ValidationFPS() {
        if (bindingMain.etHieghtInch.text.toString().isEmpty()) {
            bindingMain.etHieghtInch.error = "This Field is required"
        }
        if (bindingMain.etHieghtFeet.text.toString().isEmpty()) {
            bindingMain.etHieghtFeet.error = "This Field is required"
        }

        if (bindingMain.etWeightFps.text.toString().isEmpty()) {
            bindingMain.etWeightFps.error = "This Field is required"
        }
        if (bindingMain.etHieghtInch.text.toString().isNotEmpty() &&
            bindingMain.etHieghtInch.text.toString().isNotEmpty() &&
            bindingMain.etWeightFps.text.toString().isNotEmpty()
        ) {
            CalculationFPS()
             saveDialog()
            bindingMain.btnSubmit.visibility = View.GONE
            bindingMain.btnClear.visibility = View.VISIBLE

        }

    }
     fun CalculationFPS(){

//         Toast.makeText(this, "i am FPS", Toast.LENGTH_SHORT).show()
         height = "Feet - ${bindingMain.etHieghtFeet.text.toString()}  Inch - ${bindingMain.etHieghtInch.text.toString()}"
         weight = "Pound - ${bindingMain.etWeightFps.text.toString()}"
            var weight = bindingMain.etWeightFps.text.toString().toFloat()
           var etheightFeet = bindingMain.etHieghtFeet.text.toString().toFloat()
         var etheightInches = bindingMain.etHieghtInch.text.toString().toFloat()
           var feetheight = (etheightFeet*12)
         var height = feetheight + etheightInches
         var totalHeightinch = (height*height)

         var Bmi = (weight/totalHeightinch)*703

         var RoundBmi = (Bmi * 10.0).roundToInt() / 10.0

         bindingMain.tvBmi.text = "BMI Value   :  $RoundBmi"

         BmiValue = "FPS- $RoundBmi"


         if (RoundBmi <= 18.5) {
             bindingMain.tvBmiStatus.text = "BMI Status  :  Underweight"
             BmiStatus = "Underweight"
         } else if (RoundBmi > 18.5 && RoundBmi <= 24.9) {
             bindingMain.tvBmiStatus.text = "BMI Status  :  Normal"
             BmiStatus = "Normal"

         } else if (RoundBmi >= 25 && RoundBmi <= 29.9) {
             bindingMain.tvBmiStatus.text = "BMI Status  :  Overweight"
             BmiStatus ="Overweight"

         } else {
             bindingMain.tvBmiStatus.text = "BMI Status  :  Obesity"
             BmiStatus = "Obesity"

         }

     }

    fun dialogToInsert(){
        var bindingSaveDialog : DialogSaveBinding = DialogSaveBinding.inflate(layoutInflater)
        var saveDialog = Dialog(this@MainActivity)
        saveDialog.setContentView(bindingSaveDialog.root)
        var layoutManager = WindowManager.LayoutParams()
        layoutManager.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutManager.width = WindowManager.LayoutParams.MATCH_PARENT
        saveDialog.setCancelable(false)
        saveDialog.window!!.attributes = layoutManager
        saveDialog.show()
        bindingSaveDialog.closeBox.setOnClickListener {
            saveDialog.dismiss()
        }

            bindingSaveDialog.btnSave.setOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {

                   if (bindingSaveDialog.etName.text!!.isEmpty()){
                       bindingSaveDialog.etName.error = "Required"
                   }
                    if (bindingSaveDialog.etMobile.text!!.isEmpty()){
                        bindingSaveDialog.etMobile.error = "Required"
                    }
                    if (bindingSaveDialog.etName.text!!.isNotEmpty() && bindingSaveDialog.etMobile.text!!.isNotEmpty()) {
                        myDbAdapter.SaveData(
                            bindingSaveDialog.etName.text.toString(),
                            bindingSaveDialog.etMobile.text.toString(),
                            height,
                            weight,
                            BmiValue,
                            BmiStatus
                        )
                        dialogToSave.dismiss()
//                    Toast.makeText(this@MainActivity, "Not Saved", Toast.LENGTH_SHORT).show()
                        saveDialog.dismiss()
                    }
                }

            })
        }


    fun saveDialog(){
       var Savedialog = AlertDialog.Builder(this)
        Savedialog.setTitle("Save")
        Savedialog.setMessage("Do you want to Save")
        Savedialog.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
               dialogToInsert()
//                Toast.makeText(this@MainActivity, "i am positive", Toast.LENGTH_SHORT).show()
            }

        })

        Savedialog.setNegativeButton("No", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
//                Toast.makeText(this@MainActivity, "i am negative", Toast.LENGTH_SHORT).show()
            }

        })


        Savedialog.setCancelable(false)
        dialogToSave = Savedialog.create()
        dialogToSave.show()

    }

}

