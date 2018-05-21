package com.marsdayjam.eventplanner.webservices;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.marsdayjam.eventplanner.utils.SetupData;

import org.idempiere.webservice.client.base.LoginRequest;
import org.idempiere.webservice.client.net.WebServiceConnection;

/**
 * Created by soropeza on 20/08/17.
 */

public class WebService extends AsyncTask<Void, Integer, Void> implements I_WebService{


    protected static SharedPreferences setup;
    private  String userName;
    private  String password;
    private  String client;
    private  String role;
    protected   String org;
    private  String webServiceAddress;
   // protected String priceListVersion;
    public static String ACTION_NEW = "NEW";
    public static String ACTION_UPDATE = "UPDATE";
    public static String ACTION_DELETE = "DELETE";
    public static String NOT_ACTION = "NOT_ACTION";
    protected Context context;
    protected String warehouse;

    public WebService(Context context) {
        this.context = context;
        setParametersWS();

    }

    public void setParametersWS(){
        setup = SetupData.getInstance(context).getSharedPreferences();
        userName = setup.getString("UserName", "SuperUser");
        password = setup.getString("Password", "SuperPunto*");
        client = setup.getString("Client", "1000000");
        role = setup.getString("Role", "1000000");
        org = setup.getString("Org", "1000000");
        warehouse = setup.getString("Warehouse","0");
        webServiceAddress = setup.getString("WebServiceAddress", "http://puntodental2.comus.com.ec:8090");
        webServiceAddress = setup.getString("WebServiceAddress", "http://erp.puntodental.com.ec:8089");
        //webServiceAddress = setup.getString("WebServiceAddress", "http://192.168.29.101:8080");
      //  priceListVersion = setup.getString("PriceListVersion", "0");
    }

    @Override
    protected Void doInBackground(Void... params) {
        return null;
    }

    public WebServiceConnection getClient() {
        WebServiceConnection client = new WebServiceConnection();
        client.setAttempts(2);
        client.setTimeout(20000);
        client.setAttemptsTimeout(10000);
        client.setUrl(webServiceAddress);
        client.setAppName("Android Test WS Client");
        return client;
    }

    public LoginRequest getLogin() {
        LoginRequest login = new LoginRequest();
        login.setUser(userName);
        login.setPass(password);
        login.setClientID(Integer.parseInt(client));
        login.setRoleID(Integer.parseInt(role));
        login.setOrgID(0);
        //login.setWarehouseID(Integer.parseInt(warehouse));
        return login;
    }

    @Override
    public String getWebServiceType() {
        return null;
    }

    @Override
    public String executeWS() {
        return null;
    }
}
