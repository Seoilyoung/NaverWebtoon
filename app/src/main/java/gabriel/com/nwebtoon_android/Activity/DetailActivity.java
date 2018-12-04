package gabriel.com.nwebtoon_android.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import java.util.List;

import gabriel.com.nwebtoon_android.R;
import gabriel.com.nwebtoon_android.adapter.webtoonDetailAdapter;
import gabriel.com.nwebtoon_android.model.LocalData;
import gabriel.com.nwebtoon_android.model.webtoonInfo;
import gabriel.com.nwebtoon_android.model.webtoonListDetail;
import gabriel.com.nwebtoon_android.network.Controller;
import gabriel.com.nwebtoon_android.network.ServerInterface;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DetailActivity extends AppCompatActivity {

    private TextView txt_list_name;
    private LinearLayout layout_detail_back, layout_detail_like, layout_detail_favorite, layout_detail_first;
    private ServerInterface api;
    RecyclerView recyclerView;
    TextView txt_detail_like ;
    ImageView img_detail_like, img_detail_favorite;
    boolean bool_favorite = false, bool_like = false;
    String TOON_ID, TOON_NAME ;
    LocalData localdata ;
    NetworkInfo networkinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_detail);
        api = Controller.getInstance().getServerInterface();
        Detailinit();
        Detailset() ;
    }



    private void Detailinit() {
        Intent intent_date = getIntent();
        TOON_ID = intent_date.getStringExtra("TOON_ID");
        TOON_NAME = intent_date.getStringExtra("TOON_NAME");
        txt_detail_like = (TextView) findViewById(R.id.txt_detail_like);
        img_detail_like = (ImageView) findViewById(R.id.img_detail_like);
        img_detail_favorite = (ImageView) findViewById(R.id.img_detail_favorite);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_detail);
        txt_list_name = (TextView) findViewById(R.id.txt_list_name);
        layout_detail_back = (LinearLayout) findViewById(R.id.layout_detail_back);
        layout_detail_like = (LinearLayout) findViewById(R.id.layout_detail_like);
        layout_detail_favorite = (LinearLayout) findViewById(R.id.layout_detail_favorite);
        layout_detail_first = (LinearLayout) findViewById(R.id.layout_detail_first);
    }
    private  void Detailset() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        txt_list_name.setText(TOON_NAME);
        layout_detail_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        layout_detail_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(localdata.naver_id !=null) {
                    if (bool_like) {
                        bool_like = false;
                        img_detail_like.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    } else {
                        bool_like = true;
                        img_detail_like.setImageResource(R.drawable.ic_favorite_border_red_24dp);
                    }
                    api.updateHeaderToonLike(bool_like, localdata.naver_id, TOON_ID, new Callback<List<webtoonInfo>>() {
                        @Override
                        public void success(List<webtoonInfo> webtoonInfos, Response response) {
                            txt_detail_like.setText(String.valueOf(webtoonInfos.get(0).LIKE_NUM));
                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "좋아요 기능은 로그인 후 이용할 수 있습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                    startActivity(intent);
                }
            }
        });
        layout_detail_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(localdata.naver_id==null) {
                    Toast.makeText(getApplicationContext(), "관심 웹툰 기능은 로그인 후 이용하실 수 있습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                    startActivity(intent);
                }
                else {
                    if (bool_favorite) {
                        img_detail_favorite.setImageResource(R.drawable.ic_star_border_green_24dp);
                        bool_favorite = false;
                        api.deleteHeaderFavirite(TOON_ID, localdata.naver_id, new Callback<List<webtoonListDetail>>() {
                            @Override
                            public void success(List<webtoonListDetail> webtoonListDetails, Response response) {
                            }

                            @Override
                            public void failure(RetrofitError error) {
                            }
                        });
                    } else {
                        img_detail_favorite.setImageResource(R.drawable.ic_star_border_green_full_24dp);
                        bool_favorite = true;
                        api.insertHeaderFavirite(TOON_ID, localdata.naver_id, new Callback<List<webtoonListDetail>>() {
                            @Override
                            public void success(List<webtoonListDetail> webtoonListDetails, Response response) {
                            }

                            @Override
                            public void failure(RetrofitError error) {
                            }
                        });
                    }
                }
            }
        });
        layout_detail_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent_first = new Intent(getApplicationContext(), ShowActivity.class);
                    intent_first.putExtra("TOON_ID", TOON_ID);
                    intent_first.putExtra("DT_ID", "0001");
                    startActivity(intent_first);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        localdata = LocalData.findById(LocalData.class, 1);
        if(localdata.getNetwork() && Controller.getInstance().getNetworkinfo().getType()== ConnectivityManager.TYPE_MOBILE)
            Toast.makeText(getApplicationContext(), "데이터 네트워크에 연결 중입니다. 데이터 통화료과 부과됩니다.", Toast.LENGTH_LONG).show();
        if(localdata.naver_id !=null) {
            api.getHeaderToonLike(TOON_ID, localdata.naver_id, new Callback<List<webtoonInfo>>() {
                @Override
                public void success(List<webtoonInfo> webtoonInfos, Response response) {
                    if (!webtoonInfos.isEmpty() && webtoonInfos.get(0).LIKE_OK) {
                        img_detail_like.setImageResource(R.drawable.ic_favorite_border_red_24dp);
                        bool_like = true;
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
        api.getHeaderToonLikeNum(TOON_ID, new Callback<List<webtoonInfo>>() {
            @Override
            public void success(List<webtoonInfo> webtoonInfos, Response response) {
                txt_detail_like.setText(String.valueOf(webtoonInfos.get(0).LIKE_NUM));
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        api.getHeaderFavirite(localdata.naver_id, TOON_ID, new Callback<List<webtoonInfo>>() {
            @Override
            public void success(List<webtoonInfo> webtoonInfos, Response response) {
                if (webtoonInfos.get(0).FAVORITE_HD != 0) {
                    bool_favorite = true;
                    img_detail_favorite.setImageResource(R.drawable.ic_star_border_green_full_24dp);
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
        api.getdetailWebtoonList(TOON_ID, new Callback<List<webtoonListDetail>>() {
            @Override
            public void success(List<webtoonListDetail> webtoonListDetails, Response response) {
                recyclerView.setAdapter(new webtoonDetailAdapter(getApplicationContext(), webtoonListDetails));
                if(webtoonListDetails.size()==0)
                    layout_detail_first.setClickable(false);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "네트워크 연결 실패", Toast.LENGTH_LONG).show();
            }
        });
    }
}