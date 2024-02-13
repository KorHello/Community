package com.android.community.utils

import android.util.Log

class Utils {
    companion object {
        fun log(log: String?) {
            if (log != null) {
                val stacks = Throwable().stackTrace
                val currentStack = stacks[1]
                val strMsg = (currentStack.fileName + "(" + currentStack.lineNumber + ")::"
                        + currentStack.methodName + " - " + log)
                Log.d("Utils", strMsg)
            }
        }

        fun elog(log: String?) {
            if (log != null) {
                val stacks = Throwable().stackTrace
                val currentStack = stacks[1]
                val strMsg = (currentStack.fileName + "(" + currentStack.lineNumber + ")::"
                        + currentStack.methodName + " - " + log)
                Log.e("Utils", strMsg)
            }
        }

        fun log(TAG: String?, log: String?) {
            if (log != null) {
                val stacks = Throwable().stackTrace
                val currentStack = stacks[1]
                val strMsg = (currentStack.fileName + "(" + currentStack.lineNumber + ")::"
                        + currentStack.methodName + " - " + log)
                Log.d(TAG, strMsg)
            }
        }
    }

}