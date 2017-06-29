package net.ddns.suyashbakshi.bakenmake.NetworkServices;

import android.content.Context;
import android.os.AsyncTask;

import net.ddns.suyashbakshi.bakenmake.Adapters.MainRecipeListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by suyas on 6/7/2017.
 */
public class FetchRecipe extends AsyncTask<Void, Void, String> {

    private Context mContext;
    private MainRecipeListAdapter mAdapter;

    public FetchRecipe(Context context, MainRecipeListAdapter adapter) {
        mContext = context;
        mAdapter = adapter;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mAdapter.clear();
        try {
            JSONArray array = new JSONArray(s);
            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);
                mAdapter.add(item.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected String doInBackground(Void... voids) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json")
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            return response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
