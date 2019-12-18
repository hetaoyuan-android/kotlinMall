package com.yuan.goodscenter.ui.adtivity

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.kennyc.view.MultiStateView
import com.kotlin.goods.data.protocol.Goods
import com.kotlin.goods.injection.component.DaggerGoodsComponent
import com.kotlin.goods.injection.module.GoodsModule
import com.kotlin.goods.presenter.GoodsListPresenter
import com.kotlin.goods.presenter.view.GoodsListView
import com.kotlin.goods.ui.adapter.GoodsAdapter
import com.yuan.baselibrary.ui.activity.BaseMvpActivity
import com.yuan.goodscenter.R
import kotlinx.android.synthetic.main.activity_goods.*

class GoodsActivity: BaseMvpActivity<GoodsListPresenter>(), GoodsListView {

    private lateinit var mGoodsAdapter:GoodsAdapter
    private var mCurrentPage: Int = 1
    private var mMaxPage: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods)
        initView()
    }
    override fun injectComponent() {
        DaggerGoodsComponent.builder().activityComponent(activtyComponent).goodsModule(GoodsModule()).build().inject(this)
        mPresenter.mView = this
    }
    override fun onGetGoodsListResult(result: MutableList<Goods>?) {
        mRefreshLayout.endLoadingMore()
        mRefreshLayout.endRefreshing()
        if (result != null && result.size > 0) {
            mMaxPage = result[0].maxPage
            if (mCurrentPage == 1) {
                mGoodsAdapter.setData(result)
            } else {
                mGoodsAdapter.dataList.addAll(result)
                mGoodsAdapter.notifyDataSetChanged()
            }
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        } else {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }
    private fun initView() {
        mGoodsRv.layoutManager = GridLayoutManager(this, 2)
        mGoodsAdapter = GoodsAdapter(this)
        mGoodsRv.adapter = mGoodsAdapter
        loadData()
    }

    private fun loadData() {
        mMultiStateView.viewState
        mPresenter.getGoodsList(intent.getIntExtra("categoryId", 1), 1)
    }

}