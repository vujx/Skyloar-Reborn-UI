package com.example.util

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T> : MutableLiveData<T>() {
  private val pending = AtomicBoolean(false)
  override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
    if (hasActiveObservers()) {
      Log.d("TAG", "Multiple observers registered but only one will be notified of changes.")
    }
    super.observe(
      owner,
      {
        if (pending.compareAndSet(true, false)) {
          observer.onChanged(it)
        }
      }
    )
  }

  @MainThread
  override fun setValue(t: T?) {
    pending.set(true)
    super.setValue(t)
  }
}
