package com.yuan.user.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.jph.takephoto.app.TakePhoto
import com.jph.takephoto.app.TakePhotoImpl
import com.jph.takephoto.compress.CompressConfig
import com.jph.takephoto.model.TResult
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.base.utils.DateUtils
import com.kotlin.base.utils.GlideUtils
import com.kotlin.user.utils.UserPrefsUtils
import com.qiniu.android.http.ResponseInfo
import com.qiniu.android.storage.UpCompletionHandler
import com.qiniu.android.storage.UploadManager
import com.yuan.baselibrary.common.BaseConstant
import com.yuan.baselibrary.ext.onClick
import com.yuan.baselibrary.ui.activity.BaseMvpActivity
import com.yuan.provider.common.ProviderConstant
import com.yuan.user.R
import com.yuan.user.data.protocol.UserInfo
import com.yuan.user.injection.component.DaggerUserComponent
import com.yuan.user.injection.module.UserModule
import com.yuan.user.presenter.UserInfoPresenter
import com.yuan.user.presenter.view.UserInfoView
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_user_info.*
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.io.File

/**
 * 用户信息
 */
class UserInfoActivity : BaseMvpActivity<UserInfoPresenter>(), UserInfoView, TakePhoto.TakeResultListener {


    private lateinit var mTakePhoto: TakePhoto
    private lateinit var mTempFile: File
    private val mUploadManager: UploadManager by lazy { UploadManager() }
    private var mLocalFile: String? = null
    private var mRemoteFile: String? = null
    private var mUserIcon: String? = null
    private var mUserName: String? = null
    private var mUserGender: String? = null
    private var mUserSign: String? = null
    private var mUserMobile: String? = null

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(activtyComponent).userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        mTakePhoto = TakePhotoImpl(this, this)
        initView()
        initData()
        mTakePhoto.onCreate(savedInstanceState)
    }

    private fun initView() {
        mUserIconView.onClick {
            showAlertView()
        }
        mHeaderBarUser.getRightView().onClick {
            mPresenter.editUser(
                mRemoteFile!!, mUserNameEt.text?.toString() ?: "",
                if (mGenderMaleRb.isChecked) "0" else "1",
                mUserSignEt.text?.toString() ?: ""
            )
        }

    }

    private fun initData() {
        mUserIcon = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_ICON)
        mUserName = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_NAME)
        mUserGender = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_GENDER)
        mUserSign = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_SIGN)
        mUserMobile = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_MOBILE)
        mLocalFile = mLocalFile
        if (mUserIcon != "") {
            GlideUtils.loadUrlImage(this, mUserIcon!!, mUserIconIv)
        }
        mUserNameEt.setText(mUserName)
        mUserMobileTv.text = mUserMobile
        if (mUserGender == "0") {
            mGenderMaleRb.isChecked = true
        } else {
            mGenderFemaleRb.isChecked = true
        }

        mUserSignEt.setText(mUserSign)
    }

    private fun showAlertView() {
        AlertView(
            "选择图片",
            "",
            "取消",
            null,
            arrayOf("拍照", "相册"),
            this,
            AlertView.Style.ActionSheet,
            object : OnItemClickListener {
                override fun onItemClick(o: Any?, position: Int) {
                    mTakePhoto.onEnableCompress(CompressConfig.ofDefaultConfig(), false)
                    when (position) {
                        0 -> {
                            createTemoFile()
                            mTakePhoto.onPickFromCapture(Uri.fromFile(mTempFile))
                        }
                        1 -> mTakePhoto.onPickFromGallery()

                    }
                }
            }).show()
    }

    override fun takeSuccess(result: TResult?) {
        Log.e("TakePhoto", result?.image?.originalPath)
        Log.e("TakePhoto", result?.image?.compressPath)
        mLocalFile = result?.image?.compressPath
        mPresenter.getUploadToken()
    }

    override fun takeCancel() {

    }

    override fun takeFail(result: TResult?, msg: String?) {
        Log.e("TakePhoto", msg)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mTakePhoto.onActivityResult(requestCode, resultCode, data)
    }

    fun createTemoFile() {
        val tempFileName = "${DateUtils.curTime}.png"
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            this.mTempFile = File(Environment.getExternalStorageDirectory(), tempFileName)
            return
        }

        this.mTempFile = File(filesDir, tempFileName)
    }

    override fun onGetUploadGetTokenResult(result: String) {
        mUploadManager.put(mLocalFile, null, result, object : UpCompletionHandler {
            override fun complete(key: String?, info: ResponseInfo?, response: JSONObject?) {
                mRemoteFile = BaseConstant.IMAGE_SERVER_ADDRESS + response?.get("hash")
                GlideUtils.loadUrlImage(this@UserInfoActivity, mRemoteFile!!, mUserIconIv)
                Log.e("mRemoteFile", mRemoteFile)
            }

        }, null)
    }

    override fun onEditUserResult(result: UserInfo) {
        toast("修改成功")
        UserPrefsUtils.putUserInfo(result)
    }
}
