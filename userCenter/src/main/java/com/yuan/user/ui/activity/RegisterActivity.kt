package com.yuan.user.ui.activity

import android.os.Bundle
import com.yuan.baselibrary.ui.activity.BaseMvpActivity
import com.yuan.user.R
import com.yuan.user.injection.component.DaggerUserComponent
import com.yuan.user.injection.module.UserModule
import com.yuan.user.presenter.RegisterPresenter
import com.yuan.user.presenter.view.RegisterView
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

class RegisterActivity : BaseMvpActivity<RegisterPresenter>(), RegisterView {
    override fun onRegisterResult(result: Boolean) {
        toast("注册成功")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initInjection()

        mRegisterBtn.setOnClickListener {
            mPresenter.register(
                mMobileEt.text.toString(), mVerifyCodeEt.text.toString(),
                mPwdEt.text.toString()
            )
        }
    }

    private fun initInjection() {
        DaggerUserComponent.builder().activityComponent(activtyComponent).userModule(UserModule()).build().inject(this)
        mPresenter.mView = this


    }
}
