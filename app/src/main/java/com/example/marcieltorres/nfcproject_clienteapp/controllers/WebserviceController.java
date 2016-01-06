package com.example.marcieltorres.nfcproject_clienteapp.controllers;

import com.example.marcieltorres.nfcproject_clienteapp.helpers.WebserviceHelper;

import org.json.JSONException;
import org.json.JSONStringer;

/**
 * Created by Marciel Torres on 08/11/2015.
 */
public final class WebserviceController {

    private static String URI = "http://52.32.142.242/NFCProject/";

    public static String VerifyPresence(String _event, String _mail) throws JSONException {

        JSONStringer model = new JSONStringer().object().key("Model").object().key("ID").value("").key("EventID").value(_event).key("SubscriberMail").value(_mail).endObject().endObject();

        // Array de String que recebe o JSON do Web Service
        String[] json = new WebserviceHelper().post(URI + "services/presences/Presence.svc/VerifySubscriber", model.toString());

        return json[1];
    }

    public static String ConfirmPresence(String _event, String _mail) throws JSONException {

        JSONStringer model = new JSONStringer().object().key("Model").object().key("ID").value("").key("EventID").value(_event).key("SubscriberMail").value(_mail).endObject().endObject();

        // Array de String que recebe o JSON do Web Service
        String[] json = new WebserviceHelper().post(URI + "services/presences/Presence.svc/ConfirmPresence", model.toString());

        return json[1];
    }

    public static String ListEvents() {
        // Array de String que recebe o JSON do Web Service
        String[] json = new WebserviceHelper().get(URI + "services/event/Events.svc/ListEvents");

        return json[1];
    }

    public static String CreateSubscriber(String _event, String _mail, String _name) throws JSONException {

        JSONStringer model = new JSONStringer().object().key("Model").object().key("ID").value("").key("EventID").value(_event).key("Mail").value(_mail).key("Name").value(_name).endObject().endObject();

        // Array de String que recebe o JSON do Web Service
        String[] json = new WebserviceHelper().post(URI + "services/subscriber/Subscribers.svc/CreateSubscriber", model.toString());

        return json[1];
    }

}
