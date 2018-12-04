package gabriel.com.nwebtoon_android.Activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import gabriel.com.nwebtoon_android.R;
import gabriel.com.nwebtoon_android.adapter.webtoonSearchAdapter;
import gabriel.com.nwebtoon_android.model.webtoonListSearch;
import gabriel.com.nwebtoon_android.network.Controller;
import gabriel.com.nwebtoon_android.network.ServerInterface;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by seoil on 2016-02-28.
 */
public class SearchActivity extends AppCompatActivity {


    private ServerInterface api;
    EditText edit_search ;
    RecyclerView recyclerView_search;
    LinearLayout layout_search_back, layout_search_search ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_search);
        api = Controller.getInstance().getServerInterface();

        Searchinit();
        SearchSet();
    }

    private void Searchinit(){
        recyclerView_search = (RecyclerView)findViewById(R.id.recyclerview_search);
        layout_search_back = (LinearLayout)findViewById(R.id.layout_search_back);
        layout_search_search = (LinearLayout)findViewById(R.id.layout_search_search);
        edit_search = (EditText) findViewById(R.id.edit_search);
    }

    private void SearchSet(){
        recyclerView_search.setLayoutManager(new LinearLayoutManager(this));
        layout_search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        layout_search_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_edit = edit_search.getText().toString();
                api.getwebtoonList(str_edit, new Callback<List<webtoonListSearch>>() {
                    @Override
                    public void success(List<webtoonListSearch> webtoonListSearches, Response response) {
                        recyclerView_search.setAdapter(new webtoonSearchAdapter(getApplicationContext(), webtoonListSearches));
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getApplicationContext(), "네트워크 연결 실패", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}
