package com.example.marcieltorres.nfcproject_clienteapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

public class CardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String _event = intent.getStringExtra("Event");
        String _eventName = intent.getStringExtra("EventName");
        String _subscriberName = intent.getStringExtra("SubscriberName");
        String _mail = intent.getStringExtra("Mail");

        TextView _textEvent = (TextView)findViewById(R.id.card_account_event);
        _textEvent.setText(_eventName);

        TextView _textMail = (TextView)findViewById(R.id.card_account_mail);
        _textMail.setText(_subscriberName);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        prefs.edit().putString("Access", _event + "#" + _mail).commit();

        SharedPreferences prefs1 = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        String account = prefs1.getString("Access", "0");
    }

    private void showToastMessage(String msg){
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
