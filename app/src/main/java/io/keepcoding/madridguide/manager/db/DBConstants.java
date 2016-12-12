package io.keepcoding.madridguide.manager.db;

public class DBConstants {

    public static final String TABLE_SHOP = "SHOP";

    // Table field constants
    public static final String KEY_SHOP_ID = "_id";
    public static final String KEY_SHOP_NAME = "name";
    public static final String KEY_SHOP_IMG_URL = "img_url";
    public static final String KEY_SHOP_LOGO_IMG_URL = "logo_img_url";
    public static final String KEY_SHOP_ADDRESS = "address";
    public static final String KEY_SHOP_URL = "logo_img_url";
    public static final String KEY_SHOP_LATITUDE = "latitude";
    public static final String KEY_SHOP_LONGITUDE = "longitude";
    public static final String KEY_SHOP_DESCRIPTION = "description";


    public static final String SQL_SCRIPT_CREATE_NOTE_TABLE =
            "create table "
                    + TABLE_SHOP + "( " + KEY_SHOP_ID + " integer primary key autoincrement, "
                    + KEY_SHOP_NAME + " text not null,"
                    + KEY_SHOP_IMG_URL + " text, "
                    + KEY_SHOP_LOGO_IMG_URL + " text, "
                    + KEY_SHOP_ADDRESS + " text,"
                    + KEY_SHOP_URL + " text,"
                    + KEY_SHOP_LATITUDE + " real,"
                    + KEY_SHOP_LONGITUDE + " real, "
                    + KEY_SHOP_DESCRIPTION + " text "
                    + ");";

    public static final String[] CREATE_DATABASE_SCRIPTS = {
            SQL_SCRIPT_CREATE_NOTE_TABLE
    };

    public static final String DROP_DATABASE_SCRIPTS = "";

}
