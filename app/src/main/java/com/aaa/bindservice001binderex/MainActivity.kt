package com.aaa.bindservice001binderex

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
  private lateinit var _service: LocalService
  private var _bound: Boolean = false
  private val _con = object: ServiceConnection {
    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
      val binder = service as LocalService.LocalBinder
      _service = binder.getService()
      _bound = true
    }
    override fun onServiceDisconnected(name: ComponentName?) {
      _bound = false
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_main)
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }

    /* Text追加したらバックグラウンドサービスと通信 */
    findViewById<TextView>(R.id.txt_helloworld).setOnClickListener{
      if(!_bound) return@setOnClickListener
      val num = _service.randomNumber
      Log.d("aaaaa", "aaaaa randomnum=$num")
      Toast.makeText(this, "num: $num", Toast.LENGTH_SHORT).show()
    }
  }

  override fun onStart() {
    super.onStart()
    bindService(Intent(this, LocalService::class.java), _con, Context.BIND_AUTO_CREATE)
  }

  override fun onStop() {
    super.onStop()
    unbindService(_con)
  }
}