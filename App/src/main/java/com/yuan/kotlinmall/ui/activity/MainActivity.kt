package com.yuan.kotlinmall.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.goods.common.GoodsConstant
import com.kotlin.goods.event.UpdateCartSizeEvent
import com.kotlin.message.ui.fragment.MessageFragment
import com.kotlin.provider.event.MessageBadgeEvent
import com.yuan.baselibrary.common.AppManager
import com.yuan.goodscenter.ui.fragment.CartFragment
import com.yuan.goodscenter.ui.fragment.CategoryFragment
import com.yuan.kotlinmall.R
import com.yuan.kotlinmall.ui.fragment.HomeFragment
import com.yuan.kotlinmall.ui.fragment.MeFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import java.util.*

class MainActivity : AppCompatActivity() {

    private var pressTime: Long = 0

    private val mStack = Stack<Fragment>()
    private val mHomeFragment by lazy { HomeFragment() }
    private val mCategoryFragment by lazy { CategoryFragment() }
    private val mCartFragment by lazy { CartFragment() }
    private val mMsgFragment by lazy { MessageFragment() }
    private val mMeFragment by lazy { MeFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        mBottomBavBar.checkMsgBadge(false)
//        mBottomBavBar.checkCartBadge(20)
//        initView()
        initFragment()
        initBottomNav()
        changeFragment(0)
        initObserve()
        loadCarBadge()

//        Observable.timer(2, TimeUnit.SECONDS)
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({mBottomBavBar.checkMsgBadge(true)})
//        Observable.timer(5, TimeUnit.SECONDS)
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({mBottomBavBar.checkCartBadge(0)})


    }

    private fun initFragment() {
        val mansger = supportFragmentManager.beginTransaction()
        mansger.add(R.id.mContaier, mHomeFragment)
        mansger.add(R.id.mContaier, mCategoryFragment)
        mansger.add(R.id.mContaier, mCartFragment)
        mansger.add(R.id.mContaier, mMsgFragment)
        mansger.add(R.id.mContaier, mMeFragment)
        mansger.commit()
        mStack.add(mHomeFragment)
        mStack.add(mCategoryFragment)
        mStack.add(mCartFragment)
        mStack.add(mMsgFragment)
        mStack.add(mMeFragment)
    }
    private fun initView() {
        val mansger = supportFragmentManager.beginTransaction()
        mansger.replace(R.id.mContaier, HomeFragment())
        mansger.commit()
    }

    private fun initBottomNav() {
        mBottomBavBar.setTabSelectedListener(object: BottomNavigationBar.OnTabSelectedListener {
            override fun onTabReselected(p0: Int) {

            }

            override fun onTabUnselected(p0: Int) {
            }

            override fun onTabSelected(p0: Int) {
                changeFragment(p0)
            }
        })
        mBottomBavBar.checkMsgBadge(false)
    }

    private fun changeFragment(position: Int) {
        val mansger = supportFragmentManager.beginTransaction()
        for (fragment in mStack) {
            mansger.hide(fragment)
        }
        mansger.show(mStack[position])
        mansger.commit()
    }

    private fun initObserve() {
        Bus.observe<UpdateCartSizeEvent>()
            .subscribe {
                loadCarBadge()
            }.registerInBus(this)
        Bus.observe<MessageBadgeEvent>()
            .subscribe {
                    t: MessageBadgeEvent ->
                run {
                    mBottomBavBar.checkMsgBadge(t.isVisible)
                }
            }.registerInBus(this)
    }

    private fun loadCarBadge() {
        mBottomBavBar.checkCartBadge(AppPrefsUtils.getInt(GoodsConstant.SP_CART_SIZE))
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
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
}
