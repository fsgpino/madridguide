package io.keepcoding.madridguide.manager.net;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.io.StringReader;
import java.lang.ref.WeakReference;
import java.util.List;

import io.keepcoding.madridguide.R;

public class NetworkManager {

    public interface GetShopsListener {
        public void getShopEntitiesSuccess(List<ShopEntity> result);
        public void getShopEntitiesDidFail();
    }

    public interface GetActivitiesListener {
        public void getActivityEntitiesSuccess(List<ActivityEntity> result);
        public void getActivityEntitiesDidFail();
    }

    WeakReference<Context> context;

    public NetworkManager(Context context) {
        this.context = new WeakReference<Context>(context);
    }

    public void getShopsFromServer(final GetShopsListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context.get());
        String url = context.get().getString(R.string.shops_url);

        StringRequest request = new StringRequest(
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("JSON", response);
                        List<ShopEntity> shopResponse = parseShopsResponse(response);
                        if (listener != null) {
                            listener.getShopEntitiesSuccess(shopResponse);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (listener != null) {
                            listener.getShopEntitiesDidFail();
                        }
                    }
                }
        );
        queue.add(request);
    }

    public void getActivitiesFromServer(final GetActivitiesListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context.get());
        String url = context.get().getString(R.string.activities_url);

        StringRequest request = new StringRequest(
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("JSON", response);
                        List<ActivityEntity> activityResponse = parseActivitiesResponse(response);
                        if (listener != null) {
                            listener.getActivityEntitiesSuccess(activityResponse);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (listener != null) {
                            listener.getActivityEntitiesDidFail();
                        }
                    }
                }
        );
        queue.add(request);
    }

    private List<ShopEntity> parseShopsResponse(String response) {
        List<ShopEntity> result = null;
        try {
            Reader reader = new StringReader(response);
            Gson gson = new GsonBuilder().create();

            ShopResponse shopResponse = gson.fromJson(reader, ShopResponse.class);
            result = shopResponse.result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private List<ActivityEntity> parseActivitiesResponse(String response) {
        List<ActivityEntity> result = null;
        try {
            Reader reader = new StringReader(response);
            Gson gson = new GsonBuilder().create();

            ActivityResponse activityResponse = gson.fromJson(reader, ActivityResponse.class);
            result = activityResponse.result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
