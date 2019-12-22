package com.yuan.baselibrary.widgets

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.yuan.baselibrary.R
import com.yuan.baselibrary.ext.onClick

import kotlinx.android.synthetic.main.layout_header_bar.view.*

class HeaderBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var isShowBack = true
    private var titleText: String? = null
    private var rightText: String? = null

    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.HeaderBar)
        isShowBack = typeArray.getBoolean(R.styleable.HeaderBar_isShowBack, true)
        titleText = typeArray.getString(R.styleable.HeaderBar_titleText)
        rightText = typeArray.getString(R.styleable.HeaderBar_rightText)

        initView()
    }

    private fun initView() {
        View.inflate(context, R.layout.layout_header_bar, this)

        mLeftIv.visibility = if (isShowBack) View.VISIBLE else View.GONE

        titleText?.let {
            mTitleTv.text = it
        }

        rightText?.let{
            mRightTv.text = it
            mRightTv.visibility = View.VISIBLE
        }

        mLeftIv.onClick {
            if (context is Activity) {
                (context as Activity).finish()
            }
        }
    }

    fun getRightView(): TextView {
        return mRightTv
    }

    fun getRightText(): String {
        return mRightTv.text.toString()
    }
    fun getLeftText(): ImageView {
        return mLeftIv
    }
}
