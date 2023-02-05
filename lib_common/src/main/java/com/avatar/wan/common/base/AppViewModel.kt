package com.avatar.wan.common.base

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.data.bean.CollectData
import com.btpj.lib_base.data.bean.User

class AppViewModel :BaseViewModel(){
    override fun start() {


    }

    /** 全局用户 */
    val userEvent = MutableLiveData<User?>()

    /** 分享添加文章 */
    val shareArticleEvent = MutableLiveData<Boolean>()

    /** 文章收藏 */
    val collectEvent = MutableLiveData<CollectData>()
}