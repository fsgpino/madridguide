package io.keepcoding.madridguide.interactors;

import android.content.Context;
import android.os.Looper;

import io.keepcoding.madridguide.manager.db.ShopDAO;
import io.keepcoding.madridguide.model.Shop;
import io.keepcoding.madridguide.model.Shops;

public class CacheAllShopsInteractor {
    public void execute(final Context context, final Shops shops, final CacheAllElementsInteractorResponse response) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ShopDAO dao = new ShopDAO(context);

                if (shops == null) {
                    return;
                }

                boolean success = true;
                for (Shop shop: shops.allElements()) {
                    success = dao.insert(shop) > 0;
                    if (!success) {
                        break;
                    }
                }

                Looper main = Looper.getMainLooper();
                // TODO: put on Main Thread
                if (response != null) {
                    response.response(success);
                }
            }
        }).start();
    }
}
