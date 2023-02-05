package com.avatar.wan.common.http

import com.avatar.wan.common.data.bean.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class BaseRepository {

    suspend fun <T> apiCall(api: suspend () -> ApiResponse<T>): ApiResponse<T> {
        return withContext(Dispatchers.IO) {
            api.invoke()
        }
    }
}