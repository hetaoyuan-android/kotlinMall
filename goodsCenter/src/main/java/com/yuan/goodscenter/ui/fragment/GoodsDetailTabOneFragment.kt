package com.yuan.goodscenter.ui.fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kotlin.base.utils.YuanFenConverter
import com.kotlin.base.widgets.BannerImageLoader
import com.kotlin.goods.common.GoodsConstant
import com.kotlin.goods.data.protocol.Goods
import com.kotlin.goods.event.AddCartEvent
import com.kotlin.goods.event.GoodsDetailImageEvent
import com.kotlin.goods.event.SkuChangedEvent
import com.kotlin.goods.event.UpdateCartSizeEvent
import com.kotlin.goods.injection.component.DaggerGoodsComponent
import com.kotlin.goods.injection.module.GoodsModule
import com.kotlin.goods.presenter.GoodsDetailPresenter
import com.kotlin.goods.presenter.view.GoodsDetailView
import com.kotlin.goods.widget.GoodsSkuPopView
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.yuan.baselibrary.ext.onClick
import com.yuan.baselibrary.ui.activity.BaseActivity
import com.yuan.baselibrary.ui.fragment.BaseMvpFragment
import com.yuan.goodscenter.R
import com.yuan.goodscenter.ui.adtivity.GoodsDetailActivity
import kotlinx.android.synthetic.main.fragment_goods_detail_tab_one.*
import org.jetbrains.anko.contentView
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.toast

class GoodsDetailTabOneFragment: BaseMvpFragment<GoodsDetailPresenter>(), GoodsDetailView {


    private lateinit var mSkuPop: GoodsSkuPopView
    //SKU弹层出场动画
    private lateinit var mAnimationStart: Animation
    //SKU弹层退场动画
    private lateinit var mAnimationEnd: Animation

    private var mCurGoods:Goods? = null

    override fun injectComponent() {
        DaggerGoodsComponent.builder().activityComponent(activtyComponent).goodsModule(GoodsModule()).build().inject(this)
        mPresenter.mView = this
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_goods_detail_tab_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAnim()
        initSkuPop()
        loadData()
        initObserve()
    }

    private fun initSkuPop() {
        mSkuPop = GoodsSkuPopView(act)
        mSkuPop.setOnDismissListener {
            (activity as GoodsDetailActivity).contentView?.startAnimation(mAnimationEnd)
        }
    }

    private fun initView() {

        mGoodsDetailBanner.setImageLoader(BannerImageLoader())
        mGoodsDetailBanner.setBannerAnimation(Transformer.Accordion)
        mGoodsDetailBanner.setDelayTime(2000)
        //设置指示器位置（当banner模式中有指示器时）
        mGoodsDetailBanner.setIndicatorGravity(BannerConfig.RIGHT)

        //sku弹层
        mSkuView.onClick {
            mSkuPop.showAtLocation((activity as GoodsDetailActivity).contentView
                , Gravity.BOTTOM and Gravity.CENTER_HORIZONTAL,
                0, 0
            )
            (activity as GoodsDetailActivity).contentView?.startAnimation(mAnimationStart)
        }
    }

    /*
        加载数据
     */
    private fun loadData() {
        activity?.intent?.getIntExtra(GoodsConstant.KEY_GOODS_ID, -1)?.let { mPresenter.getGoodsDetailList(it) }
    }

    override fun onAddCartResult(result: Int) {
        Bus.send(UpdateCartSizeEvent())
    }

    override fun onGetGoodsDetailResult(result: Goods) {
        mCurGoods = result

        mGoodsDetailBanner.setImages(result.goodsBanner.split(","))
        mGoodsDetailBanner.start()

        mGoodsDescTv.text = result.goodsDesc
        mGoodsPriceTv.text = YuanFenConverter.changeF2YWithUnit(result.goodsDefaultPrice)
        mSkuSelectedTv.text = result.goodsDefaultSku

        Bus.send(GoodsDetailImageEvent(result.goodsDetailOne, result.goodsDetailTwo))

        loadPopData(result)
    }

    private fun loadPopData(result: Goods) {
        mSkuPop.setGoodsIcon(result.goodsDefaultIcon)
        mSkuPop.setGoodsCode(result.goodsCode)
        mSkuPop.setGoodsPrice(result.goodsDefaultPrice)

        mSkuPop.setSkuData(result.goodsSku)

    }

    private fun initObserve() {
        Bus.observe<SkuChangedEvent>()
            .subscribe {
                mSkuSelectedTv.text = mSkuPop.getSelectSku() +
                GoodsConstant.SKU_SEPARATOR + mSkuPop.getSelectCount() + "件"
            }.registerInBus(this)
        Bus.observe<AddCartEvent>()
            .subscribe {
                addCart()
            }.registerInBus(this)
    }

    private fun addCart() {
        mCurGoods.let {
            if (it != null) {
                mPresenter.addCart(it.id, it.goodsDesc, it.goodsDefaultIcon, it.goodsDefaultPrice,
                    mSkuPop.getSelectCount(),
                    mSkuPop.getSelectSku())
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

    /*
    初始化缩放动画
 */
    private fun initAnim() {
        mAnimationStart = ScaleAnimation(
            1f, 0.95f, 1f, 0.95f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        mAnimationStart.duration = 500
        mAnimationStart.fillAfter = true

        mAnimationEnd = ScaleAnimation(
            0.95f, 1f, 0.95f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        mAnimationEnd.duration = 500
        mAnimationEnd.fillAfter = true
    }
}