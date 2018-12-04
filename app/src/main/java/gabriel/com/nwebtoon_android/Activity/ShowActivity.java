package gabriel.com.nwebtoon_android.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import gabriel.com.nwebtoon_android.R;
import gabriel.com.nwebtoon_android.adapter.webtoonShowAdapter;
import gabriel.com.nwebtoon_android.model.LocalData;
import gabriel.com.nwebtoon_android.model.webtoonInfo;
import gabriel.com.nwebtoon_android.model.webtoonShow;
import gabriel.com.nwebtoon_android.network.Controller;
import gabriel.com.nwebtoon_android.network.ServerInterface;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ShowActivity extends AppCompatActivity {

    private ServerInterface api;
    RecyclerView recyclerView;
    private boolean mLockListView;
    LocalData localdata;
    LinearLayout layout_show_like, layout_previous, layout_next;
    ImageView img_show_like;
    LinearLayout layout_comment_view;
    RelativeLayout layout_title;
    TextView txt_show_like, txt_show_rating_total,txt_show_rating, txt_show_title;
    RatingBar ratingbar_show;
    Button btn_show_rating;
    String TOON_ID, DT_ID, TOON_NAME ;
    int size ;
    Toolbar toolbar ;
    boolean isToolbarAppeared,bool_like_chk=false;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localdata = LocalData.findById(LocalData.class, 1);
        if(!localdata.getRotation())
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_show);
        showinit();
        showSet();
    }

    private void showinit(){
        Intent intent_Data = getIntent();
        TOON_ID = intent_Data.getStringExtra("TOON_ID");
        DT_ID = intent_Data.getStringExtra("DT_ID");
        TOON_NAME = intent_Data.getStringExtra("TOON_NAME");
        size = intent_Data.getIntExtra("size", 0);
        api = Controller.getInstance().getServerInterface();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_cut);
        toolbar =(Toolbar) findViewById(R.id.below_toolbar);
        layout_title = (RelativeLayout) findViewById(R.id.layout_title);
        layout_show_like = (LinearLayout) findViewById(R.id.layout_show_like);
        txt_show_title = (TextView) findViewById(R.id.txt_show_title);
        img_show_like = (ImageView) findViewById(R.id.img_show_like);
        txt_show_like = (TextView) findViewById(R.id.txt_show_like);
        txt_show_rating_total = (TextView) findViewById(R.id.txt_show_rating_total);
        ratingbar_show = (RatingBar) findViewById(R.id.ratingbar_show);
        txt_show_rating = (TextView) findViewById(R.id.txt_show_rating);
        btn_show_rating = (Button) findViewById(R.id.btn_show_rating);
        layout_previous = (LinearLayout) findViewById(R.id.layout_previous);
        layout_comment_view = (LinearLayout) findViewById(R.id.layout_comment_view);
        layout_next = (LinearLayout) findViewById(R.id.layout_next);
    }

    private void showSet(){
        layout_title.bringToFront();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        api.getcutImageList(TOON_ID, DT_ID, new Callback<List<webtoonShow>>() {
            @Override
            public void success(List<webtoonShow> webtoonListShows, Response response) {
                recyclerView.setAdapter(new webtoonShowAdapter(getApplicationContext(), webtoonListShows));
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Failed to load thumbnails", Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.smoothScrollBy(100, 1);
        recyclerView.addOnItemTouchListener(new RecyclerViewClickListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View v, int position) {
            }
        }));

        layout_show_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (localdata.naver_id != null) {
                    if (bool_like_chk) {
                        bool_like_chk = false;
                        img_show_like.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    } else {
                        bool_like_chk = true;
                        img_show_like.setImageResource(R.drawable.ic_favorite_border_red_24dp);
                    }
                    api.updateDetailToonLike(bool_like_chk, localdata.naver_id, TOON_ID, DT_ID, new Callback<List<webtoonInfo>>() {
                        @Override
                        public void success(List<webtoonInfo> webtoonInfos, Response response) {
                            txt_show_like.setText(String.valueOf(webtoonInfos.get(0).LIKE_NUM));
                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "좋아요 기능은 로그인 후 이용할 수 있습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                    startActivity(intent);
                }
            }
        });
        ratingbar_show.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                txt_show_rating.setText("(" + (int) (rating * 2) + "/10)");
            }
        });
        btn_show_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (localdata.naver_id != null) {
                    api.insertPointDetailToon((int) (2 * ratingbar_show.getRating()), localdata.naver_id, TOON_ID, DT_ID, new Callback<List<webtoonInfo>>() {
                        @Override
                        public void success(List<webtoonInfo> webtoonInfos, Response response) {
                            ratingbar_show.setIsIndicator(true);
                            btn_show_rating.setVisibility(View.INVISIBLE);
                            txt_show_rating_total.setText("별점 " + String.valueOf(webtoonInfos.get(0).DT_RATE));
                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "별점 기능은 로그인 후 이용할 수 있습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                    startActivity(intent);
                }
            }
        });

        setSupportActionBar(toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        layout_comment_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CommentViewActivity.class);
                i.putExtra("TOON_ID", TOON_ID);
                i.putExtra("DT_ID", DT_ID);
                i.putExtra("TOON_NAME", TOON_NAME);
                startActivity(i);
            }
        });
        mLockListView = true;
        layout_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DT_ID.equals("0001"))
                    Toast.makeText(getApplicationContext(),"첫 화입니다.",Toast.LENGTH_SHORT).show();
                else {
                    DT_ID = "000" + Integer.toString(Integer.parseInt(DT_ID) - 1);
                    DT_ID = DT_ID.substring(DT_ID.length()-4,DT_ID.length());
                    Intent intent = new Intent(getApplicationContext(), ShowActivity.class);
                    intent.putExtra("TOON_ID", TOON_ID);
                    intent.putExtra("DT_ID", DT_ID);
                    intent.putExtra("TOON_NAME", TOON_NAME);
                    intent.putExtra("size", size);
                    startActivity(intent);
                    finish();
                }
            }
        });
        layout_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(DT_ID)==size)
                    Toast.makeText(getApplicationContext(),"마지막 화입니다.",Toast.LENGTH_SHORT).show();
                else {
                    DT_ID = "000" + Integer.toString(Integer.parseInt(DT_ID) + 1);
                    DT_ID = DT_ID.substring(DT_ID.length()-4,DT_ID.length());
                    Intent intent = new Intent(getApplicationContext(), ShowActivity.class);
                    intent.putExtra("TOON_ID", TOON_ID);
                    intent.putExtra("DT_ID", DT_ID);
                    intent.putExtra("TOON_NAME", TOON_NAME);
                    intent.putExtra("size", size);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();

        isToolbarAppeared = true;
        getSupportActionBar().show();
        //layout_title.setVisibility(View.VISIBLE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isToolbarAppeared = false;
                getSupportActionBar().hide();
                layout_title.setVisibility(View.INVISIBLE);
                /*isToolbarAppeared = false;*/
            }
        }, 3000);

        if(localdata.naver_id !=null) {
            api.getdetailToonLike(TOON_ID, DT_ID, localdata.naver_id, new Callback<List<webtoonInfo>>() {
                @Override
                public void success(List<webtoonInfo> webtoonInfos, Response response) {
                    if (!webtoonInfos.isEmpty() && webtoonInfos.get(0).LIKE_OK) {
                        img_show_like.setImageResource(R.drawable.ic_favorite_border_red_24dp);
                        bool_like_chk = true;
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
        api.getdetailToonLikeNum(TOON_ID, DT_ID, new Callback<List<webtoonInfo>>() {
            @Override
            public void success(List<webtoonInfo> webtoonInfos, Response response) {
                txt_show_like.setText(String.valueOf(webtoonInfos.get(0).LIKE_NUM));
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        api.getRateDetailToon(TOON_ID, DT_ID, new Callback<List<webtoonInfo>>() {
            @Override
            public void success(List<webtoonInfo> webtoonInfos, Response response) {
                txt_show_rating_total.setText("별점 " + String.valueOf(webtoonInfos.get(0).DT_RATE));
                txt_show_title.setText(String.valueOf(webtoonInfos.get(0).DT_NAME));
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        api.getPointDetailToon(localdata.naver_id, TOON_ID, DT_ID, new Callback<List<webtoonInfo>>() {
            @Override
            public void success(List<webtoonInfo> webtoonInfos, Response response) {
                if (!webtoonInfos.isEmpty()) {
                    ratingbar_show.setRating((float) webtoonInfos.get(0).POINT / 2);
                    ratingbar_show.setIsIndicator(true);
                } else if (localdata.naver_id != null)
                    btn_show_rating.setVisibility(View.VISIBLE);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    class RecyclerViewClickListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        public RecyclerViewClickListener(Context context, RecyclerView recyclerView, ClickListener clickListener){
            Log.d("TEST", "Construct Invoke ");
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    Log.d("TEST","onSingleTapUp " + e);
                    if(isToolbarAppeared) {
                        isToolbarAppeared = false;
                        getSupportActionBar().hide();
                        layout_title.setVisibility(View.INVISIBLE);
                    }
                    else {
                        isToolbarAppeared = true;
                        getSupportActionBar().show();
                        layout_title.setVisibility(View.VISIBLE);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isToolbarAppeared = false;
                                getSupportActionBar().hide();
                                layout_title.setVisibility(View.INVISIBLE);
                            }
                        }, 3000);
                    }
                    return true ;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            Log.d("TEST", "onInterceptTouchEvent " + gestureDetector.onTouchEvent(e) + " " + e);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            Log.d("TEST", "onTouchEvent " +e);
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public interface ClickListener {
        void onClick(View v, int position);
    }
}