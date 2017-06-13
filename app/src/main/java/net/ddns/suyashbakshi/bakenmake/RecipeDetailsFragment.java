package net.ddns.suyashbakshi.bakenmake;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.ImageView;
import android.widget.MediaController;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeDetailsFragment extends Fragment {

    public static RecipeStepListAdapter mAdapter;

    public RecipeDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        Intent intent = getActivity().getIntent();
        StringBuffer buff = new StringBuffer();
        mAdapter = new RecipeStepListAdapter(getContext(),0,new ArrayList());
        try {
            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {

                TextView ingredients_tv = (TextView)rootView.findViewById(R.id.ingredients_tv);
                ListView stepListView = (ListView)rootView.findViewById(R.id.recipe_step_list);
                stepListView.setAdapter(mAdapter);

                JSONObject det = new JSONObject(intent.getStringExtra(Intent.EXTRA_TEXT));
                JSONArray ingredients = det.getJSONArray("ingredients");
                JSONArray steps = det.getJSONArray("steps");

                for (int i = 0; i < ingredients.length(); i++) {
                    JSONObject item = ingredients.getJSONObject(i);
                    buff.append("-> " + item.getString("ingredient") + "\n    Quantity : " + item.getString("quantity") + " " + item.getString("measure") + "\n\n");
                }
                for (int i = 0; i < steps.length(); i++) {
                    JSONObject step = steps.getJSONObject(i);
                    mAdapter.add(step.toString());
                }

                ingredients_tv.setText(buff.toString());
                mAdapter.notifyDataSetChanged();

                stepListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getContext(),VideoActivity.class);
                        intent.putExtra(Intent.EXTRA_TEXT,adapterView.getItemAtPosition(i).toString());
                        startActivity(intent);
                    }
                });

            }
        }catch (JSONException ex){}

        return rootView;
    }
}
