package gabriel.com.nwebtoon_android.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import gabriel.com.nwebtoon_android.R;
import gabriel.com.nwebtoon_android.adapter.webtoonMyFavoriteAdapter;
import gabriel.com.nwebtoon_android.model.LocalData;
import gabriel.com.nwebtoon_android.model.webtoonListMyFavorite;
import gabriel.com.nwebtoon_android.network.Controller;
import gabriel.com.nwebtoon_android.network.ServerInterface;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/*
 * Created by seoil on 2016-02-15.
*/


public class MypageFavoriteFragment extends Fragment {
    private ServerInterface api;
    private RecyclerView recyclerView;
    TextView txt_mypage_default;
    LocalData localdata;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mypage_list, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_mypage);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        localdata = LocalData.findById(LocalData.class, 1);

        api = Controller.getInstance().getServerInterface();
        txt_mypage_default = (TextView) rootView.findViewById(R.id.txt_mypage_default);
        return rootView;
    }

    @Override
    public void onResume() {

        super.onResume();

        if(localdata.naver_id == null) {
            txt_mypage_default.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }
        else {
            api.getfavoriteWebtoonList(localdata.naver_id, new Callback<List<webtoonListMyFavorite>>() {

                @Override
                public void success(List<webtoonListMyFavorite> webtoonListFavorites, Response response) {
                    recyclerView.setAdapter(new webtoonMyFavoriteAdapter(getActivity(), webtoonListFavorites));
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Toast.makeText(getContext(), "Failed to load thumbnails", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}