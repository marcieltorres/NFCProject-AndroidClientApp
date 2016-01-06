package com.example.marcieltorres.nfcproject_clienteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.marcieltorres.nfcproject_clienteapp.controllers.WebserviceController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SubscriberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriber);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            this.LoadSpinnerEvents();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void LoadSpinnerEvents() throws JSONException {
        Spinner spinner1 = (Spinner) findViewById(R.id.editText);
        List<String> list = new ArrayList<String>();

        String _lisEvents = WebserviceController.ListEvents();

        JSONObject jsonResponse = new JSONObject(_lisEvents);
        JSONArray _events = jsonResponse.getJSONArray("Events");

        for (int i = 0; i < _events.length(); ++i)
        {
            list.add(_events.getJSONObject(i).getString("Title") + "/" + _events.getJSONObject(i).getString("ID"));
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);

    }

    public void btnNewSubscriber(View v) throws InterruptedException, JSONException {
        Spinner eventID = (Spinner)findViewById(R.id.editText);
        EditText mail = (EditText)findViewById(R.id.editText2);
        EditText name = (EditText)findViewById(R.id.editText3);
        String _mail = mail.getText().toString();
        String _name = name.getText().toString();
        String s[] = eventID.getSelectedItem().toString().split("/");
        String _event = s[1];

        //Faz consistências básicas com os dados informados
        if ((!_mail.isEmpty()) && (!_event.isEmpty()) && (!_name.isEmpty())) {
            String _resp = WebserviceController.CreateSubscriber(_event, _mail, _name);
            //JSONStringer model = new JSONStringer();
            JSONObject model = new JSONObject(_resp);
            String _success = model.getJSONObject("CreateSubscriberResult").getJSONObject("Status").getString("Success");

            if(_success.equals("0")){
                showToastMessage("Não foi possível efetuar sua inscrição");
            }
            else{
                showToastMessage("Inscrição efetuada com sucesso!");

                Intent mainActivity = new Intent(this, MainActivity.class);
                startActivity(mainActivity);
            }
        }
        else{
            showToastMessage("Você deve informar o evento, seu e-mail e seu nome.");
        }

    }


    private void showToastMessage(String msg){
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

}
