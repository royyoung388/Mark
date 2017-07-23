package com.roy.mark.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.roy.mark.R;
import com.roy.mark.activity.additem.AddItem;
import com.roy.mark.receiver.ClockAlarmReciver;
import com.roy.mark.ui.MyDatePickerDialog;
import com.roy.mark.ui.MyAdapter;
import com.roy.mark.provider.DataBaseManager;
import com.roy.mark.framework.Matter;
import com.roy.mark.ui.MyViewHolder;
import com.roy.mark.utils.TimeUtil;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private MyAdapter myAdapter;
    private Spinner sortNmae, sortOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setToolBar();
        setRecyclerView();
        setClockAlarms();
        setSpinner();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (myAdapter != null) {
            myAdapter.update(DataBaseManager.getInstance(this).getBySort("id", "ASC"));
        }
    }


    private void setSpinner() {
        sortNmae.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = sortNmae.getSelectedItem().toString();
                String sort = sortOrder.getSelectedItem().toString();
                myAdapter.update(DataBaseManager.getInstance(MainActivity.this).getBySort(name, sort));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        sortOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = sortNmae.getSelectedItem().toString();
                String sort = sortOrder.getSelectedItem().toString();
                myAdapter.update(DataBaseManager.getInstance(MainActivity.this).getBySort(name, sort));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setClockAlarms() {
        List<Matter> matters = DataBaseManager.getInstance(this).getBySort("id", "ASC");
        for (Matter matter : matters) {
            setClockAlarm(matter);
        }
    }

    private void setClockAlarm(Matter matter) {
        if (matter.finish) {
            return;
        }

        System.out.println("设置闹钟");
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, ClockAlarmReciver.class);
        intent.putExtra("title", matter.title);
        PendingIntent send = PendingIntent.getBroadcast(this, matter.id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();

        if (Build.VERSION.SDK_INT < 19) {
            am.set(AlarmManager.RTC_WAKEUP, TimeUtil.Date2Mills(matter.endTime), send);
        } else {
            am.setExact(AlarmManager.RTC_WAKEUP, TimeUtil.Date2Mills(matter.endTime), send);
        }
    }

    private void init() {
        recycler = (RecyclerView) findViewById(R.id.main_recyclerview);
        sortNmae = (Spinner) findViewById(R.id.main_sort_name);
        sortOrder = (Spinner) findViewById(R.id.main_sort_order);
    }

    private void setRecyclerView() {
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setHasFixedSize(true);

        final List<Matter> matters = DataBaseManager.getInstance(this).getBySort("id", "ASC");

        myAdapter = new MyAdapter(this, matters);
        //接口回调，截止时间的选择器
        myAdapter.setOnItemViewClickListener(new MyAdapter.OnItemViewClickListener() {
            //监听 截止时间
            @Override
            public void OnEndTimeClick(final MyViewHolder viewHolder) {
                MyDatePickerDialog datePicker = new MyDatePickerDialog();
                datePicker.show(getFragmentManager(), "date_picker");
                getFragmentManager();
                datePicker.setDateImpl(new MyDatePickerDialog.DateImpl() {
                    @Override
                    public void setDate(String date) {
                        //显示时间，更新数据库,设置闹钟
                        viewHolder.endTime.setText(date);
                        DataBaseManager.getInstance(MainActivity.this).updateEndTime((int) viewHolder.endTime.getTag(), date);
                        Matter matter = DataBaseManager.getInstance(MainActivity.this).getOne((Integer) viewHolder.endTime.getTag());
                        setClockAlarm(matter);
                    }
                });
            }

            //监听 完成项
            @Override
            public void OnFinishClick(MyViewHolder viewHolder, boolean isChecked) {
                //更新数据库，设置闹钟
                DataBaseManager.getInstance(MainActivity.this).updateFinish((int) viewHolder.endTime.getTag(), isChecked);
                Matter matter = DataBaseManager.getInstance(MainActivity.this).getOne((Integer) viewHolder.endTime.getTag());
                setClockAlarm(matter);
            }

            @Override
            public void OnPriorityClick(MyViewHolder viewHolder, int priority) {
                //更新数据库
                DataBaseManager.getInstance(MainActivity.this).updatePriority((int) viewHolder.endTime.getTag(), priority);
                Matter matter = DataBaseManager.getInstance(MainActivity.this).getOne((Integer) viewHolder.endTime.getTag());
            }

            @Override
            public void OnCardViewLongClick(MyViewHolder viewHolder) {
                //删除数据
                if (DataBaseManager.getInstance(MainActivity.this).delete((Integer) viewHolder.endTime.getTag())) {
                    Toast.makeText(MainActivity.this, "删除数据成功", Toast.LENGTH_SHORT).show();
                    myAdapter.update(DataBaseManager.getInstance(MainActivity.this).getBySort("id", "ASC"));
                } else {
                    Toast.makeText(MainActivity.this, "删除数据失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
        recycler.setAdapter(myAdapter);
    }

    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle("待办事项");
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main_add:
                        startActivity(new Intent(MainActivity.this, AddItem.class));
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
