package com.aaa.bindservice001binderex

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class LocalService : Service() {
  private val binder = LocalBinder()
  val randomNumber: Int
    get() {
      return java.util.Random().nextInt(100)
    }

  override fun onBind(intent: Intent): IBinder {
    return binder  /* ←ここがミゾ。ServiceはIBinderクラスを継承しているという  */
  }

  inner class LocalBinder : Binder() {
    fun getService(): LocalService = this@LocalService
  }
}