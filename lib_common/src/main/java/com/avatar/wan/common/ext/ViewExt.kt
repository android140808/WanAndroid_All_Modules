package com.avatar.wan.common.ext

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.avatar.wan.common.util.ScreenUtil
import com.btpj.lib_base.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

/** 加载框 */
@SuppressLint("StaticFieldLeak")
private var mLoadingDialog: MaterialDialog? = null

/** 打开加载框 */
fun AppCompatActivity.showLoading(message: String = "请求网络中") {
    if (!this.isFinishing) {
        if (mLoadingDialog == null) {
            mLoadingDialog = MaterialDialog(this)
                .cancelable(true)
                .cancelOnTouchOutside(false)
                .cornerRadius(6f)
                .customView(R.layout.dialog_loading)
                .maxWidth(literal = ScreenUtil.dp2px(this, 120f))
                .lifecycleOwner(this)
            mLoadingDialog?.getCustomView()?.run {
                this.findViewById<TextView>(R.id.tv_loadingMsg).text = message
            }
        }
        mLoadingDialog?.show()
    }
}

/** 打开加载框 */
fun Fragment.showLoading(message: String = "请求网络中") {
    if (!this.isRemoving) {
        if (mLoadingDialog == null) {
            mLoadingDialog = MaterialDialog(requireContext())
                .cancelable(true)
                .cancelOnTouchOutside(false)
                .cornerRadius(6f)
                .customView(R.layout.dialog_loading)
                .maxWidth(literal = ScreenUtil.dp2px(requireContext(), 120f))
                .lifecycleOwner(this)
            mLoadingDialog?.getCustomView()?.run {
                this.findViewById<TextView>(R.id.tv_loadingMsg).text = message
            }
        }
        mLoadingDialog?.show()
    }
}

/** 隐藏Loading加载框 */
fun hideLoading() {
    mLoadingDialog?.dismiss()
    mLoadingDialog = null
}


fun SwipeRefreshLayout.initColors() {
    setColorSchemeResources(
        R.color.purple_500, android.R.color.holo_red_light,
        android.R.color.holo_orange_light, android.R.color.holo_green_light
    )
}

/**
 * RecyclerView列表为空时的显示视图
 */
fun RecyclerView.getEmptyView(message: String = "列表为空"): View {
    return LayoutInflater.from(context)
        .inflate(R.layout.layout_empty, parent as ViewGroup, false).apply {
            findViewById<TextView>(R.id.tv_empty).text = message
        }
}


fun ImageView.load(url: String, showPlaceholder: Boolean = true) {
    if (showPlaceholder) {
        Glide.with(context).load(url)
            .placeholder(R.drawable.ic_default_img)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(this)
    } else {
        Glide.with(context).load(url)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(this)
    }
}