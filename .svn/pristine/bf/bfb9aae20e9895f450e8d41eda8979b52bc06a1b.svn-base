package gabriel.com.nwebtoon_android.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

import gabriel.com.nwebtoon_android.R;
import gabriel.com.nwebtoon_android.adapter.commentListAdapter;
import gabriel.com.nwebtoon_android.model.commentList;
import gabriel.com.nwebtoon_android.network.Controller;
import gabriel.com.nwebtoon_android.network.ServerInterface;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CommentViewActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;


    private ViewPager mViewPager;
    Intent intent;
    static String TOON_ID, DT_ID, TOON_NAME, CMT_ID;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_view);

        TOON_ID = null;
        DT_ID = null;
        TOON_NAME = null;
        CMT_ID = null;

        intent = getIntent();
        TOON_ID = intent.getStringExtra("TOON_ID");
        DT_ID = intent.getStringExtra("DT_ID");
        TOON_NAME = intent.getStringExtra("TOON_NAME");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(TOON_NAME);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        tabLayout = (TabLayout) findViewById(R.id.tab_comment_view);
        tabLayout.setupWithViewPager(mViewPager);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comment_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.new_comment) {
            Intent intent = new Intent(getApplicationContext(), InsertCommActivity.class);
            intent.putExtra("TOON_ID", TOON_ID);
            intent.putExtra("DT_ID", DT_ID);
            intent.putExtra("TOON_NAME", TOON_NAME);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {



        private ServerInterface api;
        private static final String ARG_SECTION_NUMBER = "section_number";
        RecyclerView recyclerView;
        int pageNum = 0;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_mypage_list, container, false);
            //final RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_mypage_list, container, false);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_mypage);
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            Bundle args = getArguments();
            pageNum = args.getInt(ARG_SECTION_NUMBER);
            api = Controller.getInstance().getServerInterface();

            if(pageNum == 1){//Best댓글
                 api.getBestCommentList(1, TOON_ID, "0001", new Callback<List<commentList>>() {

                     @Override
                     public void success(List<commentList> commentLists, Response response) {
                         Context context = null;
                         context = getActivity();
                         if (context != null) {
                             recyclerView.setAdapter(new commentListAdapter(context, commentLists));
                         } else {
                             Log.e("CommentViewFragment", "getActivity() returns null value - best comment");
                         }
                     }

                     @Override
                     public void failure(RetrofitError error) {
                         Context appContext = null;
                         appContext = getContext();
                         if (appContext != null) {
                             Toast.makeText(appContext, "Failed to load thumbnails", Toast.LENGTH_LONG).show();
                         }
                     }
                 });
            }
            else if(pageNum == 3){//Random 댓글(최근댓글만 보게되면 다른댓글들은 볼 기회가 적으므로 랜덤페이지 댓글을 볼수 있는 창을 만든다.
                //TODO Random 페이지 댓글 정보를 불러와서 adapter에 연결
            }
            return rootView;
        }
    @Override
        public void onResume(){
        super.onResume();
        if(pageNum == 2) {//최근 댓글
            api.getCommentList(1, DT_ID, "0001", TOON_ID, "1", new Callback<List<commentList>>() {
                /*
                int commentType,String dtId, String ctId, String toonId,String pageNum
                 */
                @Override
                public void success(List<commentList> commentLists, Response response) {
                    Context context = null;
                    context = getActivity();
                    if (context != null) {
                        recyclerView.setAdapter(new commentListAdapter(context, commentLists));
                    } else {
                        Log.e("CommentViewFragment", "getActivity() returns null value - recent comment");
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Context appContext = null;
                    appContext = getContext();
                    if (appContext != null) {
                        Toast.makeText(appContext, "Failed to load thumbnails", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "베스트 댓글";
                case 1:
                    return "최근 댓글";
                case 2:
                    return "랜덤 댓글";
            }
            return null;
        }
    }
}



