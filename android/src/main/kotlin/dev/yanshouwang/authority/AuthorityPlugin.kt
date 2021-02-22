package dev.yanshouwang.authority

import android.content.pm.PackageManager
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry

/** AuthorityPlugin */
class AuthorityPlugin : FlutterPlugin, ActivityAware, MethodCallHandler, PluginRegistry.RequestPermissionsResultListener {
    companion object {
        private const val REQUEST_CODE = 1993
    }

    private lateinit var activityPluginBinding: ActivityPluginBinding
    private lateinit var method: MethodChannel

    private var result: Result? = null

    private val activity get() = activityPluginBinding.activity

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        method = MethodChannel(flutterPluginBinding.binaryMessenger, "yanshouwang.dev/authority/method")
        method.setMethodCallHandler(this)
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        method.setMethodCallHandler(null)
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        activityPluginBinding = binding
        activityPluginBinding.addRequestPermissionsResultListener(this)
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        onAttachedToActivity(binding)
    }

    override fun onDetachedFromActivity() {
        activityPluginBinding.removeRequestPermissionsResultListener(this)
    }

    override fun onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity()
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        when (call.method) {
            "check" -> checkNative(call, result)
            "request" -> requestNative(call, result)
            else -> result.notImplemented()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>?, grantResults: IntArray?): Boolean {
        if (requestCode == REQUEST_CODE && result != null) {
            val value = grantResults!!.all { item -> item == PackageManager.PERMISSION_GRANTED }
            result!!.success(value)
            result = null
            return true
        } else {
            return false
        }
    }

    private fun checkNative(call: MethodCall, result: Result) {
        val value = call.permissions.all { item -> ContextCompat.checkSelfPermission(activity, item) == PackageManager.PERMISSION_GRANTED }
        result.success(value)
    }

    private fun requestNative(call: MethodCall, result: Result) {
        // ANDROID DOESN'T SUPPORT MULTI REQUESTS AT THE SAME TIME.
        if (this.result != null) {
            result.success(false)
        } else {
            this.result = result
            ActivityCompat.requestPermissions(activity, call.permissions, REQUEST_CODE)
        }
    }
}
