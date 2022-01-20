package com.ezatpanah.simplealarmmanager

import android.annotation.SuppressLint
import android.app.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.DatePickerDialog.OnDateSetListener

import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context

import android.content.Intent
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.ezatpanah.simplealarmmanager.databinding.ActivityMainBinding
import java.util.*
import android.widget.Toast
import kotlin.concurrent.fixedRateTimer


class MainActivity : AppCompatActivity(), OnTimeSetListener, OnDateSetListener {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pickDate()

    }

    fun setAlarm() {
        val calNow = Calendar.getInstance()
        val calSet = calNow.clone() as Calendar
        calSet[Calendar.HOUR_OF_DAY] = alarmHour
        calSet[Calendar.MINUTE] = alarmMin
        calSet[Calendar.DAY_OF_MONTH] = alarmDay
        calSet[Calendar.YEAR] = alarmYear
        calSet[Calendar.MONTH] = alarmMonth
        setAlarmN(calSet)
    }

    @SuppressLint("SetTextI18n")
    private fun setAlarmN(targetCal: Calendar) {
        binding.tvShowDateTime.text="Alarm is set at" + targetCal.time
        Toast.makeText(this, "Alarm is set at" + targetCal.time,
            Toast.LENGTH_LONG).show()
        val intent = Intent(baseContext, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            baseContext, 1, intent, 0)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager[AlarmManager.RTC_WAKEUP, targetCal.timeInMillis] = pendingIntent
    }


    private fun pickDate() {
        binding.btnSetAlarm.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c[Calendar.YEAR]
            val month = c[Calendar.MONTH]
            val day = c[Calendar.DAY_OF_MONTH]
            DatePickerDialog(this, this, year, month, day).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        alarmDay = dayOfMonth
        alarmMonth = month
        alarmYear = year

        val c = Calendar.getInstance()
        val hour = c[Calendar.HOUR_OF_DAY]
        val minute = c[Calendar.MINUTE]
        TimePickerDialog(this, this, hour, minute, true).show()

    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        alarmHour = hourOfDay
        alarmMin = minute

        setAlarm()
    }

}