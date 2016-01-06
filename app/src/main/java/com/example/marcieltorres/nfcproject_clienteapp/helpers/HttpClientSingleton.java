package com.example.marcieltorres.nfcproject_clienteapp.helpers;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/**
 * Created by Marciel Torres on 08/11/2015.
 */
public class HttpClientSingleton {

    private static final int JSON_CONNECTION_TIMEOUT = 3000;
    private static final int JSON_SOCKET_TIMEOUT = 5000;

    private static HttpClientSingleton instance;
    private HttpParams httpParameters ;
    private DefaultHttpClient httpclient;

    private void setTimeOut(HttpParams params){
        HttpConnectionParams.setConnectionTimeout(params, JSON_CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(params, JSON_SOCKET_TIMEOUT);
    }


    private HttpClientSingleton() {
        httpParameters = new BasicHttpParams();
        setTimeOut(httpParameters);
        httpclient = new DefaultHttpClient(httpParameters);
    }

    public static DefaultHttpClient getHttpClientInstace(){
        if(instance==null)
            instance = new HttpClientSingleton();
        return instance.httpclient;
    }

}
