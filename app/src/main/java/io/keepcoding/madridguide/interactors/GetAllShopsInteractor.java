package io.keepcoding.madridguide.interactors;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.List;

import io.keepcoding.madridguide.activities.ShopsActivity;
import io.keepcoding.madridguide.manager.db.ShopDAO;
import io.keepcoding.madridguide.manager.net.NetworkManager;
import io.keepcoding.madridguide.manager.net.ShopEntity;
import io.keepcoding.madridguide.model.Shop;
import io.keepcoding.madridguide.model.Shops;
import io.keepcoding.madridguide.model.mappers.ShopEntityShopMapper;
import io.keepcoding.madridguide.navigator.Navigator;
import io.keepcoding.madridguide.util.CacheValidator;
import io.keepcoding.madridguide.util.OnElementClick;

public class GetAllShopsInteractor implements IGetAllElementsInteractor<Shops> {
    public void execute(final Context context, final GetAllElementsInteractorResponse<Shops> response) {

        NetworkManager networkManager = new NetworkManager(context);
        networkManager.getShopsFromServer(new NetworkManager.GetShopsListener() {
            @Override
            public void getShopEntitiesSuccess(List<ShopEntity> result) {
                List<Shop> shops = new ShopEntityShopMapper().map(result);
                if (response != null) {
                    response.response(Shops.build(shops));
                }
                CacheValidator.resetShopsCacheTime(context);
            }

            @Override
            public void getShopEntitiesDidFail() {
                if (response != null) {
                    response.response(null);
                }
            }
        });

    }
}
