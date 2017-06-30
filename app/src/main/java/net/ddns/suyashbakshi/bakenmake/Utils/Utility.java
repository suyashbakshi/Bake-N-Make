package net.ddns.suyashbakshi.bakenmake.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by suyas on 6/28/2017.
 */

public class Utility {

    public static String result;

    public static boolean isOnline(Context context){
        ConnectivityManager manager = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = manager.getActiveNetworkInfo();

        return info!=null && info.isConnectedOrConnecting();
    }
}
