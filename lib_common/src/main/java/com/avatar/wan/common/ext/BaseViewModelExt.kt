package com.avatar.wan.common.ext

import androidx.lifecycle.viewModelScope
import com.avatar.wan.common.base.BaseViewModel
import com.avatar.wan.common.data.bean.ApiResponse
import com.avatar.wan.common.util.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

fun BaseViewModel.launch(
    tryBlock: suspend CoroutineScope.() -> Unit,
    catchBlock: suspend CoroutineScope.() -> Unit = {},
    finallyBlock: suspend CoroutineScope.() -> Unit = {}
) {
    viewModelScope.launch {
        try {
            tryBlock()
        } catch (e: Exception) {
            exception.value = e
            catchBlock()
        } finally {
            finallyBlock()
        }
    }
}

fun BaseViewModel.launch1(
    tryBody: suspend CoroutineScope.() -> Unit,
    catchBody: suspend CoroutineScope.() -> Unit = {},
    finallyBody: suspend CoroutineScope.() -> Unit = {}
) {
    viewModelScope.launch {
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

suspend fun <T> BaseViewModel.handleRequest(
    response: ApiResponse<T>,
    successBlock: suspend CoroutineScope.(response: ApiResponse<T>) -> Unit = {},
    errorBlock: suspend CoroutineScope.(response: ApiResponse<T>) -> Boolean = { false }
) {
    coroutineScope {
        when (response.errorCode) {
            0 -> successBlock(response) // 服务器返回请求成功码
            else -> { // 服务器返回的其他错误码
                if (!errorBlock(response)) {
                    // 只有errorBlock返回false不拦截处理时，才去统一提醒错误提示
                    errorResponse.value = response
                }
            }
        }
    }
}

suspend fun <R> BaseViewModel.handleRequest1(
    response: ApiResponse<R>,
    successBody: suspend CoroutineScope.(response: ApiResponse<R>) -> Unit = {},
    errorBody: suspend CoroutineScope.(response: ApiResponse<R>) -> Boolean = { false }
) {
    coroutineScope {
        when (response.errorCode) {
            0 -> successBody(response)
            else -> {
                if (!errorBody(response)) {
                    errorResponse.value = response
                }
            }
        }

    }
}