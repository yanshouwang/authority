package dev.yanshouwang.authority

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
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

    private var waiter: Result? = null

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
            "openAppSettings" -> openAppSettingsNative(result)
            else -> result.notImplemented()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>?, grantResults: IntArray?): Boolean {
        return if (requestCode == REQUEST_CODE && waiter != null) {
            val value = grantResults!!.all { result -> result == PackageManager.PERMISSION_GRANTED }
            waiter!!.success(value)
            waiter = null
            true
        } else {
            false
        }
    }

    private fun checkNative(call: MethodCall, result: Result) {
        // ActivityCompat.shouldShowRequestPermissionRationale() always returns false before user make a choice.
        val value = call.permissions.all { permission -> ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED }
        result.success(value)
    }

    private fun requestNative(call: MethodCall, result: Result) {
        if (waiter != null) {
            result.error("AUTHORITY", "Can't make multi requests at the same time.", null)
        } else {
            waiter = result
            ActivityCompat.requestPermissions(activity, call.permissions, REQUEST_CODE)
        }
    }

    private fun openAppSettingsNative(result: Result) {
        val uri = Uri.parse("package:${activity.packageName}")
        val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        activity.startActivity(intent)
        result.success(null)
    }
}
