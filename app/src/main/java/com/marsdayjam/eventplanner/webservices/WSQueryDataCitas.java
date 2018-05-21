package com.marsdayjam.eventplanner.webservices;

import android.content.Context;


import com.marsdayjam.eventplanner.Calendar.CalendarEvent;
import com.marsdayjam.eventplanner.Calendar.CalendarFragment;
import com.marsdayjam.eventplanner.DB.DBController;

import org.idempiere.webservice.client.base.DataRow;
import org.idempiere.webservice.client.base.Enums;
import org.idempiere.webservice.client.base.Field;
import org.idempiere.webservice.client.request.QueryDataRequest;
import org.idempiere.webservice.client.response.WindowTabDataResponse;

import java.util.Date;


/**
 * Created by soropeza on 21/06/17.
 */

public class WSQueryDataCitas extends WebService {

    private final String EMail;
    private final String Password;

    public WSQueryDataCitas(Context context, String EMail, String Password ) {
        super(context);
        this.EMail = EMail;
        this.Password = Password;
    }

    @Override
    public String getWebServiceType() {
        return "QueryCitas";
    }

    @Override
    public Void doInBackground(Void... params) {
        executeWS();
        return null;
    }


    @Override
    public String executeWS() {
        String msj = "";
        QueryDataRequest ws = new QueryDataRequest();
        ws.setWebServiceType(getWebServiceType());
        ws.setLogin(getLogin());
        Date today = new Date();
        //ws.setOffset(3);
        // ws.setLimit(50);

        DataRow data = new DataRow();
        data.addField("EMail", EMail);
        data.addField("Password",Password);
        ws.setDataRow(data);


        DBController dbController = DBController.getInstance(context);

        try {
            WindowTabDataResponse response = getClient().sendRequest(ws);

            if (response.getStatus() == Enums.WebServiceResponseStatus.Error) {
                //ViewUtils.displayLongToast(context, response.getErrorMessage());
                msj = response.getErrorMessage();
            } else {
                System.out.println("Total rows: " + response.getTotalRows());
                System.out.println("Num rows: " + response.getNumRows());
                System.out.println("Start row: " + response.getStartRow());
                dbController.deleteAllEvents();
                for1:for (int i = 0; i < response.getDataSet().getRowsCount(); i++) {
                    System.out.println("Row: " + (i + 1));
                    CalendarEvent event = new CalendarEvent();
                    event.setEmployee(dbController.getEmployee(EMail));
                    for (int j = 0; j < response.getDataSet().getRow(i).getFieldsCount(); j++) {

                        Field field = response.getDataSet().getRow(i).getFields().get(j);
                        System.out.println("Column: " + field.getColumn() + " = " + field.getStringValue());
                        switch (field.getColumn()) {
                            case "StartTime":
                                if(field.getDateValue().compareTo(today)<0)
                                    break for1;
                                event.setStart(field.getDateValue());
                                break;
                            case "EndTime":
                                event.setEnd(field.getDateValue());
                                break;
                            case "Description":
                                event.setDescription(field.getStringValue());
                                break;
                        }
                    }

                    dbController.insertCalendarEvent(event);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            msj =  e.getMessage()+" "+e.getCause();
        }
        return msj;
    }

}
