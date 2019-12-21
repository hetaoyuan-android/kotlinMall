package com.yuan.goodscenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuan.baselibrary.ui.fragment.BaseFragment
import com.yuan.goodscenter.R

class GoodsDetailTabTwoFragment: BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_goods_detail_tab_two, container, false)
    }
}