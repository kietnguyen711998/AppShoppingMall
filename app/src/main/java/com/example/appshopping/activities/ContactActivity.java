package com.example.appshopping.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.example.appshopping.R;

public class ContactActivity extends AppCompatActivity {
    Toolbar toolbarcontact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        toolbarcontact = findViewById(R.id.toolbarcontact);
        ActionBar();
    }

    private void ActionBar() {
        setSupportActionBar(toolbarcontact);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarcontact.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
