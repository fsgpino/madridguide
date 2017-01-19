package io.keepcoding.madridguide.manager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

import io.keepcoding.madridguide.model.Activity;
import io.keepcoding.madridguide.model.Activities;

import static io.keepcoding.madridguide.manager.db.DBConstants.*;

public class ActivityDAO implements DAOPersistable<Activity> {
    private WeakReference<Context> context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public ActivityDAO(Context context, DBHelper dbHelper) {
        this.context = new WeakReference<Context>(context);
        this.dbHelper = dbHelper;
        this.db = dbHelper.getDB();
    }

    public ActivityDAO(Context context) {
        this(context, DBHelper.getInstance(context));
    }

    /**
     * Insert a activity in DB
     * @param activity shouldn't be null
     * @return 0 is activity is null, id if insert is OK, INVALID_ID if insert fails
     */
    @Override
    public long insert(@NonNull Activity activity) {
        if (activity == null) {
            return 0;
        }
        // insert

        db.beginTransaction();
        long id = DBHelper.INVALID_ID;
        try { // Null Column Hack
            id = db.insert(TABLE_ACTIVITY, KEY_ACTIVITY_NAME, this.getContentValues(activity));
            activity.setId(id);
            db.setTransactionSuccessful();  // COMMIT
        } finally {
            db.endTransaction();
        }

        return id;
    }

    public static @NonNull ContentValues getContentValues(final @NonNull Activity activity) {
        final ContentValues contentValues = new ContentValues();

        if (activity == null) {
            return contentValues;
        }

        contentValues.put(KEY_ACTIVITY_NAME, activity.getName());
        contentValues.put(KEY_ACTIVITY_ADDRESS, activity.getAddress());
        contentValues.put(KEY_ACTIVITY_DESCRIPTION, activity.getDescription());
        contentValues.put(KEY_ACTIVITY_IMAGE_URL, activity.getImageUrl());
        contentValues.put(KEY_ACTIVITY_LOGO_IMAGE_URL, activity.getLogoImgUrl());
        contentValues.put(KEY_ACTIVITY_LATITUDE, activity.getLatitude());
        contentValues.put(KEY_ACTIVITY_LONGITUDE, activity.getLongitude());
        contentValues.put(KEY_ACTIVITY_URL, activity.getUrl());

        return contentValues;
    }

    public static @NonNull Activity getActivityFromContentValues(final @NonNull ContentValues contentValues) {
        final Activity activity = new Activity(1, "");

        //activity.setId(contentValues.getAsInteger(KEY_ACTIVITY_ID));
        activity.setName(contentValues.getAsString(KEY_ACTIVITY_NAME));
        activity.setAddress(contentValues.getAsString(KEY_ACTIVITY_ADDRESS));
        activity.setDescription(contentValues.getAsString(KEY_ACTIVITY_DESCRIPTION));
        activity.setImageUrl(contentValues.getAsString(KEY_ACTIVITY_IMAGE_URL));
        activity.setLogoImgUrl(contentValues.getAsString(KEY_ACTIVITY_LOGO_IMAGE_URL));
        activity.setUrl(contentValues.getAsString(KEY_ACTIVITY_URL));
        activity.setLatitude(contentValues.getAsFloat(KEY_ACTIVITY_LATITUDE));
        activity.setLongitude(contentValues.getAsFloat(KEY_ACTIVITY_LONGITUDE));

        return activity;
    }

    @Override
    public void update(long id, @NonNull Activity data) {

    }

    @Override
    public int delete(long id) {
        return db.delete(TABLE_ACTIVITY, KEY_ACTIVITY_ID + " = " + id, null);  // 1st way
        // db.delete(TABLE_ACTIVITY, KEY_ACTIVITY_ID + " = ?", new String[]{ "" + id });  // 2nd way
        //db.delete(TABLE_ACTIVITY, KEY_ACTIVITY_ID + " = ? AND " + KEY_ACTIVITY_NAME + "= ?" ,
        //        new String[]{ "" + id, "pepito" });  // 2nd way

    }

    @Override
    public void deleteAll() {
        db.delete(TABLE_ACTIVITY, null, null);
    }

    @Nullable
    @Override
    public Cursor queryCursor() {
        Cursor c = db.query(TABLE_ACTIVITY, ALL_ACTIVITY_COLUMNS, null, null, null, null, KEY_ACTIVITY_ID);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
        }
        return c;
    }

    @Override
    public @Nullable Activity query(final long id) {
        Cursor c = db.query(TABLE_ACTIVITY, ALL_ACTIVITY_COLUMNS, KEY_ACTIVITY_ID + " = " + id, null, null, null, KEY_ACTIVITY_ID);

        if (c != null && c.getCount() == 1) {
            c.moveToFirst();
        } else {
            return null;
        }

        Activity activity = getActivity(c);

        return activity;
    }

    @NonNull
    public static Activity getActivity(Cursor c) {
        long identifier = c.getLong(c.getColumnIndex(KEY_ACTIVITY_ID));
        String name = c.getString(c.getColumnIndex(KEY_ACTIVITY_NAME));
        Activity activity = new Activity(identifier, name);

        activity.setAddress(c.getString(c.getColumnIndex(KEY_ACTIVITY_ADDRESS)));
        activity.setDescription(c.getString(c.getColumnIndex(KEY_ACTIVITY_DESCRIPTION)));
        activity.setImageUrl(c.getString(c.getColumnIndex(KEY_ACTIVITY_IMAGE_URL)));
        activity.setLogoImgUrl(c.getString(c.getColumnIndex(KEY_ACTIVITY_LOGO_IMAGE_URL)));
        activity.setLatitude(c.getFloat(c.getColumnIndex(KEY_ACTIVITY_LATITUDE)));
        activity.setLongitude(c.getFloat(c.getColumnIndex(KEY_ACTIVITY_LONGITUDE)));
        activity.setUrl(c.getString(c.getColumnIndex(KEY_ACTIVITY_URL)));
        return activity;
    }

    @Nullable
    @Override
    public List<Activity> query() {
        Cursor c = queryCursor();

        if (c == null || !c.moveToFirst()) {
            return null;
        }

        List<Activity> activities = new LinkedList<>();

        c.moveToFirst();
        do {
            Activity activity = getActivity(c);
            activities.add(activity);
        } while (c.moveToNext());

        return activities;
    }

    public Cursor queryCursor(long id) {
        Cursor c = db.query(TABLE_ACTIVITY, ALL_ACTIVITY_COLUMNS, "ID = " + id, null, null, null, KEY_ACTIVITY_ID);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
        }
        return c;
    }

    @NonNull
    public static Activities getActivities(Cursor data) {
        List<Activity> activityList = new LinkedList<>();

        while (data.moveToNext()) {
            Activity activity = ActivityDAO.getActivity(data);
            activityList.add(activity);
        }

        return Activities.build(activityList);
    }
}
