package com.wangxingxing.xtoastdemo

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils


/**
 * author : 王星星
 * date : 2023/2/25 14:43
 * email : 1099420259@qq.com
 * description :
 */
class App : Application() {

    private val TAG = "wxx"
    private var mHandler: Handler? = null
    var mIsShowFloatingView = false

    override fun onCreate() {
        super.onCreate()
        instance = this

        Utils.init(this)

        FloatingLifecycle.with(this)

        mHandler = Handler(Looper.getMainLooper()) {
            Log.i(TAG, "handleMessage: 显示弹窗")
            //获取栈顶Activity，弹出悬浮窗
            val floatingView = FloatingLifecycle.getTopFloatingView(ActivityUtils.getTopActivity())
            val tvContent = floatingView?.contentView?.findViewById<TextView>(R.id.tv_content)
            tvContent?.text = System.currentTimeMillis().toString()
            floatingView?.show()
            mIsShowFloatingView = true

//            createDialog(ActivityUtils.getTopActivity())

//            val dialogFragment = MyDialogFragment()
//            dialogFragment.show((ActivityUtils.getTopActivity() as AppCompatActivity).supportFragmentManager, "my_dialog")
            // 悬浮窗显示时，返回键处理
            true
        }

        //模拟收到通知
        mHandler?.sendEmptyMessageDelayed(1, 5000)
//        mHandler?.sendEmptyMessageDelayed(2, 10000)
    }

    private fun createDialog(context: Context) {
        val dialog = Dialog(context)
        val window: Window? = dialog.window
        val layoutParams = window!!.attributes
        layoutParams.x = 0 // 设置 Dialog 左上角的 x 坐标
        layoutParams.y = 0 // 设置 Dialog 左上角的 y 坐标
        layoutParams.width = (context.resources.displayMetrics.widthPixels * 0.8).toInt()
        layoutParams.gravity = Gravity.TOP or Gravity.CENTER // 设置 Dialog 在屏幕左上角
        window.attributes = layoutParams
        dialog.setContentView(R.layout.item_calling)
        dialog.show()
        val ivCallEnd = dialog.findViewById<ImageView>(R.id.iv_call_end)
        ivCallEnd.setOnClickListener {
            ToastUtils.showShort("点击取消按钮")
            dialog.dismiss()
        }
    }

    companion object {
        private var instance: App? = null
        fun getInstance(): App = instance!!
    }
}