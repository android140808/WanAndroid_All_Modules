package com.avatar.wan.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avatar.wan.common.data.bean.ApiResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

abstract class BaseViewModel1 : ViewModel() {

    val exception = MutableLiveData<Exception>()

    val errorResponse = MutableLiveData<ApiResponse<*>?>()

    abstract fun start()
}

/**
 * BaseViewModel顶层函数launch
 *
 */
fun BaseViewModel1.launch(
    tryBody: suspend CoroutineScope.() -> Unit,
    catchBody: suspend CoroutineScope.() -> Unit = {},
    finallyBody: suspend CoroutineScope.() -> Unit = {}
) {
    viewModelScope.launch() {
        try {
            tryBody()
        } catch (e: Exception) {
            exception.value = e
            catchBody()
        } finally {
            finallyBody()
        }
    }
}

/**
 * BaseViewModel顶层函数handleRequest
 */
suspend fun <T> BaseViewModel1.handleRequest(
    apiResponse: ApiResponse<T>,
    errorResponse: suspend CoroutineScope.(response: ApiResponse<T>) -> Boolean = { false },
    successResponse: suspend CoroutineScope.(response: ApiResponse<T>) -> Unit = {

    }
) {

    coroutineScope {
        when (apiResponse.errorCode) {
            0 -> successResponse(apiResponse)
            else -> {
                if (!errorResponse(apiResponse))
                    errorResponse(apiResponse)
            }
        }
    }

}