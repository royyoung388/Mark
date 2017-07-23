package com.roy.mark.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.roy.mark.framework.Matter;
import com.roy.mark.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2017/7/19.
 */

public class DataBaseManager extends DataBaseHelper {

    private static volatile DataBaseManager manager;

    public DataBaseManager(Context context) {
        super(context);
    }

    public DataBaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static DataBaseManager getInstance(Context context) {
        if (manager == null) {
            synchronized (DataBaseManager.class) {
                if (manager == null) {
                    manager = new DataBaseManager(context);
                }
            }
        }
        return manager;
    }

    public boolean insert(Matter matter) {
        try {
            ContentValues values = new ContentValues();
            values.put("title", matter.title);
            values.put("content", matter.content);
            values.put("begin_time", TimeUtil.Date2TimeStamp(matter.beginTime));
            values.put("end_time", TimeUtil.Date2TimeStamp(matter.endTime));
            values.put("finish", matter.finish);
            values.put("priority", matter.priority);

            SQLiteDatabase db = getWritableDatabase();
            db.beginTransaction();
            db.insert(TABLE_NAME, null, values);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Matter> getBySort(String columnName, String order) {
        List<Matter> matters = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, columnName + " " + order);
        while (cursor.moveToNext()) {
            Matter matter = new Matter();
            matter.title = cursor.getString(cursor.getColumnIndex("title"));
            matter.content = cursor.getString(cursor.getColumnIndex("content"));
            matter.beginTime = TimeUtil.TimeStamp2Date(cursor.getLong(cursor.getColumnIndex("begin_time")));
            matter.endTime = TimeUtil.TimeStamp2Date(cursor.getLong(cursor.getColumnIndex("end_time")));
            matter.finish = cursor.getInt(cursor.getColumnIndex("finish")) == 1;
            matter.priority = cursor.getInt(cursor.getColumnIndex("priority"));
            matter.id = cursor.getInt(cursor.getColumnIndex("id"));
            matters.add(matter);
        }
        return matters;
    }

    public Matter getOne(int id) {
        Matter matter = new Matter();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "id = " + id, null, null, null, null);
        while (cursor.moveToNext()) {
            matter.title = cursor.getString(cursor.getColumnIndex("title"));
            matter.content = cursor.getString(cursor.getColumnIndex("content"));
            matter.beginTime = TimeUtil.TimeStamp2Date(cursor.getLong(cursor.getColumnIndex("begin_time")));
            matter.endTime = TimeUtil.TimeStamp2Date(cursor.getLong(cursor.getColumnIndex("end_time")));
            matter.finish = cursor.getInt(cursor.getColumnIndex("finish")) == 1;
            matter.priority = cursor.getInt(cursor.getColumnIndex("priority"));
            matter.id = cursor.getInt(cursor.getColumnIndex("id"));
        }
        return matter;
    }

    public boolean updatePriority(int id, int priority) {
        try {
            ContentValues values = new ContentValues();
            values.put("priority", priority);

            SQLiteDatabase db = getWritableDatabase();
            db.beginTransaction();
            db.update(TABLE_NAME, values, "id = " + id, null);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateEndTime(int id, String date) {
        try {
            ContentValues values = new ContentValues();
            values.put("end_time", TimeUtil.Date2TimeStamp(date));

            SQLiteDatabase db = getWritableDatabase();
            db.beginTransaction();
            db.update(TABLE_NAME, values, "id = " + id, null);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateFinish(int id, boolean finish) {
        try {
            ContentValues values = new ContentValues();
            values.put("finish", finish);

            SQLiteDatabase db = getWritableDatabase();
            db.beginTransaction();
            db.update(TABLE_NAME, values, "id = " + id, null);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean delete(int id) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.beginTransaction();
            db.delete(TABLE_NAME, "id = " + id, null);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
