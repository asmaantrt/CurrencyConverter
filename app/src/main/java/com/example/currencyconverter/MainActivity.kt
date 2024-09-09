package com.example.currencyconverter

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private val unitedStatesDollar = "United States Dollar"
    private val turkishLira = "Turkish Lira"
    private val euro = "uro"
    private val saudiRiyal = "Saudi Riyal"
    val values = mapOf( unitedStatesDollar to 1.0 , turkishLira to 18.62 , euro to 1.0 , saudiRiyal to 3.76)

    var fromDropDownMenu  : AutoCompleteTextView? = null
    lateinit var toDropDownMenu : AutoCompleteTextView
    lateinit var convertBtn : Button
    lateinit var amountEt : TextInputEditText
    lateinit var resultEt : TextInputEditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeView()
        populateDropDownMenu()

        var Messages = intent.getStringExtra("USER NAME")
        Toast.makeText(this , "Hello " + Messages , Toast.LENGTH_LONG).show()

        toDropDownMenu.setOnItemClickListener { adapterView, view, i, l ->
            calculatResult()
        }

        fromDropDownMenu?.setOnItemClickListener { adapterView, view, i, l ->
            calculatResult()
        }
        amountEt.addTextChangedListener {
            calculatResult()
        }
       /* convertBtn.setOnClickListener {
            calculatResult()
        }*/




    }

    private fun calculatResult(){
        if (amountEt.text.toString().isNotEmpty())
        {
            var amount = amountEt.text.toString().toDouble()
            var toValue = values[toDropDownMenu.text.toString()]
            var fromValue = values[fromDropDownMenu?.text.toString()]
            var result = (amount.times(toValue!!)).div(fromValue!!)
            var formattedResult = String.format("%.3f" , result)
            resultEt.setText(formattedResult.toString())

        } else
        { amountEt.setError(" Amount Field Required")

            /* val snackBar = Snackbar.make(toDropDownMenu , " Amount Field Required " , Snackbar.LENGTH_LONG)
            snackBar.show()
            snackBar.setAction("OK"){}  */
        }


    }

    private fun populateDropDownMenu()
    {
        val listOfCentrrey = listOf(unitedStatesDollar , turkishLira , euro , saudiRiyal)
        val adapter = ArrayAdapter(this , R.layout.drop_down_list_item , listOfCentrrey)
        toDropDownMenu.setAdapter(adapter)
        fromDropDownMenu?.setAdapter(adapter)
    }

    private fun initializeView()
    {

        convertBtn = findViewById(R.id.convert_button)
        amountEt  = findViewById(R.id.amount_edit_text)
        resultEt  = findViewById(R.id.result_text)
        toDropDownMenu = findViewById(R.id.to_currency_menu)
        fromDropDownMenu = findViewById(R.id.from_currency_menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu , menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.opShare -> {

            var message = "${amountEt.text.toString()} ${fromDropDownMenu?.text.toString()} " +
                    " Is Equal To ${resultEt.text.toString()} ${toDropDownMenu.text.toString()}"

            //show all apps sending
            val ShareIntent = Intent(Intent.ACTION_SEND)
                ShareIntent.type = "text/plain"
                ShareIntent.putExtra(Intent.EXTRA_TEXT , message)


                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(ShareIntent)
                }
                else{
                    Toast.makeText(this , " the app is none " , Toast.LENGTH_LONG).show()
                }

            }
            R.id.opmess -> {
                val sendIntent = Intent(Intent.ACTION_SENDTO)
                sendIntent.data = Uri.parse("mailto:")

                    //check if the app inside mobile
               if (sendIntent.resolveActivity(packageManager) != null)
               {
                   startActivity(sendIntent)
               }
                else{
                    Toast.makeText(this , " the app is none " , Toast.LENGTH_LONG).show()
                }

                /*
                //show all apps sending
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "*//*"
                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("samoasma@gmail.com"))
                intent.putExtra(Intent.EXTRA_SUBJECT, "subject")
                intent.putExtra(Intent.EXTRA_STREAM, "attachment")

                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this , " the app is none " , Toast.LENGTH_LONG).show()
                }
                 */
                 }
            R.id.opbrow ->
            {
                val browserIntent = Intent(Intent.ACTION_VIEW)
                browserIntent.data = Uri.parse("https://www.google.com")
                if (browserIntent.resolveActivity(packageManager) != null) {
                    startActivity(browserIntent)
                }
                else{
                    Toast.makeText(this , " the app is none " , Toast.LENGTH_LONG).show()
                }
            }
            R.id.opContact ->
            {
                val callIntent = Intent(Intent.ACTION_DIAL)
                callIntent.data = Uri.parse("tel:05360561203")
                startActivity(callIntent)

            }
        }


        return super.onOptionsItemSelected(item)
    }



}
