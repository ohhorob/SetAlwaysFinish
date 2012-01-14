package com.bricolsoftconsulting.setalwaysfinish;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

public class WidgetProvider  extends AppWidgetProvider {
    private static final String LOG_TAG = "WidgetProvider";

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        
        final boolean isSet = SetAlwaysFinishActivity.GetAlwaysFinish(context);

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, buildWidget(context, isSet));
        }
    }
    
    public static RemoteViews buildWidget(Context context, boolean isSet) {

        final Uri intentUri = Uri.parse("setalwaysfinish://?alwaysfinish=" + Boolean.toString(!isSet) + "&notify=false");
        Log.d(LOG_TAG, "buildWidget() intentUri = " + intentUri);
        final String buttonText = isSet ? "Turn Off" : "Turn On";

        // Create an Intent to launch ExampleActivity
        Intent intent = new Intent();
        intent.setData(intentUri);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Get the layout for the App Widget and attach an on-click listener
        // to the button
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget);
        views.setOnClickPendingIntent(R.id.toggle, pendingIntent);


        views.setTextViewText(R.id.toggle, buttonText);

        return views;
    }
}
