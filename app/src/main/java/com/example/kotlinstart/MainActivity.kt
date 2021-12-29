package com.example.kotlinstart

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*

class MainActivity : AppCompatActivity() {

    lateinit var tvDemo:TextView
    lateinit var autoCompleteTextView:AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        basicTextView()
        dynamicTextView()
        autoCompleteTextView()

    }

    private fun autoCompleteTextView() {

        autoCompleteTextView = findViewById(R.id.acText)

        val user = arrayOf("Aparnathi Devendra","Aparnathi Jigar")

        /*val user = User(0,autoCompleteTextView.text.toString())
*/

        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,user)
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.threshold = 1
        val button = findViewById<Button>(R.id.btnSubmit)
            button.setOnClickListener(View.OnClickListener {

/*                val db = DataBaseHandler(this)
                if (autoCompleteTextView.text.toString().isNotEmpty())
                {
                    db.insertData(user)
                }else
                {

                }*/

                val enterText = autoCompleteTextView.text

                if(enterText.isNotEmpty())
                {
                    Toast.makeText(applicationContext, enterText.toString(), Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,ChatActivity::class.java)
                    startActivity(intent)

                }else
                {
                    autoCompleteTextView.setError("Please Enter")
                }


            })
    }


    private fun dynamicTextView() {
        val layout = findViewById<LinearLayout>(R.id.llRoot)
        val tv_dynamic = TextView(this)
        layout.addView(tv_dynamic)

        tv_dynamic.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT).apply { marginStart = 20.dpToPixels(this@MainActivity) }
        tv_dynamic.setText("Mango")
        tv_dynamic.setTextSize(TypedValue.COMPLEX_UNIT_SP,14f)
        tv_dynamic.setTextColor(Color.MAGENTA)
        tv_dynamic.gravity = Gravity.CENTER_HORIZONTAL

        tv_dynamic.setOnClickListener {
            Toast.makeText(applicationContext, "You Click on Custom Text", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun basicTextView() {

        tvDemo = findViewById(R.id.tvDemo)

///////////////////////////////For Strings
/*
        var string: String = "bye"

        tvDemo.setText(string.length)*/

///////////////////////////////For Operation
/*        val a=20
        val b=10

        println("a+b = " +(a+b))
        println("a-b = " +(a-b))
        println("a*b = " +(a*b))
        println("a/b = " +(a/b))*/

///////////////////////////////For TextView Operation

        tvDemo.setOnClickListener {
            tvDemo.setText("hello")
            Toast.makeText(applicationContext, tvDemo.text, Toast.LENGTH_SHORT).show()
        }
    }
}

private fun Int.dpToPixels(context:Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
    ).toInt()
}
