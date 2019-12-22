package com.yuan.goodscenter.ui.adtivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.Gravity
import android.widget.QuickContactBadge
import android.widget.TableLayout
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.goods.common.GoodsConstant
import com.kotlin.goods.event.AddCartEvent
import com.kotlin.goods.event.SkuChangedEvent
import com.kotlin.goods.event.UpdateCartSizeEvent
import com.kotlin.provider.common.afterLogin
import com.kotlin.provider.common.isLogined
import com.yuan.baselibrary.ext.onClick
import com.yuan.goodscenter.R
import com.yuan.goodscenter.ui.adapter.GoodsDetailVpAdapter
import com.yuan.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_goods_detail.*
import kotlinx.android.synthetic.main.fragment_goods_detail_tab_one.*
import org.jetbrains.anko.startActivity
import q.rorbin.badgeview.QBadgeView

class GoodsDetailActivity : AppCompatActivity() {

    private lateinit var mCartBdage:QBadgeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_detail)
        initView()
        initObserve()
        loadCarSize()
    }



    private fun initView() {
        mGoodsDetailTab.tabMode = TabLayout.MODE_FIXED
        mGoodsDetailVp.adapter = GoodsDetailVpAdapter(supportFragmentManager, this)
        mGoodsDetailTab.setupWithViewPager(mGoodsDetailVp)
        mLeftIv.onClick { finish() }
        mAddCartBtn.onClick {
            afterLogin {
                Bus.send(AddCartEvent())
            }
        }
        mCartBdage = QBadgeView(this)

        mEnterCartTv.onClick {
            startActivity<CartActivity>()
        }
    }

    private fun initObserve() {
        Bus.observe<UpdateCartSizeEvent>()
            .subscribe {
                setCartBage()
            }.registerInBus(this)
    }

    private fun setCartBage() {
        mCartBdage.setBadgeGravity(Gravity.END or Gravity.TOP)
        mCartBdage.setGravityOffset(22f,-2f,true)
        mCartBdage.setBadgeTextSize(6f,true)
        mCartBdage.bindTarget(mEnterCartTv).badgeNumber = AppPrefsUtils.getInt(GoodsConstant.SP_CART_SIZE)

    }

    private fun loadCarSize() {
        setCartBage()
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}
