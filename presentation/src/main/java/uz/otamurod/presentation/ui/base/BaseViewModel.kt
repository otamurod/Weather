package uz.otamurod.presentation.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel:ViewModel() {
    protected fun <T> LiveData<T>.postValue(value: T?) {
        (this as? MutableLiveData<T>)?.postValue(value)
    }

    protected fun <T> LiveData<T>.setValue(value: T?) {
        (this as? MutableLiveData<T>)?.value = value
    }
}