package com.yuan.user.ui.activity

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.kotlin.user.utils.UserPrefsUtils
import com.yuan.baselibrary.ext.enable
import com.yuan.baselibrary.ext.onClick
import com.yuan.baselibrary.ui.activity.BaseMvpActivity
import com.yuan.provider.router.RouterPath
import com.yuan.user.R
import com.yuan.user.data.protocol.UserInfo
import com.yuan.user.injection.component.DaggerUserComponent
import com.yuan.user.injection.module.UserModule
import com.yuan.user.presenter.LoginPresenter
import com.yuan.user.presenter.view.LoginView
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.mMobileEt
import kotlinx.android.synthetic.main.activity_register.mPwdEt
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * 登录界面
 */
@Route(path = RouterPath.UserCenter.PATH_LOGIN)
class LoginActivity : BaseMvpActivity<LoginPresenter>(), LoginView, View.OnClickListener {


    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(activtyComponent).userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
    }

    private fun initView() {

        mLoginBtn.enable(mMobileEt, {isBtnEnable()})
        mLoginBtn.enable(mPwdEt, {isBtnEnable()})

        mLoginBtn.onClick(this)
        mHeaderBar.getRightView().onClick(this)
        mForgetPwdTv.onClick(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id) {
                R.id.mLoginBtn ->{
                    mPresenter.login(mMobileEt.text.toString(), mPwdEt.text.toString(), "" )
                }
                R.id.mRightTv ->{startActivity<RegisterActivity>()}

                R.id.mForgetPwdTv ->{
                    startActivity<ForgetActivity>()
                }
            }
        }
    }

    private fun isBtnEnable(): Boolean {
        return mMobileEt.text.isNullOrEmpty().not() &&
                mPwdEt.text.isNullOrEmpty().not()
    }

    override fun onLoginResult(result: UserInfo) {
        toast("登录成功")
        UserPrefsUtils.putUserInfo(result)
        finish()
    }
}
