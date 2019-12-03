package com.yuan.user.ui.activity

import android.os.Bundle
import android.view.View
import com.yuan.baselibrary.ext.onClick
import com.yuan.baselibrary.ui.activity.BaseMvpActivity
import com.yuan.user.R
import com.yuan.user.injection.component.DaggerUserComponent
import com.yuan.user.injection.module.UserModule
import com.yuan.user.presenter.RegisterPresenter
import com.yuan.user.presenter.view.RegisterView
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

class RegisterActivity : BaseMvpActivity<RegisterPresenter>(), RegisterView {
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

        mRegisterBtn.setOnClickListener {
            mPresenter.register(
                mMobileEt.text.toString(), mVerifyCodeEt.text.toString(),
                mPwdEt.text.toString()
            )
        }

//        mRegisterBtn.onClick(object: View.OnClickListener {
//            override fun onClick(v: View?) {
//            }
//        } )
//        mRegisterBtn.onClick {
//
//        }
    }
}
