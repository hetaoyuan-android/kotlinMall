package com.yuan.goodscenter.ui.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kennyc.view.MultiStateView
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.goods.data.protocol.Category
import com.kotlin.goods.injection.component.DaggerCategoryComponent
import com.kotlin.goods.injection.module.CategoryModule
import com.kotlin.goods.presenter.CategoryPresenter
import com.kotlin.goods.presenter.view.CategoryView
import com.kotlin.goods.ui.adapter.SecondCategoryAdapter
import com.kotlin.goods.ui.adapter.TopCategoryAdapter
import com.yuan.baselibrary.ui.fragment.BaseMvpFragment
import com.yuan.goodscenter.R
import com.yuan.goodscenter.ui.adtivity.GoodsActivity
import kotlinx.android.synthetic.main.fragment_category.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity

class CategoryFragment: BaseMvpFragment<CategoryPresenter>(), CategoryView {
    //一级分类Adapter
    lateinit var topAdapter: TopCategoryAdapter
    //二级分类Adapter
    lateinit var secondAdapter: SecondCategoryAdapter
    private lateinit var rootView: View
    override fun injectComponent() {
        DaggerCategoryComponent.builder().activityComponent(activtyComponent).categoryModule(CategoryModule()).build()
            .inject(this)
        mPresenter.mView = this
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        rootView = inflater.inflate(R.layout.fragment_category, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        loadData()
    }

    private fun initView() {
        mTopCategoryRv.layoutManager = LinearLayoutManager(context)
        topAdapter = TopCategoryAdapter(act)
        mTopCategoryRv.adapter = topAdapter
        //单项点击事件
        topAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Category> {
            override fun onItemClick(item: Category, position: Int) {
                for (category in topAdapter.dataList) {
                    category.isSelected = item.id == category.id
                }
                topAdapter.notifyDataSetChanged()
                loadData(item.id)
            }
        })

        mSecondCategoryRv.layoutManager = GridLayoutManager(context, 3)
        secondAdapter = SecondCategoryAdapter(act)
        mSecondCategoryRv.adapter = secondAdapter
        secondAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Category> {
            override fun onItemClick(item: Category, position: Int) {
                startActivity<GoodsActivity>("categoryId" to item.id)
            }
        })
    }

    /*
       加载数据
    */
    private fun loadData(parentId: Int = 0) {
        if (parentId != 0) {
            mMultiStateView.viewState

        }

        mPresenter.getCategory(parentId)

    }

    override fun onGetCategoryResult(result: MutableList<Category>?) {
        if (result != null && result.size > 0) {
            if (result[0].parentId == 0) {
                result[0].isSelected = true
                topAdapter.setData(result)
                mPresenter.getCategory(result[0].id)
            } else {
                secondAdapter.setData(result)
                mTopCategoryIv.visibility = View.VISIBLE
                mCategoryTitleTv.visibility = View.VISIBLE
                mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
            }
        } else {
            //没有数据
            mTopCategoryIv.visibility = View.INVISIBLE
            mCategoryTitleTv.visibility = View.INVISIBLE
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
//        result?.let {
//            if (result[0].parentId == 0) {
//                result[0].isSelected = true
//                topAdapter.setData(result)
//                mPresenter.getCategory(result[0].id)
//            } else {
//                secondAdapter.setData(result)
//                mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
//            }
//            }
//        }
    }
}