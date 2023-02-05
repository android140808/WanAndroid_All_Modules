package com.avatar.wan.module_home.data.http

import com.avatar.wan.common.data.bean.ApiResponse
import com.avatar.wan.common.data.bean.Article
import com.avatar.wan.common.data.bean.Banner
import com.avatar.wan.common.data.bean.PageResponse
import com.btpj.lib_base.data.bean.*
import retrofit2.http.*

/**
 * Http接口，Retrofit的请求Service
 *
 * @author LTP  2022/3/21
 */
interface Api {

    /** 获取首页banner数据 */
    @GET("banner/json")
    suspend fun getBanner(): ApiResponse<List<Banner>>

    /** 获取置顶文章集合数据 */
    @GET("article/top/json")
    suspend fun getArticleTopList(): ApiResponse<List<Article>>

    /** 获取首页文章数据 */
    @GET("article/list/{pageNo}/json")
    suspend fun getArticlePageList(
        @Path("pageNo") pageNo: Int,
        @Query("page_size") pageSize: Int
    ): ApiResponse<PageResponse<Article>>




}