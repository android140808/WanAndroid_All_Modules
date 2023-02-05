package com.avatar.wan.module_home.home

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.avatar.wan.common.data.bean.Banner
import com.avatar.wan.common.ext.load
import com.youth.banner.adapter.BannerAdapter

class MyBannerAdapter(dataList: ArrayList<Banner>) :
    BannerAdapter<Banner, MyBannerAdapter.BannerViewHolder>(dataList) {

    inner class BannerViewHolder(var imageView: ImageView) : RecyclerView.ViewHolder(imageView)

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
        val imageView = ImageView(parent?.context)
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return BannerViewHolder(imageView)
    }

    override fun onBindView(holder: BannerViewHolder, data: Banner, position: Int, size: Int) {
        holder.imageView.apply {
            load(data.imagePath)
        }
    }
}