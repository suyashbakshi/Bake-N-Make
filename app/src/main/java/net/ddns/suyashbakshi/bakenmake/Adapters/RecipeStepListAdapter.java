package net.ddns.suyashbakshi.bakenmake.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.ddns.suyashbakshi.bakenmake.Activity.RecipeDetails;
import net.ddns.suyashbakshi.bakenmake.Activity.RecipeDetailsFragment;
import net.ddns.suyashbakshi.bakenmake.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suyas on 6/8/2017.
 */

public class RecipeStepListAdapter extends RecyclerView.Adapter<RecipeStepListAdapter.ViewHolder>{

    private ArrayList<String> list;
    private Context context;

    public void addItem(String item){
        list.add(item);
    }

    public String getItem(int i){
        return list.get(i);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_step_list_item, parent, false);
        return new RecipeStepListAdapter.ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            JSONObject info = new JSONObject(list.get(position));
            holder.descriptionTv.setText(info.getString("description"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public RecipeStepListAdapter(ArrayList mList, Context mContext){
        this.list = mList;
        this.context = mContext;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView descriptionTv;

        public ViewHolder(View itemView) {
            super(itemView);
            descriptionTv = (TextView) itemView.findViewById(R.id.recipe_description_tv);
        }
    }
}


  /*  @NonNull
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

        public class ViewHolder {
        }
}*/