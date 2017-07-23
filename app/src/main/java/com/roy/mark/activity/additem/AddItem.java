package com.roy.mark.activity.additem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.roy.mark.R;
import com.roy.mark.activity.MainActivity;
import com.roy.mark.framework.Matter;
import com.roy.mark.provider.DataBaseManager;
import com.roy.mark.ui.MyDatePickerDialog;
import com.roy.mark.utils.TimeUtil;

/**
 * Created by Administrator on 2017/7/21.
 */

public class AddItem extends AppCompatActivity {

    private EditText title, content;
    private Spinner priority;
    private TextView beginTime, endTime;

    private String strBeginTime, strEndTime, strTitle, strContent;
    private int nPriority = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);

        init();
        setToolbar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_item, menu);
        return true;
    }

    private void init() {
        title = (EditText) findViewById(R.id.add_title);
        content = (EditText) findViewById(R.id.add_content);
        priority = (Spinner) findViewById(R.id.add_priority);
        beginTime = (TextView) findViewById(R.id.add_begin_time);
        endTime = (TextView) findViewById(R.id.add_end_time);

        strBeginTime = TimeUtil.getNowDate();
        strEndTime = strBeginTime;
        beginTime.setText(strBeginTime);
        endTime.setText(strEndTime);

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatePickerDialog datePicker = new MyDatePickerDialog();
                datePicker.show(getFragmentManager(), "date_picker");
                getFragmentManager();
                datePicker.setDateImpl(new MyDatePickerDialog.DateImpl() {
                    @Override
                    public void setDate(String date) {
                        endTime.setText(date);
                        strEndTime = date;
                    }
                });
            }
        });

        priority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nPriority = Integer.parseInt(priority.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.add_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("添加事项");

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add_item:
                        if (addItem()) {
                            Toast.makeText(AddItem.this, "添加成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(AddItem.this, "输入有误，请检查输入", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                return true;
            }
        });
    }

    private boolean addItem() {
        strTitle = title.getText().toString();
        strContent = content.getText().toString();
        Matter matter = new Matter(strTitle, strContent, strBeginTime, strEndTime, nPriority, false);
        return DataBaseManager.getInstance(this).insert(matter);
    }

}
