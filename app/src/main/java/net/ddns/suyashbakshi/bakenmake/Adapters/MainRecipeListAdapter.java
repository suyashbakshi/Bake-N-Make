package net.ddns.suyashbakshi.bakenmake.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.ddns.suyashbakshi.bakenmake.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by suyas on 6/7/2017.
 */
public class MainRecipeListAdapter extends ArrayAdapter<String> {
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){

            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.recipe_list_item,parent,false);
        }

        try {
            JSONObject item = new JSONObject(getItem(position));
            TextView recipeTv = (TextView)convertView.findViewById(R.id.recipe_list_tv);
            TextView servingTv = (TextView)convertView.findViewById(R.id.serving_tv);
            recipeTv.setText(item.getString("name"));
            servingTv.setText("Servings: " + item.getString("servings"));

            ImageView img = (ImageView)convertView.findViewById(R.id.fav_img);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(),"Favorite Feature will be added later",Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    public MainRecipeListAdapter(Context context, int resource, List<String> objects) {
        super(context, 0, objects);
    }
}
