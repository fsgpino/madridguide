package io.keepcoding.madridguide;

import android.support.annotation.NonNull;
import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.List;

import io.keepcoding.madridguide.model.Shop;
import io.keepcoding.madridguide.model.Shops;

public class ShopsTests extends AndroidTestCase {

    private static final String SHOP = "shop";
    private static final String ADDRESS = "ADDRESS";

    public void testCreatingAShopsWithNullListReturnsNonNullShops() {
        Shops sut = Shops.build(null);
        assertNotNull(sut);
        assertNotNull(sut.allElements());
    }

    public void testCreatingAShopsWithAListReturnsNonNullShops() {
        List<Shop> data = getShops();

        Shops sut = Shops.build(data);
        assertNotNull(sut);
        assertNotNull(sut.allElements());
        assertEquals(sut.allElements(), data);
        assertEquals(sut.allElements().size(), data.size());
    }

    @NonNull
    private List<Shop> getShops() {
        List<Shop> data = new ArrayList<>();
        data.add(new Shop(1, SHOP).setAddress(ADDRESS));
        data.add(new Shop(2, SHOP).setAddress(ADDRESS));
        return data;
    }

}
