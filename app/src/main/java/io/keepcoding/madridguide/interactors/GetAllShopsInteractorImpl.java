package io.keepcoding.madridguide.interactors;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.keepcoding.madridguide.model.Shop;
import io.keepcoding.madridguide.model.Shops;

public class GetAllShopsInteractorImpl implements GetAllShopsInteractor {

    @Override
    public void execute(GetAllShopsInteractorResponse response) {
        List<Shop> data = getShops();

        Shops sut = Shops.build(data);

        response.response(sut);
    }

    @NonNull
    private List<Shop> getShops() {
        List<Shop> data = new ArrayList<>();
        data.add(new Shop(1, "1").setAddress("AD 1"));
        data.add(new Shop(2, "2").setAddress("AD 2"));
        return data;
    }
}
