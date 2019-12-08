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
import com.yuan.user.presenter.ResetPresenter
import com.yuan.user.presenter.view.ForgetView
import com.yuan.user.presenter.view.ResetView
import kotlinx.android.synthetic.main.activity_forget_pwd.*
import kotlinx.android.synthetic.main.activity_register.mMobileEt
import kotlinx.android.synthetic.main.activity_register.mVerifyCodeBtn
import kotlinx.android.synthetic.main.activity_register.mVerifyCodeEt
import kotlinx.android.synthetic.main.activity_reset_pwd.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop
import org.jetbrains.anko.toast

/**
 * 重置密码
 */
class  ResetActivity : BaseMvpActivity<ResetPresenter>(), ResetView, View.OnClickListener {

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(activtyComponent).userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pwd)

        initView()
    }

    private fun initView() {

        mConfirmBtn.enable(mPwdEt, {isBtnEnable()})
        mConfirmBtn.enable(mPwdConfirmEt, {isBtnEnable()})

        mConfirmBtn.onClick(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id) {
                R.id.mConfirmBtn ->{
                    if (checkNetWork()) {
                        if (mPwdEt.text.toString() != mPwdConfirmEt.text.toString()) {
                            toast("密码不一致")
                            return
                        }
                        mPresenter.resetPwd(
                            intent.getStringExtra("mobile"), mPwdEt.text.toString()
                        )
                    } else {
                        toast("网络不可用")
                    }
                }
            }
        }
    }
    private fun isBtnEnable(): Boolean {
        return mPwdEt.text.isNullOrEmpty().not() &&
                mPwdConfirmEt.text.isNullOrEmpty().not()
    }

    override fun onResetPwdResult(result: String) {
        toast(result)
        startActivity(intentFor<LoginActivity>().singleTop().clearTop())
    }
}
