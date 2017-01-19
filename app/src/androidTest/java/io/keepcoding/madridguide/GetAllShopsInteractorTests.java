package io.keepcoding.madridguide;

import android.test.AndroidTestCase;

import io.keepcoding.madridguide.interactors.GetAllShopsInteractorFakeImpl;
import io.keepcoding.madridguide.interactors.GetAllElementsInteractorResponse;
import io.keepcoding.madridguide.interactors.IGetAllElementsInteractor;
import io.keepcoding.madridguide.model.Shop;
import io.keepcoding.madridguide.model.Shops;

// deprecated: https://developer.android.com/topic/libraries/testing-support-library/index.html
public class GetAllShopsInteractorTests extends AndroidTestCase {

    public void testCanCreateInteractor() {
        IGetAllElementsInteractor getAllShopsInteractor = new GetAllShopsInteractorFakeImpl();

        getAllShopsInteractor.execute(getContext(), new GetAllElementsInteractorResponse<Shops>() {
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
