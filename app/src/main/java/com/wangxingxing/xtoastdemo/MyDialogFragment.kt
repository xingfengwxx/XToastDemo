package com.wangxingxing.xtoastdemo

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.blankj.utilcode.util.ToastUtils

/**
 * author : 王星星
 * date : 2023/2/26 13:14
 * email : 1099420259@qq.com
 * description :
 */
class MyDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // 创建一个 AlertDialog.Builder 对象
        val builder = AlertDialog.Builder(requireActivity())

        // 加载自定义布局文件
        val view = requireActivity().layoutInflater.inflate(R.layout.item_calling, null)

        // 在这里设置自定义布局文件中的控件和属性
        val okButton = view.findViewById<ImageView>(R.id.iv_call_end)
        okButton.setOnClickListener {
            // 点击 OK 按钮时执行的操作
            ToastUtils.showShort("点击取消按钮")
            dismiss() // 关闭对话框
        }

        // 将自定义布局文件设置为对话框的视图
        builder.setView(view)

        builder.setCancelable(false)

        // 创建 AlertDialog 对象
        val alertDialog = builder.create()
        alertDialog.setCanceledOnTouchOutside(false)

        // 获取 Dialog 对应的 Window 对象
        val window = alertDialog.window

        window?.setDimAmount(0f)

        // 设置对话框的位置
        window?.setGravity(Gravity.TOP)

        // 拦截返回键
        alertDialog.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                // 在此处执行拦截返回键时的操作
                Log.i("wxx", "onCreateDialog: 拦截返回键")
                true // 返回 true 表示已经处理了返回键事件
            } else {
                false // 返回 false 表示未处理返回键事件
            }
        }

        return alertDialog
    }
}

