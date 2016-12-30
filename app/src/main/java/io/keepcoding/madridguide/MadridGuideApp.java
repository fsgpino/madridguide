package io.keepcoding.madridguide;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

import io.keepcoding.madridguide.manager.db.ShopDAO;
import io.keepcoding.madridguide.model.Shop;

public class MadridGuideApp extends Application {
    private static WeakReference<Context> appContext;

    @Override
    public void onCreate() {
        super.onCreate();

        MadridGuideApp.appContext = new WeakReference<Context>(getApplicationContext());

        createShops();

        Picasso.with(getApplicationContext()).setIndicatorsEnabled(true);
        Picasso.with(getApplicationContext()).setLoggingEnabled(true);

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public static Context getAppContext() {
        if (appContext != null) {
            return MadridGuideApp.appContext.get();
        }
        return null;
    }

    @NonNull
    private void createShops() {
        ShopDAO dao = new ShopDAO(this);

        dao.deleteAll();
        for (int i = 0; i < 100; i++) {
            final Shop shop = new Shop(1, "shop " + i).setLogoImgUrl("https://antonitathefantastic.files.wordpress.com/2012/12/marco-fary.jpg");
            dao.insert(shop);
        }
    }
}
