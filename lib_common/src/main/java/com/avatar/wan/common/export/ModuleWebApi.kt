package com.avatar.wan.common.export

import com.alibaba.android.arouter.launcher.ARouter
import com.avatar.wan.common.data.bean.Article

object ModuleWebApi {

    const val ROUTER_WEB_WEB_ACTIVITY = "/web/WebActivity"
    const val ROUTER_WEB_EXTRA_BANNER = "extra_banner"
    const val ROUTER_WEB_EXTRA_ARTICLE = "extra_article"
    const val ROUTER_WEB_EXTRA_COLLECT_ARTICLE = "extra_collect_article"
    const val ROUTER_WEB_EXTRA_COLLECT_URL = "extra_collect_url"


    /**
     * 跳转到web页面
     * @param article Article实体
     */
    fun navToWebActivity(article: Article) {
        ARouter.getInstance().build(ROUTER_WEB_WEB_ACTIVITY)
            .withParcelable(ROUTER_WEB_EXTRA_ARTICLE, article).navigation()
    }
}