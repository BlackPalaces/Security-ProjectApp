package com.example.logmaster_app

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView


class LOGMASTER : AppCompatActivity() {

    var cardView4: CardView? = null
    var cardView5: CardView? = null
    var ScrollView2: ScrollView? = null
    var ButtonB: Button? = null
    var ButtonStop: Button? = null
    var Buttonwifi: Button? = null
    var linearLayout: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logmaster)

        cardView4 = findViewById<CardView>(R.id.CardView4)
        cardView5 = findViewById<CardView>(R.id.CardView5)
        ScrollView2 = findViewById<ScrollView>(R.id.ScrollView2)
        ButtonB = findViewById<Button>(R.id.buttonbackwifi)
        ButtonStop = findViewById<Button>(R.id.buttonStopApp)
        Buttonwifi = findViewById<Button>(R.id.buttonwifi)
        val packCardView: CardView = findViewById(R.id.Pack)
        val AppName: EditText = findViewById(R.id.InputAppName)
        val scrollView2: ScrollView = findViewById(R.id.ScrollView2)
        linearLayout = scrollView2.findViewById(R.id.LayoutAddText)

        ButtonB!!.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        Buttonwifi!!.setOnClickListener {
            val intent = Intent(this, WifiActivity::class.java)
            startActivity(intent)
        }

        ButtonStop!!.setOnClickListener {

            val appName = AppName.text.toString()
            val success = stopApp(appName)

            if (success) {
                val newText = appName
                val newTextView = TextView(this)
                newTextView.id = View.generateViewId()
                newTextView.layoutParams = ViewGroup.LayoutParams(
                    resources.getDimensionPixelSize(R.dimen.new_text_width),
                    resources.getDimensionPixelSize(R.dimen.new_text_height)
                )
                newTextView.text = newText
                newTextView.setTextColor(Color.parseColor("#FA1E1E"))
                newTextView.setBackgroundColor(Color.parseColor("#FFFFFF"))
                newTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                val translationX = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    5f,
                    resources.displayMetrics
                )
                val translationY = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    -9f,
                    resources.displayMetrics
                )
                newTextView.translationX = translationX
                newTextView.translationY = translationY

                val newButton = Button(this)
                newButton.id = View.generateViewId()
                newButton.text = "Restore"
                newButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#B7B7B7"))
                newButton.layoutParams = LinearLayout.LayoutParams(
                    resources.getDimensionPixelSize(R.dimen.button_width),
                    resources.getDimensionPixelSize(R.dimen.button_height),
                    1f
                )

                newButton.setOnClickListener {
                    linearLayout?.removeView(newTextView.parent as View)
                }

                val newColumnLayout = LinearLayout(this)
                newColumnLayout.id = View.generateViewId()
                newColumnLayout.orientation = LinearLayout.HORIZONTAL
                newColumnLayout.addView(newTextView)
                newColumnLayout.addView(newButton)
                newColumnLayout.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )

                linearLayout?.addView(newColumnLayout)
                packCardView.visibility = View.GONE
            }
        }

        packCardView.visibility = View.GONE
        val ButtonQuarantine: Button = findViewById(R.id.button7)
        ButtonQuarantine.setOnClickListener {
            packCardView.visibility = View.VISIBLE
        }
        listAllRunningServices()
    }

    private fun stopApp(appName: String): Boolean {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val processes = activityManager.runningAppProcesses

        for (processInfo in processes) {
            if (processInfo.processName == appName) {
                try {
                    Toast.makeText(this, "$appName ถูกหยุดการทำงาน", Toast.LENGTH_SHORT).show()

                    // Stop the app by sending it to the background
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_HOME)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)

                    // Kill the process
                    android.os.Process.killProcess(processInfo.pid)
                    return true
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "ไม่สามารถหยุด $appName", Toast.LENGTH_SHORT).show()
                }
            }
        }

        Toast.makeText(this, "ไม่พบ $appName ที่กำลังทำงาน", Toast.LENGTH_SHORT).show()
        return false
    }
    private fun listAllRunningServices() {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningServices = activityManager.getRunningServices(Int.MAX_VALUE)

        for (serviceInfo in runningServices) {
            val serviceName = serviceInfo.service.className

            //  แสดงชื่อของ service
            println("Service Name: $serviceName")
        }
    }
}
