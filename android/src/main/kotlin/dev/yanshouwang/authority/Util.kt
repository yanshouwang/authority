package dev.yanshouwang.authority

import io.flutter.plugin.common.MethodCall

val MethodCall.permissions: Array<String>
    get() {
        return when (arguments) {
            0 -> arrayOf()
            1 -> arrayOf()
            2 -> arrayOf()
            else -> throw NotImplementedError()
        }
    }