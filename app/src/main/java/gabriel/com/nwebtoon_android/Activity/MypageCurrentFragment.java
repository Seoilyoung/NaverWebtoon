package gabriel.com.nwebtoon_android.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import gabriel.com.nwebtoon_android.R;
import gabriel.com.nwebtoon_android.adapter.webtoonMyCurrentAdapter;
import gabriel.com.nwebtoon_android.model.LocalData_CurrentWebtoon;

/**
 * Created by seoil on 2016-02-15.
 */
public class MypageCurrentFragment extends Fragment {
    private List<LocalData_CurrentWebtoon> CurrentWebtoons;
    private RecyclerView recyclerView ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mypage_list, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_mypage);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        return rootView;
    }

    @Override
    public void onResume() {

        super.onResume();

        if(LocalData_CurrentWebtoon.findById(LocalData_CurrentWebtoon.class, 1) !=null) {
            CurrentWebtoons = LocalData_CurrentWebtoon.listAll(LocalData_CurrentWebtoon.class);
            recyclerView.setAdapter(new webtoonMyCurrentAdapter(getActivity(), CurrentWebtoons));
        }
    }
}