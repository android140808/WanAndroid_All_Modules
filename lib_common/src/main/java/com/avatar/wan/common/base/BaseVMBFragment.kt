package com.avatar.wan.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.btpj.lib_base.BR
import com.avatar.wan.common.ext.hideLoading
import com.avatar.wan.common.util.LogUtil
import com.avatar.wan.common.util.ToastUtil
import java.lang.reflect.ParameterizedType
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseVMBFragment<VM : BaseViewModel, B : ViewDataBinding>(private val contentViewResId: Int) :
    Fragment() {

    private var mIsFirstLoading = true

    protected lateinit var mViewModel: VM
    protected lateinit var mBinding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, contentViewResId, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mIsFirstLoading = true
        initViewModel()
        initView()
        setupDataBinding()
        createObserve()
    }

    abstract fun initView()

    private fun initViewModel() {
        val type: Class<VM> =
            (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
        mViewModel = ViewModelProvider(this).get(type)
        mViewModel.start()
    }

    private fun setupDataBinding() {
        mBinding.apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.viewModel, mViewModel)
        }
    }

    open fun createObserve() {
        mViewModel.apply {
            exception.observe(viewLifecycleOwner) {
                requestError(it.message)
                LogUtil.d("网络请求错误：${it.message}")
                when (it) {
                    is SocketTimeoutException -> ToastUtil.showShort(
                        requireContext(),
                        "网络请求超时，请稍后重试！"
                    )
                    is ConnectException, is UnknownHostException -> ToastUtil.showShort(
                        requireContext(),
                        "网络连接失败，请稍后重试！"
                    )
                    else -> ToastUtil.showShort(
                        requireContext(),
                        it.message ?: "网络请求错误，请稍后重试！"
                    )
                }
            }
            errorResponse.observe(viewLifecycleOwner) {
                requestError(it?.errorMsg)
                it?.errorMsg?.run {
                    ToastUtil.showShort(requireContext(), this)
                }
                if (it?.errorCode == -1001) {
                    ToastUtil.showShort(requireContext(), "功能完善中， 请稍等！")
                }
            }
        }
    }

    open fun requestError(msg: String? = null) {
        hideLoading()
    }


}