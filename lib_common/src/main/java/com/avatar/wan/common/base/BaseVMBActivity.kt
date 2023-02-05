package com.avatar.wan.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.avatar.wan.common.ext.hideLoading
import com.avatar.wan.common.util.LogUtil
import com.avatar.wan.common.util.StatusBarUtil
import com.avatar.wan.common.util.ToastUtil
import java.lang.reflect.ParameterizedType
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseVMBActivity<VM : BaseViewModel, B : ViewDataBinding>(private val contentViewResId: Int) :
    AppCompatActivity() {

    lateinit var mViewModel: VM
    lateinit var mBinding: B

    open fun setFullScreen(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (setFullScreen()) {
            StatusBarUtil.setNoStatus(this)
        } else {
            StatusBarUtil.setImmersionStatus(this)
        }

        initViewModel()
        initDataBinding()
        createObserve()
        initView(savedInstanceState)
    }

    private fun initViewModel() {
        val type: Class<VM> =
            (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
        mViewModel = ViewModelProvider(this).get(type)
        mViewModel.start()
    }

    abstract fun initView(savedInstanceState: Bundle?);

    private fun initDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, contentViewResId)
        mBinding.apply {
            lifecycleOwner = this@BaseVMBActivity
            setVariable(com.btpj.lib_base.BR.viewModel, mViewModel)
        }
    }

    open fun createObserve() {
        mViewModel.apply {
            exception.observe(this@BaseVMBActivity) {
                requestError(it.message)
                LogUtil.d("网络请求错误：${it.message}")
                when (it) {
                    is SocketTimeoutException -> ToastUtil.showShort(
                        this@BaseVMBActivity,
                        "网络请求超时，请稍后重试"
                    )
                    is ConnectException, is UnknownHostException -> ToastUtil.showShort(
                        this@BaseVMBActivity,
                        "网络连接失败，请稍后重试"
                    )
                    else -> ToastUtil.showShort(
                        this@BaseVMBActivity, it.message ?: "网络请求错误，请稍后重试"
                    )
                }
            }

            errorResponse.observe(this@BaseVMBActivity) {
                requestError(it?.errorMsg)
                LogUtil.d("${it?.errorMsg}")
                it?.errorMsg?.run {
                    ToastUtil.showShort(this@BaseVMBActivity, this)
                }
                if (it?.errorCode == -1001) {
                    ToastUtil.showShort(this@BaseVMBActivity, "功能完善中， 请稍等！")
                }
            }
        }
    }

    open fun requestError(msg: String?) {
        hideLoading()
    }

}