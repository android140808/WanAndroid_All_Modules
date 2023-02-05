package com.avatar.wan.module_home.data

import com.avatar.wan.common.data.bean.ApiResponse
import com.avatar.wan.common.data.bean.Article
import com.avatar.wan.common.data.bean.Banner
import com.avatar.wan.common.data.bean.PageResponse
import com.avatar.wan.common.http.BaseRepository
import com.avatar.wan.common.http.RetrofitManager
import com.avatar.wan.common.util.LogUtil
import com.avatar.wan.module_home.data.http.Api

object DataRepository : BaseRepository(), Api {

    private val service by lazy { RetrofitManager.getService(Api::class.java) }

    override suspend fun getBanner(): ApiResponse<List<Banner>> {
        LogUtil.e("请求网络数据1")
        return apiCall {
            service.getBanner()
        }
    }

    override suspend fun getArticleTopList(): ApiResponse<List<Article>> {
        LogUtil.e("请求网络数据2 ")
        return apiCall { service.getArticleTopList() }
    }

    override suspend fun getArticlePageList(
        pageNo: Int,
        pageSize: Int
    ): ApiResponse<PageResponse<Article>> {
        return apiCall { service.getArticlePageList(pageNo, pageSize) }
    }

}