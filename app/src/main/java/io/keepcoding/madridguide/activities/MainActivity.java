package io.keepcoding.madridguide.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.keepcoding.madridguide.R;
import io.keepcoding.madridguide.navigator.Navigator;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button_activity_main_open_shops)
    Button openShopsButton;

    @BindView(R.id.button_activity_main_open_activities)
    Button openActivitiesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ButterKnife.setDebug(true);
        ButterKnife.bind(this);

        setupShopsButton();

        setTitle(R.string.app_name);

        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            Toast.makeText(MainActivity.this, R.string.no_internet_connection, Toast.LENGTH_LONG).show();
        }

    }

    private void setupShopsButton() {
        this.openShopsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigator.navigateFromMainActivityToShopsActivity(MainActivity.this);
            }
        });

        this.openActivitiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigator.navigateFromMainActivityToActivitiesActivity(MainActivity.this);
            }
        });

    }
}
