package gabriel.com.nwebtoon_android.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.util.List;
import gabriel.com.nwebtoon_android.R;
import gabriel.com.nwebtoon_android.adapter.webtoonListMainAdapter;
import gabriel.com.nwebtoon_android.model.webtoonListDay;
import gabriel.com.nwebtoon_android.network.Controller;
import gabriel.com.nwebtoon_android.network.ServerInterface;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ToonListFragment extends Fragment {

    private ServerInterface api;
    public static Fragment newInstance(int arg1) {
        Fragment result = new ToonListFragment();
        Bundle args = new Bundle();
        args.putInt("pageNum",arg1);
        result.setArguments(args);
        return result;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_mypage_list, container, false);
        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_mypage);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        int pageNum = 0;
        Bundle args = getArguments();
        pageNum = args.getInt("pageNum");

        api = Controller.getInstance().getServerInterface();
        api.getwebtoonLIstDays(pageNum, new Callback<List<webtoonListDay>>() {

            @Override
            public void success(List<webtoonListDay> webtoonListDays, Response response) {
                Context context = null;
                context= getActivity();
                if(context != null) {
                    recyclerView.setAdapter(new webtoonListMainAdapter(context, webtoonListDays));
                }
                else{
                    Log.e("ToonListFragment","getActivity() returns null value");
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Context appContext = null;
                appContext = getContext();
                if(appContext != null) {
                    Toast.makeText(getContext(), "Failed to load thumbnails", Toast.LENGTH_LONG).show();
                }
            }
        });
        return rootView;
    }

}
