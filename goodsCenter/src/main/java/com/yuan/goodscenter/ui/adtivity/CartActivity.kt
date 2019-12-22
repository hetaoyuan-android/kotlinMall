package com.yuan.goodscenter.ui.adtivity

import android.os.Bundle
import com.yuan.baselibrary.ui.activity.BaseActivity
import com.yuan.goodscenter.R
import com.yuan.goodscenter.ui.fragment.CartFragment

class CartActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val findFragment = supportFragmentManager.findFragmentById(R.id.fragment_cart)
        (findFragment as CartFragment).setBackVisible(true)
    }
}
