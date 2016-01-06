package com.example.marcieltorres.nfcproject_clienteapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
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

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Carrega eventos para exibir no spinner
            try {
                this.LoadSpinnerEvents();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_newsubscriber) {
            Intent subscriberActivity = new Intent(this, SubscriberActivity.class);
            startActivity(subscriberActivity);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btnCreateCard(View v) throws InterruptedException, JSONException {

        Spinner eventID = (Spinner)findViewById(R.id.editText);
        EditText mail = (EditText)findViewById(R.id.editText2);
        String _mail = mail.getText().toString();
        String s[] = eventID.getSelectedItem().toString().split("/");
        String _event = s[1];

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Faz consistências básicas com os dados informados
        if ((!_mail.isEmpty()) && (!_event.isEmpty()))
        {
            String _resp = WebserviceController.VerifyPresence(_event, _mail);
            JSONObject model = new JSONObject(_resp);
            String _success = model.getJSONObject("VerifySubscriberResult").getJSONObject("Status").getString("Success");

            if(_success.equals("0"))
            {
                showToastMessage(model.getJSONObject("VerifySubscriberResult").getJSONObject("Status").getString("Message"));
            }
            else
            {

                showToastMessage(model.getJSONObject("VerifySubscriberResult").getJSONObject("Status").getString("Message"));

                JSONArray _presences = model.getJSONObject("VerifySubscriberResult").getJSONArray("Presences");
                String _eventName = "";
                String _subscriberName ="";

                for (int i = 0; i < _presences.length(); ++i)
                {
                    _eventName = _presences.getJSONObject(i).getString("EventName");
                    _subscriberName = _presences.getJSONObject(i).getString("SubscriberName");
                }

                Intent cardActivity = new Intent(this, CardActivity.class);
                cardActivity.putExtra("Event", _event);
                cardActivity.putExtra("Mail", _mail);
                cardActivity.putExtra("EventName", _eventName);
                cardActivity.putExtra("SubscriberName", _subscriberName);

                startActivity(cardActivity);
            }
        }
        else
        {
            showToastMessage("Você deve informar o evento e seu email");
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

    private void showToastMessage(String msg){
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
