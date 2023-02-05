package com.avatar.wan.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.avatar.wan.common.data.bean.ApiResponse

abstract class BaseViewModel : ViewModel() {

    val exception = MutableLiveData<Exception>()

    //    val errorResponse = MutableLiveData<ApiResponse<*>>()
    val errorResponse = MutableLiveData<ApiResponse<*>?>()

    abstract fun start();
}