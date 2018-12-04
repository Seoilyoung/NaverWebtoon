package gabriel.com.nwebtoon_android.network;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nhn.android.naverlogin.OAuthLogin;

import gabriel.com.nwebtoon_android.Activity.MainActivity;
import gabriel.com.nwebtoon_android.model.NaverUserData;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class Controller extends com.orm.SugarApp {

    private static Controller instance;
    ConnectivityManager connectivityManager;
    NetworkInfo networkinfo;
    public static Controller getInstance() { return instance; }
    public static NaverUserData naverUserData = null;

    @Override
    public void onCreate() {

        super.onCreate();
        Controller.instance = this;
        connectivityManager = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkinfo = connectivityManager.getActiveNetworkInfo();
        if(networkinfo.getType() == ConnectivityManager.TYPE_MOBILE){
            Log.e("Network_Inspection","mobile is connected");
        }
        if(networkinfo.getType() == ConnectivityManager.TYPE_WIFI){
            Log.e("Network_Inspection","Wi-Fi is connected");
        }
        if(naverApi == null){
            naverApi = OAuthLogin.getInstance();
        }
        if(naverUserData == null)
        {
            naverUserData = new NaverUserData();
        }
    }
    private OAuthLogin naverApi;
    public OAuthLogin getNaverApi() { return naverApi; }
    private ServerInterface api;
    public ServerInterface getServerInterface() { return api; }
    public NetworkInfo getNetworkinfo() { return networkinfo; }
    public void flushNaverUserData() {
        if(naverUserData != null) {
            naverUserData.nickname = null;
            naverUserData.email = null;
            naverUserData.enc_id = null;
            naverUserData.profile_image = null;
            naverUserData.age = null;
            naverUserData.gender = null;
            naverUserData.id = null;
            naverUserData.name = null;
            naverUserData.birthday = null;
        }
    }

    private String endpoint;

    public void buildServerInterface() {

        synchronized (Controller.class) {

            if (api == null) {

                endpoint = "http://115.23.142.212:8080/nwebtoon";

                Gson gson = new GsonBuilder().create();

                RestAdapter.Builder builder = new RestAdapter.Builder();
                builder.setConverter(new GsonConverter(gson));
                builder.setEndpoint(endpoint);

                RestAdapter adapter = builder.build();
                api = adapter.create(ServerInterface.class);
            }

        }
    }
}