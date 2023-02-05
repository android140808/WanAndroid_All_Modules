package com.avatar.wan.common.adapter

import android.widget.Toast
import com.avatar.wan.common.data.bean.Article
import com.avatar.wan.common.export.ModuleWebApi
import com.btpj.lib_base.R
import com.btpj.lib_base.databinding.CommonListItemArticleBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

class ArticleAdapter :
    BaseQuickAdapter<Article, BaseDataBindingHolder<CommonListItemArticleBinding>>(R.layout.common_list_item_article) ,LoadMoreModule{

    init {
        setAnimationWithDefault(AnimationType.ScaleIn)
    }

    override fun convert(
        holder: BaseDataBindingHolder<CommonListItemArticleBinding>,
        item: Article
    ) {
        holder.dataBinding?.apply {
            article = item
            executePendingBindings()
            clItem.setOnClickListener {
//                ModuleWebApi.navToWebActivity(article)
                Toast.makeText(context, "还没有准备好，请稍等！", Toast.LENGTH_SHORT).show()
            }
        }
    }
}