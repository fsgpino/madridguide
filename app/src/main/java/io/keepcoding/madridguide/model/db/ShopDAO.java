package io.keepcoding.madridguide.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

import io.keepcoding.madridguide.manager.db.DBConstants;
import io.keepcoding.madridguide.manager.db.DBHelper;
import io.keepcoding.madridguide.model.Shop;

import static io.keepcoding.madridguide.manager.db.DBConstants.*;

public class ShopDAO implements DAOPersistable<Shop> {

    public static final String[] allColumns = {
            KEY_SHOP_ID,
            KEY_SHOP_ADDRESS,
            KEY_SHOP_IMG_URL,
            KEY_SHOP_NAME

    };

    private WeakReference<Context> context;

    public ShopDAO(@NonNull Context context) {
        this.context = new WeakReference<Context>(context);
    }

    @Override
    public long insert(@NonNull Shop shop) {
        if (shop == null) {
            return 0;
        }
        // insert
        DBHelper dbHelper = DBHelper.getInstance(context.get());
        SQLiteDatabase db = dbHelper.getDB();

        db.beginTransaction();
        long id = DBHelper.INVALID_ID;
        try {
            id = dbHelper.getWritableDatabase().insert(DBConstants.TABLE_SHOP, null, this.getContentValues(shop));
            shop.setId(id);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        dbHelper.close();
        dbHelper=null;

        return id;
    }

    @Override
    public void update(long id, @NonNull Shop shop) {
        if (shop == null) {
            return;
        }

        DBHelper db = DBHelper.getInstance(context.get());

        db.getWritableDatabase().update(TABLE_SHOP, this.getContentValues(shop), KEY_SHOP_ID + "=" + id, null);

        db.close();
        db=null;
    }

    @Override
    public void delete(long id) {
        DBHelper db = DBHelper.getInstance(context.get());

        db.getWritableDatabase().delete(DBConstants.TABLE_SHOP,  DBConstants.KEY_SHOP_ID + " = " + id, null);

        db.close();
        db=null;
    }

    @Override
    public void deleteAll() {
        DBHelper db = DBHelper.getInstance(context.get());

        db.getWritableDatabase().delete(DBConstants.TABLE_SHOP,  null, null);

        db.close();
        db=null;
    }

    public static ContentValues getContentValues(Shop shop) {
        ContentValues content = new ContentValues();
        content.put(KEY_SHOP_NAME, shop.getName());
        //content.put(KEY_NOTEBOOK_ID, shop.getId());
        content.put(KEY_SHOP_ADDRESS, shop.getAddress());
        content.put(KEY_SHOP_DESCRIPTION, shop.getDescription());

        return content;
    }


    // convenience method
    public static Shop shopFromCursor(Cursor c) {
        assert c != null;

        long id = c.getInt(c.getColumnIndex(KEY_SHOP_ID));
        Shop shop = new Shop(id, c.getString(c.getColumnIndex(KEY_SHOP_NAME)));



        return shop;
    }

    public @NonNull Cursor queryCursor() {
        // select
        DBHelper db = DBHelper.getInstance(context.get());

        Cursor c = db.getReadableDatabase().query(TABLE_SHOP, allColumns, null, null, null, null, null);

        return c;
    }


    /**
     * Returns a Notebook object from its id
     * @param id - the notebook id in db
     * @return Notebook object if found, null otherwise
     */
    @Override
    public @Nullable
    Shop query(long id) {
        Shop notebook = null;

        DBHelper db = DBHelper.getInstance(context.get());

        String where = KEY_SHOP_ID + "=" + id;
        Cursor c = db.getReadableDatabase().query(TABLE_SHOP, allColumns, where, null, null, null, null);
        if (c != null) {
            if (c.getCount() > 0) {
                c.moveToFirst();
                notebook = shopFromCursor(c);
            }
        }
        c.close();
        db.close();
        return notebook;
    }



}
