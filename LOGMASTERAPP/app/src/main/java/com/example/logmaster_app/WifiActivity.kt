package com.example.logmaster_app

import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.app.TimePickerDialog
import android.widget.TimePicker
import java.text.SimpleDateFormat
import java.util.*



class WifiActivity : AppCompatActivity() {

    private var b1: Button? = null
    private var textView: TextView? = null
    private var wifiManager: WifiManager? = null
    private var Buttonback: Button? = null
    private var selectedTime: Calendar = Calendar.getInstance()
    private var selectedTime2: Calendar = Calendar.getInstance()
    private var timer: Timer? = null
    private var timer2: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi)

        b1 = findViewById(R.id.wifiSwitch)
        textView = findViewById(R.id.tv)
        wifiManager = getSystemService(Context.WIFI_SERVICE) as WifiManager
        Buttonback = findViewById<Button>(R.id.buttonbackwifi)

        findViewById<Button>(R.id.buttonSelectTime).setOnClickListener {
            showTimePickerDialog()
        }
        findViewById<Button>(R.id.buttonSelectTime2).setOnClickListener {
            showTimePickerDialog2()
        }

        Buttonback!!.setOnClickListener {
            val intent = Intent(this, LOGMASTER::class.java)
            startActivity(intent)
        }

        b1?.setOnClickListener { toggleWifi() }

        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    checkTimeAndToggleWifi()
                }
            }
        }, 0, 2000) // ตรวจสอบทุก 2 วินาที

        timer2 = Timer()
        timer2?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    checkTimeAndToggleWifi2()
                }
            }
        }, 0, 2000) // ตรวจสอบทุก 2 วินาที
    }



    private fun showTimePickerDialog() {
        val hour = selectedTime.get(Calendar.HOUR_OF_DAY)
        val minute = selectedTime.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
                selectedTime.set(Calendar.HOUR_OF_DAY, selectedHour)
                selectedTime.set(Calendar.MINUTE, selectedMinute)

                // เอาเวลาที่เลือกไป เซ็ทใน text
                updateSelectedTimeTextView()

                // ยกเลิกและเริ่ม Timer ใหม่เมื่อทำการเลือกเวลาใหม่
                timer?.cancel()
                timer = Timer()
                timer?.scheduleAtFixedRate(object : TimerTask() {
                    override fun run() {
                        runOnUiThread {
                            checkTimeAndToggleWifi()
                        }
                    }
                }, 0, 2000)
            },
            hour,
            minute,
            true
        )

        timePickerDialog.show()
    }

    private fun updateSelectedTimeTextView() {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val formattedTime = sdf.format(selectedTime.time)

        findViewById<TextView>(R.id.selectedTimeTextView).text = "ตั้งเวลาปิดWIFI: $formattedTime"
    }





    private fun toggleWifi() {
        wifiManager?.let {
            if (it.isWifiEnabled) {
                it.isWifiEnabled = false
                updateWifiStatus(false)
            } else {
                it.isWifiEnabled = true
                updateWifiStatus(true)
            }
        }
    }

    private fun offWifi() {
        wifiManager?.let {
            if (it.isWifiEnabled) {
                it.isWifiEnabled = false
                updateWifiStatus(false)
            }
        }
    }

    private fun updateWifiStatus(isWifiEnabled: Boolean) {
        if (isWifiEnabled) {
            textView?.text = "Wifi is ON"
        } else {
            textView?.text = "Wifi is OFF"
        }
    }

    private fun checkTimeAndToggleWifi() {
        val currentTime = Calendar.getInstance()
        if (currentTime.compareTo(selectedTime) >= 0) {
            // ถ้าถึงเวลาที่กำหนดแล้ว ให้ปิด WiFi
            offWifi()
            timer?.cancel() // ยกเลิกการทำงานของ Timer
            timer = null // เคลียร์ reference เพื่อให้ GC ไปทำลาย Timer
        }
    }




    /* ------------------------------ระบบ ON WIFI-------------------------------- */
    /* ------------------------------ระบบ ON WIFI-------------------------------- */
    /* ------------------------------ระบบ ON WIFI-------------------------------- */
    /* ------------------------------ระบบ ON WIFI-------------------------------- */


    private fun showTimePickerDialog2() {
        val hour = selectedTime2.get(Calendar.HOUR_OF_DAY)
        val minute = selectedTime2.get(Calendar.MINUTE)

        val timePickerDialog2 = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
                selectedTime2.set(Calendar.HOUR_OF_DAY, selectedHour)
                selectedTime2.set(Calendar.MINUTE, selectedMinute)

                // ทำสิ่งที่คุณต้องการทำเมื่อเลือกเวลา
                updateSelectedTimeTextView2()

                // ยกเลิกและเริ่ม Timer ใหม่เมื่อทำการเลือกเวลาใหม่
                timer2?.cancel()
                timer2 = Timer()
                timer2?.scheduleAtFixedRate(object : TimerTask() {
                    override fun run() {
                        runOnUiThread {
                            checkTimeAndToggleWifi2()
                        }
                    }
                }, 0, 2000)
            },
            hour,
            minute,
            true
        )

        timePickerDialog2.show()
    }

    private fun updateSelectedTimeTextView2() {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val formattedTime = sdf.format(selectedTime2.time)

        findViewById<TextView>(R.id.selectedTimeTextView2).text = "ตั้งเวลาเปิดWIFI: $formattedTime"
    }
    private fun OnWifi() {
        wifiManager?.let {
            if (!it.isWifiEnabled) { // เปลี่ยนจาก it.isWifiEnabled เป็น !it.isWifiEnabled
                it.isWifiEnabled = true // เปลี่ยนจาก false เป็น true
                updateWifiStatus(true)
            }
        }
    }
    private fun checkTimeAndToggleWifi2() {
        val currentTime = Calendar.getInstance()
        if (currentTime.compareTo(selectedTime2) >= 0) {
            // ถ้าถึงเวลาที่กำหนดแล้ว ให้ปิด WiFi
            OnWifi()
            timer2?.cancel() // ยกเลิกการทำงานของ Timer2
            timer2 = null // เคลียร์ reference เพื่อให้ GC ไปทำลาย Timer2
        }
    }
}
