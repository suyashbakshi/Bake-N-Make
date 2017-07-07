package net.ddns.suyashbakshi.bakenmake.Adapters;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.ddns.suyashbakshi.bakenmake.Activity.RecipeDetails;
import net.ddns.suyashbakshi.bakenmake.GridWidgetProvider;
import net.ddns.suyashbakshi.bakenmake.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static net.ddns.suyashbakshi.bakenmake.R.id.fav_img;

/**
 * Created by suyas on 6/7/2017.
 */
public class MainRecipeListAdapter extends RecyclerView.Adapter<MainRecipeListAdapter.ViewHolder> {

    ArrayList<String> recipeList;
    Context context;

    public void addItems(String mItem) {
        recipeList.add(mItem);
    }

    public void clearItems() {
        recipeList.clear();
    }

    public void show() {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.v("Check_insert", recipeList.toString());
        try {
            final JSONObject item = new JSONObject(recipeList.get(position));
            holder.recipeTv.setText(item.getString("name"));
            holder.servingTv.setText("Servings: " + item.getString("servings"));
            if (!item.getString("image").equalsIgnoreCase(""))
                Picasso.with(context).load(item.getString("image")).into(holder.main_img);

            holder.fav_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //setData of fav'd item in shared preferences
                    SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.widget_pref), context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(context.getString(R.string.widget_pref), item.toString());
                    editor.apply();

                    //broadcast an intent to update all the widgets.
                    Intent intent = new Intent(context, GridWidgetProvider.class);
                    intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,new int[]{R.xml.grid_widget_provider_info});
                    context.sendBroadcast(intent);

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public MainRecipeListAdapter(ArrayList<String> mList, Context mContext) {
        this.recipeList = mList;
        this.context = mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView recipeTv, servingTv;
        ImageView fav_img, main_img;

        public ViewHolder(View itemView) {
            super(itemView);
            recipeTv = (TextView) itemView.findViewById(R.id.recipe_list_tv);
            servingTv = (TextView) itemView.findViewById(R.id.serving_tv);
            fav_img = (ImageView) itemView.findViewById(R.id.fav_img);
            main_img = (ImageView) itemView.findViewById(R.id.list_image_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String recipe = recipeList.get(getAdapterPosition()).toString();
                    Intent detailIntent = new Intent(context, RecipeDetails.class).putExtra(Intent.EXTRA_TEXT, recipe);
                    context.startActivity(detailIntent);
                }
            });


        }
    }
}