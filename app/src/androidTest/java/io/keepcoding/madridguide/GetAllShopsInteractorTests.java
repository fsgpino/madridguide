package io.keepcoding.madridguide;

import android.test.AndroidTestCase;

import io.keepcoding.madridguide.interactors.GetAllShopsInteractor;
import io.keepcoding.madridguide.interactors.GetAllShopsInteractorFakeImpl;
import io.keepcoding.madridguide.interactors.GetAllShopsInteractorResponse;
import io.keepcoding.madridguide.model.Shop;
import io.keepcoding.madridguide.model.Shops;

// deprecated: https://developer.android.com/topic/libraries/testing-support-library/index.html
public class GetAllShopsInteractorTests extends AndroidTestCase {

    public void testCanCreateInteractor() {
        GetAllShopsInteractor getAllShopsInteractor = new GetAllShopsInteractorFakeImpl();

        getAllShopsInteractor.execute(new GetAllShopsInteractorResponse() {
            @Override
            public void response(Shops shops) {
                assertNotNull(shops);
                assertTrue(shops.size() > 0);

                Shop shop = shops.get(1);
                assertEquals(shop.getId(), 2);
            }
        });
    }

}
