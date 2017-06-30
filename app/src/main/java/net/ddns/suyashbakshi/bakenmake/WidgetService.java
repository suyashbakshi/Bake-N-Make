package net.ddns.suyashbakshi.bakenmake;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

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

    private static final String[] items={"lorem", "ipsum", "dolor",
            "sit", "amet", "consectetuer",
            "adipiscing", "elit", "morbi",
            "vel", "ligula", "vitae",
            "arcu", "aliquet", "mollis",
            "etiam", "vel", "erat",
            "placerat", "ante",
            "porttitor", "sodales",
            "pellentesque", "augue",
            "purus"};

    public GridProvider(Context context, Intent intent){
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return(items.length);
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews row=new RemoteViews(context.getPackageName(),
                R.layout.widget_grid_item);

        row.setTextViewText(R.id.widget_grid_view_tv, items[i]);

        Intent intent=new Intent();
        Bundle extras=new Bundle();

        extras.putString(Intent.EXTRA_TEXT, items[i]);
        intent.putExtras(extras);
        row.setOnClickFillInIntent(R.id.widget_grid_view_tv, intent);

        return(row);
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return(1);
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


