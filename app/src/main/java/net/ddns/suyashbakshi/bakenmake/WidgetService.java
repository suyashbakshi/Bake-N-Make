package net.ddns.suyashbakshi.bakenmake;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import net.ddns.suyashbakshi.bakenmake.Utils.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by suyas on 6/29/2017.
 */

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int appWidgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        return (new GridProvider(this.getApplicationContext(), intent));
    }
}

class GridProvider implements RemoteViewsService.RemoteViewsFactory {

    private Context context = null;
    private int appWidgetId;

    private static ArrayList<String> items = new ArrayList<>();

    public GridProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        JSONArray array = null;
        try {
            array = new JSONArray(Utility.result);

            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);
                items.add(item.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        items.clear();
    }

    @Override
    public int getCount() {
        return (items.size());
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews row = new RemoteViews(context.getPackageName(),
                R.layout.widget_grid_item);

        try {
            JSONObject jsonObject = new JSONObject(items.get(i));

            row.setTextViewText(R.id.widget_grid_view_tv, jsonObject.getString("name"));

            Intent intent = new Intent();

            intent.putExtra(Intent.EXTRA_TEXT, items.get(i));
            row.setOnClickFillInIntent(R.id.widget_grid_view_tv, intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return (row);
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return (1);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}


