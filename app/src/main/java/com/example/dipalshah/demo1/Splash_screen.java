package com.example.dipalshah.demo1;

/**
 * Created by patel on 05-01-2018.
 */

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Set;

public class Splash_screen extends AppCompatActivity {

    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;
    String ShowOrHideWebViewInitialUse = "show";
    private ProgressBar spinner;
    private static int SPLASH_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);


        spinner = (ProgressBar)findViewById(R.id.progressBar1);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                BA = BluetoothAdapter.getDefaultAdapter();

                if (!BA.isEnabled()) {
                    BA.enable();
                    Toast.makeText(Splash_screen.this, "Bluetooth is on now", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(Splash_screen.this, "Bluetooth is already on", Toast.LENGTH_SHORT).show();
                }
                Intent i = new Intent(Splash_screen.this, Main_page.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);


    }

    // This allows for a splash screen
    // (and hide elements once the page loads)
  /*  private class NextPageLoad extends Intent {

        @Override
        public void onPageStarted(WebView webview, String url, Bitmap favicon) {

            // only make it invisible the FIRST time the app is run
            if (ShowOrHideWebViewInitialUse.equals("show") {
                webview.setVisibility(webview.INVISIBLE);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            ShowOrHideWebViewInitialUse = "hide";
            spinner.setVisibility(View.GONE);

            //view.setVisibility(webview.VISIBLE);
            super.onPageFinished(view, url);

        }
    }*/
}
