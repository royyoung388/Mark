package com.roy.mark.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.util.Calendar;

/**
 * Created by Administrator on 2017/7/20.
 */

public class MyDatePickerDialog extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener {

    private DateImpl dateImpl;

    public interface DateImpl {
        void setDate(String date);
    }

    public void setDateImpl(DateImpl dateImpl) {
        this.dateImpl = dateImpl;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //得到Calendar类实例，用于获取当前时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //返回DatePickerDialog对象
        //因为实现了OnDateSetListener接口，所以第二个参数直接传入this
        return new android.app.DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        if (dateImpl != null) {
            dateImpl.setDate(year + "-" + (month + 1) + "-" + dayOfMonth);
        }
    }
}
