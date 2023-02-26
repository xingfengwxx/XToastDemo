package com.wangxingxing.xtoastdemo

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.hjq.toast.Toaster
import com.hjq.xtoast.XToast
import com.hjq.xtoast.draggable.SpringDraggable
import com.wangxingxing.xtoastdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOpenNew.setOnClickListener {
            startActivity(Intent(this@MainActivity, SecondActivity::class.java))
        }

        binding.btnOpenGlobal.setOnClickListener {
            XXPermissions.with(this)
                .permission(Permission.SYSTEM_ALERT_WINDOW)
                .request(object : OnPermissionCallback {
                    override fun onGranted(granted: List<String>, all: Boolean) {
                        showGlobalWindow(application)
                    }

                    override fun onDenied(denied: List<String>, never: Boolean) {
//                        XToast<XToast<*>>(this@MainActivity)
//                            .setDuration(1000)
//                            .setContentView(R.layout.window_hint)
//                            .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_error)
//                            .setText(android.R.id.message, "请先授予悬浮窗权限")
//                            .show()
                        Toaster.show("请先授予悬浮窗权限")
                    }
                })
        }
    }

    /**
     * 显示全局弹窗
     */
    fun showGlobalWindow(application: Application?) {
        // 传入 Application 表示这个是一个全局的 Toast
        XToast<XToast<*>>(application)
            .setContentView(R.layout.item_calling)
            .setGravity(Gravity.CENTER or Gravity.TOP)
            .setYOffset(200) // 设置指定的拖拽规则
            .setDraggable(SpringDraggable())
//            .setOnClickListener(android.R.id.icon,
//                XToast.OnClickListener<ImageView?> { toast, view ->
//                    Toaster.show("我被点击了")
//                    // 点击后跳转到拨打电话界面
//                    // Intent intent = new Intent(Intent.ACTION_DIAL);
//                    // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    // toast.startActivity(intent);
//                    // 安卓 10 在后台跳转 Activity 需要额外适配
//                    // https://developer.android.google.cn/about/versions/10/privacy/changes#background-activity-starts
//                })
            .show()
    }
}