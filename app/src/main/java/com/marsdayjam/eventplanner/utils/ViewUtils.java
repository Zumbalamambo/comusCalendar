package com.marsdayjam.eventplanner.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ViewUtils {


	public static void displayShortToast(Context context, String msg) {
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public static void displayLongToast(Context context, String msg) {
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

}
