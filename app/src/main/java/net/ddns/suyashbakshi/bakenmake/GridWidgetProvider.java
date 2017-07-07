package net.ddns.suyashbakshi.bakenmake;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import net.ddns.suyashbakshi.bakenmake.Activity.MainActivity;
import net.ddns.suyashbakshi.bakenmake.Activity.RecipeDetails;

import org.json.JSONException;
import org.json.JSONObject;

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

            SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.widget_pref), Context.MODE_PRIVATE);
            try {
                JSONObject fav_item = new JSONObject(preferences.getString(context.getString(R.string.widget_pref), ""));
                Log.v("Widget_update_check", fav_item.getString("name"));
                views.setTextViewText(R.id.widget_name_tv, fav_item.getString("name"));
                views.setTextViewText(R.id.widget_ingredients_tv, fav_item.getString("servings"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            appWidgetManager.updateAppWidget(appWidgetIds[i],views);
            views.setEmptyView(R.id.widget_name_tv, R.id.empty_view);;
        }
        super.onUpdate(context,appWidgetManager,appWidgetIds);
    }
}

