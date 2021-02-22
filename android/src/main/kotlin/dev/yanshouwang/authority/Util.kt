package dev.yanshouwang.authority

import android.Manifest
import io.flutter.plugin.common.MethodCall

val MethodCall.permissions: Array<String>
    get() {
        return when (arguments) {
            0 -> arrayOf(Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR)
            1 -> arrayOf(Manifest.permission.CAMERA)
            2 -> arrayOf(Manifest.permission.GET_ACCOUNTS, Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)
            3 -> if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            } else {
                arrayOf()
            }
            4 -> arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
            5 -> arrayOf(Manifest.permission.RECORD_AUDIO)
            6 -> when {
                android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q -> {
                    arrayOf(Manifest.permission.ADD_VOICEMAIL, Manifest.permission.CALL_PHONE, Manifest.permission.READ_CALL_LOG,
                            Manifest.permission.READ_PHONE_STATE, Manifest.permission.USE_SIP, Manifest.permission.WRITE_CALL_LOG,
                            Manifest.permission.ANSWER_PHONE_CALLS, Manifest.permission.BIND_CALL_REDIRECTION_SERVICE)
                }
                android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O -> {
                    arrayOf(Manifest.permission.ADD_VOICEMAIL, Manifest.permission.CALL_PHONE, Manifest.permission.READ_CALL_LOG,
                            Manifest.permission.READ_PHONE_STATE, Manifest.permission.USE_SIP, Manifest.permission.WRITE_CALL_LOG,
                            Manifest.permission.ANSWER_PHONE_CALLS)
                }
                else -> {
                    arrayOf(Manifest.permission.ADD_VOICEMAIL, Manifest.permission.CALL_PHONE, Manifest.permission.READ_CALL_LOG,
                            Manifest.permission.READ_PHONE_STATE, Manifest.permission.USE_SIP, Manifest.permission.WRITE_CALL_LOG)
                }
            }
            7 -> if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
                arrayOf(Manifest.permission.BODY_SENSORS)
            } else {
                arrayOf()
            }
            8 -> arrayOf(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_MMS, Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.RECEIVE_WAP_PUSH, Manifest.permission.SEND_SMS)
            9 -> arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            else -> arrayOf()
        }
    }