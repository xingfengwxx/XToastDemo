package com.wangxingxing.xtoastdemo

import android.R
import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.ToastUtils
import com.hjq.xtoast.XToast
import com.wangxingxing.xtoastdemo.databinding.ItemCallingBinding


/**
 * author : 王星星
 * date : 2023/2/25 14:39
 * email : 1099420259@qq.com
 * description : 每个Activity创建的时候，创建一个xToast，键值对形式保存下来。需要show的时候通过key获得xToast对象。
 *
 */
class FloatingLifecycle : Application.ActivityLifecycleCallbacks {

    companion object {
        private var xToast: XToast<*>? = null

        private val floatingMap = HashMap<String, XToast<*>>()

        fun with(application: Application) {
            application.registerActivityLifecycleCallbacks(FloatingLifecycle())
        }

        fun getTopFloatingView(activity: Activity): XToast<*>? {
            return floatingMap[activity.javaClass.name]
        }
    }

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
//        XToast<XToast<*>>(activity)
//            .setContentView(R.layout.item_test)
//            .show()
//        val params = WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
//            WindowManager.LayoutParams.WRAP_CONTENT)
        if (activity is MainActivity) {
            Log.i("wxx", "onActivityCreated: MainActivity, class name=${activity.javaClass.name}")
        }

        val binding: ItemCallingBinding = ItemCallingBinding.inflate(activity.layoutInflater)
        val xToast = XToast<XToast<*>>(activity)
            .setContentView(binding.root)
            .setGravity(Gravity.TOP)
            .setOnClickListener(binding.ivCallEnd.id, XToast.OnClickListener<ImageView?> { toast: XToast<*>, view: ImageView? ->
                ToastUtils.showShort("点击取消")
                toast.cancel()
                App.getInstance().mIsShowFloatingView = true
            })
            .setWidth(ScreenUtils.getScreenWidth())

        floatingMap[activity.javaClass.name] = xToast

//        xToast?.show()
//        xToast?.cancel()
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
        if (App.getInstance().mIsShowFloatingView) {
            val floatingView = getTopFloatingView(ActivityUtils.getTopActivity())
            val tvContent = floatingView?.contentView?.findViewById<TextView>(com.wangxingxing.xtoastdemo.R.id.tv_content)
            tvContent?.text = System.currentTimeMillis().toString()
            floatingView?.show()
        }
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, p1: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        floatingMap.remove(activity.javaClass.name)
    }
}