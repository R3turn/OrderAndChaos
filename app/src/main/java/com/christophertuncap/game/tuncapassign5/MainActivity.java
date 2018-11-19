//Programmer: Christopher Tuncap
//Date created: 11/4/2018
//Class: CSCI 4010

package com.christophertuncap.game.tuncapassign5;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Anonymous listener for About button:
        findViewById(R.id.about_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAboutPage();
            }
        });

        // Anonymous listener for Learn more button:
        findViewById(R.id.learn_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://en.wikipedia.org/wiki/Order_and_Chaos";
                goToWebSite(url);
            }
        });

        // Anonymous listener for Play button:
        findViewById(R.id.play_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPlayPage();
            }
        });

    }

    private void goToAboutPage(){
        Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(intent);
    }

    private void goToWebSite(String url){
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void goToPlayPage(){
        Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        //Disregard this onClick.  Anonymous onClick listeners have been created in onCreate.
    }
}
