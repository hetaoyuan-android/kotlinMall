package com.yuan.user.ui.activity

import android.os.Bundle
import android.view.View
import com.kotlin.base.utils.NetWorkUtils
import com.kotlin.base.widgets.VerifyButton
import com.yuan.baselibrary.common.AppManager
import com.yuan.baselibrary.ext.enable
import com.yuan.baselibrary.ext.onClick
import com.yuan.baselibrary.ui.activity.BaseMvpActivity
import com.yuan.user.R
import com.yuan.user.injection.component.DaggerUserComponent
import com.yuan.user.injection.module.UserModule
import com.yuan.user.presenter.RegisterPresenter
import com.yuan.user.presenter.view.RegisterView
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

class RegisterActivity : BaseMvpActivity<RegisterPresenter>(), RegisterView, View.OnClickListener {

    private var pressTime: Long = 0

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(activtyComponent).userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onRegisterResult(result: String) {
        toast(result)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initView()
//        mRegisterBtn.onClick(object: View.OnClickListener {
//            override fun onClick(v: View?) {
//            }
//        } )
//        mRegisterBtn.onClick {
//
//        }
    }

    private fun initView() {

        mRegisterBtn.enable(mMobileEt, {isBtnEnable()})
        mRegisterBtn.enable(mVerifyCodeEt, {isBtnEnable()})
        mRegisterBtn.enable(mPwdEt, {isBtnEnable()})
        mRegisterBtn.enable(mPwdConfirmEt, {isBtnEnable()})

        mVerifyCodeBtn.onClick(this)
        mRegisterBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id) {
                R.id.mVerifyCodeBtn ->{
                    mVerifyCodeBtn.requestSendVerifyNumber()
                    toast("发送验证码成功")
                }

                R.id.mRegisterBtn ->{

                        mPresenter.register(
                            mMobileEt.text.toString(), mVerifyCodeEt.text.toString(),
                            mPwdEt.text.toString())

                    }
                }
            }
        }

    override fun onBackPressed() {
        val time = System.currentTimeMillis()
        if (time - pressTime > 2000) {
            toast("再按一次退出程序")
            pressTime = time
        } else {
            AppManager.instance.exitApp(this)
        }
    }

    private fun isBtnEnable(): Boolean {
        return mMobileEt.text.isNullOrEmpty().not() &&
                mVerifyCodeEt.text.isNullOrEmpty().not() &&
                mPwdEt.text.isNullOrEmpty().not() &&
                mPwdConfirmEt.text.isNullOrEmpty().not()
    }
}
