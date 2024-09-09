package com.example.currencyconverter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

class Main2Avtivity : AppCompatActivity() {
    private val unitedStatesDollar = "USD"
    private val turkishLira = "TRY"
    private val euro = "EUR"
    private val saudiRiyal = "SAR"
    //val values = mapOf( unitedStatesDollar to 1.0 , turkishLira to 18.62 , euro to 1.0 , saudiRiyal to 3.76)


    private var baceCurrency = ""
    private var convertedToCurrency  = ""
    private var converionRate = 0f
    lateinit var resultEt : TextInputEditText
    lateinit var amountEt : TextInputEditText
    var fromDropDownMenu  : AutoCompleteTextView? = null
    lateinit var toDropDownMenu : AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#EFCD07")))

        amountEt = findViewById(R.id.amount_edit_text)
        toDropDownMenu = findViewById(R.id.to_currency_menu)
        fromDropDownMenu = findViewById(R.id.from_currency_menu)
        resultEt = findViewById(R.id.result_text)
        populateDropDownMenu()


        fromDropDownMenu?.setOnItemClickListener { adapterView, view, i, l ->
            baceCurrency = fromDropDownMenu?.text.toString()
            getApiResult()
            //  Log.d("Main", convertedToCurrency)
        }


        toDropDownMenu.setOnItemClickListener { adapterView, view, i, l ->
            convertedToCurrency = toDropDownMenu.text.toString()
            getApiResult()
            Log.d("Main", baceCurrency)

        }

        amountEt.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                try {
                    getApiResult()
                } catch (e: Exception) {
                    Toast.makeText(applicationContext, "Type a value", Toast.LENGTH_SHORT).show()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("Main", "Before Text Changed")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("Main", "OnTextChanged")
            }

        })


    }


    private fun getApiResult(){
        Log.d("Main" , "amount mull")
        if (amountEt != null && amountEt.text!!.isNotEmpty() && amountEt.text!!.isNotBlank()){

            Log.d("Main" , amountEt.text.toString())

            val API ="https://api.getgeoapi.com/v2/currency/convert?api_key=ccafd887fc3159657013d02028127c1445ce9bb4&from=$baceCurrency&to=$convertedToCurrency&amount=${amountEt.text}&format=json"

            Log.d("API" , API)
                //"https://api.getgeoapi.com/v2/currency/convert?api_key=ccafd887fc3159657013d02028127c1445ce9bb4"

                //"https://api.getgeoapi.com/v2/currency/convert?api_key=ccafd887fc3159657013d02028127c1445ce9bb4&from=$baceCurrency&to=$convertedToCurrency"

                //"https://api.fastforex.io/fetch-all?api_key=1447c9c934-99ebe9ad19-rs4ro2\n"

            //"https://api.fastforex.io/fetch-multi?from=TRY&to=USD%2CTRY%2CEUR%2CSAR&api_key=1447c9c934-99ebe9ad19-rs4ro2"
            if (baceCurrency == convertedToCurrency){
                Toast.makeText(this , "Please pick a currency to convert",
                    Toast.LENGTH_SHORT).show()

                resultEt.setText("")

            }else{
                GlobalScope.launch(Dispatchers.IO){
                    try {
                        val apiResult = URL(API).readText()
                        val jsonObject = JSONObject(apiResult)
                        Log.d("Main0" , "$convertedToCurrency")
                       converionRate = jsonObject.getJSONObject("rates").getJSONObject("$convertedToCurrency").getString("rate").toFloat()
                           //.getString(convertedToCurrency.toString()).toFloat()
                        Log.d("converionRate" , "$converionRate")
                        Log.d("apiResult" , apiResult)

                        withContext(Dispatchers.Main){
                            val text = ((amountEt.text.toString()
                                .toFloat()) * converionRate).toString()
                            resultEt.setText(text)
                            Log.d("Result=" , text)
                        }


                    }catch (e: java.lang.Exception){
                        Log.d("Main3" , "$e")

                    }
                }

            }
        }
    }

    private fun populateDropDownMenu()
    {

        //val currencies: List<ExtendedCurrency> = ExtendedCurrency.getAllCurrenciesList()

        val list = resources.getStringArray(R.array.Currency)
        val listOfCentrrey = listOf(unitedStatesDollar , turkishLira , euro , saudiRiyal)
        val adapter = ArrayAdapter(this , R.layout.drop_down_list_item , list)
        toDropDownMenu.setAdapter(adapter)
        fromDropDownMenu?.setAdapter(adapter)
    }
}