package com.avatar.wan.module_home.ui

import android.os.Bundle
import com.avatar.wan.common.base.BaseVMBActivity
import com.avatar.wan.common.export.ModuleHomeApi
import com.btpj.lib_base.databinding.CommonContainerBinding

class AloneActivity : BaseVMBActivity<AloneViewModel,CommonContainerBinding>(com.btpj.lib_base.R.layout.common_container){
    override fun initView(savedInstanceState: Bundle?) {
        supportFragmentManager.beginTransaction()
            .add(com.btpj.lib_base.R.id.fl_container, ModuleHomeApi.getHomeFragment())
            .commit()
    }
}