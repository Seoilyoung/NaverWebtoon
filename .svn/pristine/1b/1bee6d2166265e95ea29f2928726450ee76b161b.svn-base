package gabriel.com.nwebtoon_android.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import gabriel.com.nwebtoon_android.model.LocalData;
import gabriel.com.nwebtoon_android.model.adList;
import gabriel.com.nwebtoon_android.network.Controller;
import gabriel.com.nwebtoon_android.network.ServerInterface;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SplashActivity extends AppCompatActivity {

    //Handler handler = new Handler(); //Handler객체를 이용해 2~3초 뒤에 무언가 작동하는 기능을 수행할 수 있습니다.
    LocalData localdata ;
    private ServerInterface api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if(LocalData.findById(LocalData.class, 1) ==null) {
            localdata = new LocalData(false, true, false);
            localdata.save();
        }
        Controller webtoonLIstDayContorller = Controller.getInstance();
        webtoonLIstDayContorller.buildServerInterface();
        api = Controller.getInstance().getServerInterface();
        api.getAdvertisementList(new Callback<List<adList>>() {
            @Override
            public void success(List<adList> adLists, Response response) {
                MainActivity.ad_img = adLists;
                Log.e("#mainActivity", "loading ad list success");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Toast.makeText(getApplicationContext(),"광고이미지를 불러오지 못했습니다. 네트워크 상태를 확인해주세요",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    /*
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        }, 10000);*/
    }
}
