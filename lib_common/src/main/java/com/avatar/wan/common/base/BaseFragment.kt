package com.avatar.wan.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.avatar.wan.common.ext.hideLoading
import com.avatar.wan.common.util.ToastUtil
import com.btpj.lib_base.BR
import java.lang.reflect.ParameterizedType
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * BaseFragment 基础类
 * VM ViewModel 子类需要实现
 * B ViewDataBinding 需要确定布局文件后根据layout标签会APT自动生成的中间类
 */
abstract class BaseFragment<VM : BaseViewModel1, B : ViewDataBinding> constructor(val resIDLayout: Int) :
    Fragment() {

    private var mIsFirstLoading = true

    /**
     * ViewDataBinding
     */
    lateinit var mBinding: B

    /**
     * ViewModel
     */
    lateinit var mViewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //绑定ViewDataBinding
        mBinding = DataBindingUtil.inflate(inflater, resIDLayout, container, false)
        //获取根布局
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView()
        setUpViewBinding()
        createObserver()
    }

    /**
     * 初始化ViewModel
     * 根据子类泛型参数的第一个参数来确定类型
     */
    private fun initViewModel() {
        //SonFragment<SonViewModel,SonXXXBinding> --> SonViewModel
        val type = (this.javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0] as Class<VM>
        mViewModel = ViewModelProvider(this).get(type)
    }

    /**
     * 初始化视图数据
     */
    abstract fun initView()

    /**
     * 初始化ViewBinding
     */
    private fun setUpViewBinding() {
        mBinding.apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.viewModel, mViewModel)
        }
    }

    /**
     * 创建观察者对象
     */
    open fun createObserver() {
        mViewModel.apply {
            exception.observe(viewLifecycleOwner) {
                handleError(it.message)
                when (it) {
                    //请求超时
                    is SocketTimeoutException -> {
                        ToastUtil.showShort(requireContext(), "请求超时")
                    }
                    //连接异常，主机异常
                    is ConnectException, is UnknownHostException -> {
                        ToastUtil.showShort(requireContext(), "连接异常")
                    }
                    //其他暂时归类为网络异常
                    else -> {
                        ToastUtil.showShort(requireContext(), "网络异常")
                    }
                }
            }

            errorResponse.observe(viewLifecycleOwner) {
                handleError(it?.errorMsg)
                it?.errorMsg?.apply {
                    ToastUtil.showShort(requireContext(), this)
                }
                if (it?.errorCode == -10001) {
                    ToastUtil.showShort(requireContext(), "功能完善中！")
                }
            }
        }
    }

    open fun handleError(msg: String? = null) {
        hideLoading()
    }

}