package com.yuan.user.ui.activity

import android.os.Bundle
import android.view.View
import com.yuan.baselibrary.common.AppManager
import com.yuan.baselibrary.ext.enable
import com.yuan.baselibrary.ext.onClick
import com.yuan.baselibrary.ui.activity.BaseMvpActivity
import com.yuan.user.R
import com.yuan.user.injection.component.DaggerUserComponent
import com.yuan.user.injection.module.UserModule
import com.yuan.user.presenter.ForgetPresenter
import com.yuan.user.presenter.view.ForgetView
import kotlinx.android.synthetic.main.activity_forget_pwd.*
import kotlinx.android.synthetic.main.activity_register.mMobileEt
import kotlinx.android.synthetic.main.activity_register.mVerifyCodeBtn
import kotlinx.android.synthetic.main.activity_register.mVerifyCodeEt
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * 忘记密码
 */
class ForgetActivity : BaseMvpActivity<ForgetPresenter>(), ForgetView, View.OnClickListener {

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(activtyComponent).userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_pwd)

        initView()
    }

    private fun initView() {

        mNextBtn.enable(mMobileEt, {isBtnEnable()})
        mNextBtn.enable(mVerifyCodeEt, {isBtnEnable()})

        mNextBtn.onClick(this)
        mVerifyCodeBtn.onClick(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id) {
                R.id.mVerifyCodeBtn ->{
                    mVerifyCodeBtn.requestSendVerifyNumber()
                    toast("发送验证码成功")
                }

                R.id.mNextBtn ->{
                    if (checkNetWork()) {
                        mPresenter.forgetPwd(
                            mMobileEt.text.toString(), mVerifyCodeEt.text.toString()
                        )
                    } else {
                        toast("网络不可用")
                    }
                }
            }
        }
    }
    private fun isBtnEnable(): Boolean {
        return mMobileEt.text.isNullOrEmpty().not() &&
                mVerifyCodeEt.text.isNullOrEmpty().not()
    }

    override fun onForgetPwdResult(result: String) {
        toast(result)
        startActivity<ResetActivity>("mobile" to mMobileEt.text.toString())
    }

}
