package com.roy.mark.ui;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by Administrator on 2017/7/21.
 */

public class ClockDialog extends DialogFragment implements DialogInterface.OnClickListener {

    private String title;

    private DialogClickListener listener;

    public interface DialogClickListener {
        void DialogClick();
    }

    public void setDialogClickListener(DialogClickListener listener) {
        this.listener = listener;
    }

    public ClockDialog(String title) {
        this.title = title;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("有待办事项待处理")
                .setMessage(title)
                .setPositiveButton("我知道了", this)
                .setCancelable(false);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dismiss();
        if (listener != null) {
            listener.DialogClick();
        }
    }
}
