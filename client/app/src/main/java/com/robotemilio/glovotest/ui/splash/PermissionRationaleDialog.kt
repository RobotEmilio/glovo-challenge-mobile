package com.robotemilio.glovotest.ui.splash

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.robotemilio.glovotest.R

class PermissionRationaleDialog constructor(
    context: Context,
    onRetry: () -> Unit,
    onDeny: () -> Unit
) : AlertDialog(context) {

    init {
        setTitle(context.getString(R.string.dialog_gps_permission_title))
        setMessage(context.getString(R.string.dialog_gps_permission_message))
        setButton(AlertDialog.BUTTON_NEGATIVE, context.getString(R.string.dialog_deny)) { _: DialogInterface, _: Int ->
            dismiss()
            onDeny()
        }
        setButton(AlertDialog.BUTTON_POSITIVE, context.getString(R.string.dialog_retry)) { _: DialogInterface, _: Int ->
            dismiss()
            onRetry()
        }
    }

}