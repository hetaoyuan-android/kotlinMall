package com.yuan.orderlibrary.ui.activity

import android.os.Bundle
import com.yuan.baselibrary.ext.onClick
import com.yuan.baselibrary.ui.activity.BaseActivity
import com.yuan.orderlibrary.R
import kotlinx.android.synthetic.main.activity_address.*
import org.jetbrains.anko.startActivity

class ShipAddressActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        initView()
    }

    private fun initView() {
        mAddAddressBtn.onClick {
            startActivity<ShipEditActivity>()
        }
    }
}
