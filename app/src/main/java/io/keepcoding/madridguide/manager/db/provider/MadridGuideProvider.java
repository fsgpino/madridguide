package io.keepcoding.madridguide.manager.db.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import io.keepcoding.madridguide.manager.db.ActivityDAO;
import io.keepcoding.madridguide.manager.db.ShopDAO;
import io.keepcoding.madridguide.model.Activity;
import io.keepcoding.madridguide.model.Shop;

public class MadridGuideProvider extends ContentProvider {
    public static final String MADRIDGUIDE_PROVIDER = "io.keepcoding.madridguide.provider";

    public static final Uri SHOPS_URI = Uri.parse("content://" + MADRIDGUIDE_PROVIDER + "/shops");
    public static final Uri ACTIVITIES_URI = Uri.parse("content://" + MADRIDGUIDE_PROVIDER + "/activities");

    // Create the constants used to differentiate between the different URI requests.
    private static final int ALL_SHOPS = 1;
    private static final int SINGLE_SHOP = 2;
    private static final int ALL_ACTIVITIES = 3;
    private static final int SINGLE_ACTIVITY = 4;

    private static final UriMatcher uriMatcher;
    // Populate the UriMatcher object, where a URI ending in ‘elements’ will correspond to a request for all items, and ‘elements/[rowID]’ represents a single row.
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        // content://io.keepcoding.madridguide.provider/shops
        uriMatcher.addURI(MADRIDGUIDE_PROVIDER, "shops", ALL_SHOPS);
        // content://io.keepcoding.madridguide.provider/shops/363
        uriMatcher.addURI(MADRIDGUIDE_PROVIDER, "shops/#", SINGLE_SHOP);
        // content://io.keepcoding.madridguide.provider/activities
        uriMatcher.addURI(MADRIDGUIDE_PROVIDER, "activities", ALL_ACTIVITIES);
        // content://io.keepcoding.madridguide.provider/activities/363
        uriMatcher.addURI(MADRIDGUIDE_PROVIDER, "activities/#", SINGLE_ACTIVITY);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        ShopDAO shopDAO = new ShopDAO(getContext());
        ActivityDAO activityDAO = new ActivityDAO(getContext());

        Cursor cursor = null;
        String rowID = null;
        switch (uriMatcher.match(uri)) {
            case SINGLE_SHOP :
                rowID = uri.getPathSegments().get(1);
                cursor = shopDAO.queryCursor(Long.parseLong(rowID));
                break;
            case ALL_SHOPS :
                cursor = shopDAO.queryCursor();
                break;
            case SINGLE_ACTIVITY :
                rowID = uri.getPathSegments().get(1);
                cursor = activityDAO.queryCursor(Long.parseLong(rowID));
                break;
            case ALL_ACTIVITIES :
                cursor = activityDAO.queryCursor();
                break;
            default: break;
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        String type = null;

        switch (uriMatcher.match(uri)) {
            case SINGLE_SHOP : case SINGLE_ACTIVITY :
                type = "vnd.android.cursor.item/vnd.io.keepcoding.madridguide.provider";
                break;
            case ALL_SHOPS: case ALL_ACTIVITIES :
                type = "vnd.android.cursor.dir/vnd.io.keepcoding.madridguide.provider";
                break;
            default:
                break;
        }

        return type;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        // content://io.keepcoding.madridguide.provider/shops
        // content://io.keepcoding.madridguide.provider/activities

        Uri insertedUri = null;
        long id = -1;
        switch (uriMatcher.match(uri)) {
            case ALL_SHOPS:
                ShopDAO shopDAO = new ShopDAO(getContext());

                Shop shop = ShopDAO.getShopFromContentValues(contentValues);

                id = shopDAO.insert(shop);
                if (id == -1) {
                    return null;
                }

                // content://io.keepcoding.madridguide.provider/shops/5353

                // Construct and return the URI of the newly inserted row.
                insertedUri = ContentUris.withAppendedId(SHOPS_URI, id);
                break;
            case ALL_ACTIVITIES:
                ActivityDAO activityDAO = new ActivityDAO(getContext());

                Activity activity = ActivityDAO.getActivityFromContentValues(contentValues);

                id = activityDAO.insert(activity);
                if (id == -1) {
                    return null;
                }

                // content://io.keepcoding.madridguide.provider/activities/5353

                // Construct and return the URI of the newly inserted row.
                insertedUri = ContentUris.withAppendedId(ACTIVITIES_URI, id);
                break;
            default:
                break;
        }

        // Notify any observers of the change in the data set.
        getContext().getContentResolver().notifyChange(uri, null);
        getContext().getContentResolver().notifyChange(insertedUri, null);

        return insertedUri;
    }

    @Override
    public int delete(Uri uri, String where, String[] whereSelection) {
        // content://io.keepcoding.madridguide.provider/shops/72
        // content://io.keepcoding.madridguide.provider/activity/57

        ShopDAO shopDAO = new ShopDAO(getContext());
        ActivityDAO activityDAO = new ActivityDAO(getContext());
        int deleteCount = 0;
        String rowID = null;

        // If this is a row URI, limit the deletion to the specified row.
        switch (uriMatcher.match(uri)) {
            case SINGLE_SHOP:
                rowID = uri.getPathSegments().get(1);
                deleteCount = shopDAO.delete(Long.parseLong(rowID));
                break;
            case ALL_SHOPS:
                shopDAO.deleteAll();
                break;
            case SINGLE_ACTIVITY :
                rowID = uri.getPathSegments().get(1);
                deleteCount = activityDAO.delete(Long.parseLong(rowID));
                break;
            case ALL_ACTIVITIES :
                activityDAO.deleteAll();
                break;
            default:
                break;
        }

        // Notify any observers of the change in the data set.
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the number of deleted items.
        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
