package net.ddns.suyashbakshi.bakenmake.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.ddns.suyashbakshi.bakenmake.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by suyas on 6/8/2017.
 */

public class RecipeStepListAdapter extends ArrayAdapter {

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        try {
            final JSONObject info = new JSONObject(getItem(position).toString());


            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.recipe_step_list_item, parent, false);
            }
            TextView descriptionTv = (TextView) convertView.findViewById(R.id.recipe_description_tv);
            descriptionTv.setText(info.getString("description"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    public RecipeStepListAdapter(Context context, int resource, List objects) {
        super(context, 0, objects);
    }
}
