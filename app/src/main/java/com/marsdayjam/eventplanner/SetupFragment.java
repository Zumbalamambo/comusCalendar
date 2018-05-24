package com.marsdayjam.eventplanner;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.idempiere.webservice.client.base.LoginRequest;
import org.idempiere.webservice.client.net.WebServiceConnection;


public class SetupFragment extends Fragment implements View.OnClickListener {


    private static SharedPreferences setup;
    private TextView txtUserName;
    private TextView txtPassword;
    private TextView txtClient;
    private TextView txtRole;
    private TextView txtOrg;
    private TextView txtWebServiceAddress;
    private TextView txtInfo;

    public SetupFragment() {

    }

    // TODO: Rename and change types and number of parameters
    public static SetupFragment newInstance(SharedPreferences s) {
        setup = s;
        SetupFragment fragment = new SetupFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_setup, container, false);

        txtUserName = (TextView)view.findViewById(R.id.settings_username);
        txtPassword = (TextView)view.findViewById(R.id.settings_userpassword);
        txtClient = (TextView)view.findViewById(R.id.settings_client);
        txtRole = (TextView)view.findViewById(R.id.settings_role);
        txtOrg = (TextView)view.findViewById(R.id.settings_organization);
        txtWebServiceAddress =(TextView)view.findViewById(R.id.settings_webserviceEndpoint);

        txtInfo = (TextView)view.findViewById(R.id.item_Info);

        Button btnSave =(Button)view.findViewById(R.id.btn_saveconfig);
        btnSave.setOnClickListener(this);

        chargeSetupConfig();

        return view;

    }

    @Override
    public void onClick(View v) {

         if (v.getId()==R.id.btn_saveconfig) {
            saveConfig();
            txtInfo.setText("Datos Guardados Correctamente");
        }
    }

    //cargar configuración aplicación Android usando SetupData
    public void chargeSetupConfig() {
        String userName = setup.getString("UserName", "SuperUser");
        String password = setup.getString("Password", "SuperDemo");
        String client = setup.getString("Client", "1000000");
        String role = setup.getString("Role", "1000000");
        String org = setup.getString("Org", "1000000");
        String webServiceAddress = setup.getString("WebServiceAddress", "http://erp.puntodental.com.ec:8089");

        txtUserName.setText(userName);
        txtPassword.setText(password);
        txtClient.setText(client);
        txtRole.setText(role);
        txtOrg.setText(org);
        txtWebServiceAddress.setText(webServiceAddress);

        /*txtUserName.setText("SuperUser");
        txtPassword.setText("SuperDemo");
        txtClient.setText("1000000");
        txtRole.setText("1000000");
        txtOrg.setText("0");
        //txtWebServiceAddress.setText("http://192.168.1.110:8080");
        txtWebServiceAddress.setText("http://demo.comus.com.ec:8080");*/
    }

    private void saveConfig(){

        String userName = txtUserName.getText().toString();
        String password = txtPassword.getText().toString();
        String client = txtClient.getText().toString();
        String role = txtRole.getText().toString();
        String serviceAddress = txtWebServiceAddress.getText().toString();


        if (password==null || password.equals("0")) {
            txtInfo.setText("Campo Contraseña es Obligatorio");
            txtPassword.setFocusable(true);
            return;
        }

        if (client==null || client.equals("0")) {
            txtInfo.setText("Campo cliente es Obligatorio");
            txtClient.setFocusable(true);
            return;
        }
        if (role==null || role.equals("0")) {
            txtInfo.setText("Campo rol es Obligatorio");
            txtRole.setFocusable(true);
            return;
        }
        if (serviceAddress==null || serviceAddress.equals("0")) {
            txtInfo.setText("Campo web service es Obligatorio");
            txtWebServiceAddress.setFocusable(true);
            return;
        }


        SharedPreferences.Editor editor = setup.edit();

        editor.putString("UserName",txtUserName.getText().toString());
        editor.putString("Password",txtPassword.getText().toString());
        editor.putString("Client",txtClient.getText().toString());
        editor.putString("Role",txtRole.getText().toString());
        editor.putString("Org",txtOrg.getText().toString());
        editor.putString("Warehouse","0");
        editor.putString("WebServiceAddress",txtWebServiceAddress.getText().toString());
        editor.apply();
    }


    public WebServiceConnection getClient() {
        WebServiceConnection client = new WebServiceConnection();
        client.setAttempts(6);
        client.setTimeout(70000);
        client.setAttemptsTimeout(10000);
        client.setUrl(txtWebServiceAddress.getText().toString());
        client.setAppName("Android Test WS Client");
        return client;
    }

    public LoginRequest getLogin() {
        LoginRequest login = new LoginRequest();
        login.setUser(txtUserName.getText().toString());
        login.setPass(txtPassword.getText().toString());
        login.setClientID(Integer.parseInt(txtClient.getText().toString()));
        login.setRoleID(Integer.parseInt(txtRole.getText().toString()));
        login.setOrgID(Integer.parseInt(txtOrg.getText().toString()));
        return login;
    }
}
