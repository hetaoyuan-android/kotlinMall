package com.yuan.kotlinmall.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.mall.ui.activity.SettingActivity
import com.kotlin.order.common.OrderConstant
import com.kotlin.order.common.OrderStatus
import com.kotlin.order.ui.activity.OrderActivity
import com.kotlin.provider.common.afterLogin
import com.kotlin.provider.common.isLogined
import com.yuan.baselibrary.common.BaseConstant
import com.yuan.baselibrary.ext.loadUrl
import com.yuan.baselibrary.ext.onClick
import com.yuan.baselibrary.ui.fragment.BaseFragment
import com.yuan.kotlinmall.R
import com.yuan.orderlibrary.ui.activity.ShipAddressActivity
import com.yuan.provider.common.ProviderConstant
import com.yuan.user.ui.activity.LoginActivity
import com.yuan.user.ui.activity.UserInfoActivity
import kotlinx.android.synthetic.main.fragment_me.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class MeFragment: BaseFragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_me, null)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {

        mAllOrderTv.onClick {
            afterLogin {
                startActivity<OrderActivity>()
            }
        }

        mWaitPayOrderTv.onClick {
            startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_WAIT_PAY)
        }
        mWaitConfirmOrderTv.onClick {
            startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_WAIT_CONFIRM)

        }
        mCompleteOrderTv.onClick {
            startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_COMPLETED)
        }

        mAddressTv.onClick {
            if (isLogined()) {
                startActivity<ShipAddressActivity>()
            } else {
                startActivity<LoginActivity>()
            }
        }
        mUserIconIv.onClick {
            if (isLogined()) {
                startActivity<UserInfoActivity>()
            } else {
                startActivity<LoginActivity>()
            }
        }
        mUserNameTv.onClick {
            if (isLogined()) {
                startActivity<UserInfoActivity>()
            } else {
                startActivity<LoginActivity>()
            }
        }
        mSettingTv.onClick {
            startActivity<SettingActivity>()
        }

        mShareTv.onClick {
            toast("敬请期待.......")
        }

    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        if (isLogined()) {
            val userIcon = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_ICON)
            if (userIcon.isNotEmpty()) {
                mUserIconIv.loadUrl(userIcon)
            }
            mUserNameTv.text = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_NAME)
        }else {
            mUserIconIv.setImageResource(R.drawable.icon_default_user)
            mUserNameTv.text = getString(R.string.un_login_text)
        }
    }
}