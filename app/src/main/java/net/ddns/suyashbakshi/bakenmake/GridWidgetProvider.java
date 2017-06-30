package net.ddns.suyashbakshi.bakenmake;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import net.ddns.suyashbakshi.bakenmake.Activity.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class GridWidgetProvider extends AppWidgetProvider {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int imgRes, long plantId, boolean showWater, int appWidgetId) {
        // Get current width to decide on single plant vs garden grid view
        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        RemoteViews rv = new RemoteViews(context.getPackageName(),R.layout.grid_widget_provider);
        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        for (int i = 0; i < appWidgetIds.length; i++) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.grid_widget_provider);
            Log.v("Widget_check","Update");
            Intent adapterIntent = new Intent(context, WidgetService.class);
            adapterIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);

            views.setRemoteAdapter(R.id.widget_grid_view,adapterIntent);

            Intent clickIntent = new Intent(context, MainActivity.class);
            PendingIntent pi = PendingIntent.getActivity(context,0,clickIntent,PendingIntent.FLAG_UPDATE_CURRENT);

            views.setOnClickPendingIntent(R.id.widget_grid_view,pi);
            appWidgetManager.updateAppWidget(appWidgetIds[i],views);

            views.setEmptyView(R.id.widget_grid_view,R.id.empty_view);
        }
        super.onUpdate(context,appWidgetManager,appWidgetIds);
    }
}

