package gabriel.com.nwebtoon_android.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import gabriel.com.nwebtoon_android.R;
import gabriel.com.nwebtoon_android.model.commentList;
import gabriel.com.nwebtoon_android.network.Controller;
import gabriel.com.nwebtoon_android.network.ServerInterface;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class InsertCommActivity extends AppCompatActivity {

    LinearLayout layout_insert_comm_back;
    EditText txt_insert_comm_contents;
    TextView txt_insert_comm_counter,txt_insert_comm_id;
    Button btn_insert_comm_submit;
    int maxCmtLen;
    private ServerInterface api;
    String TOON_ID, DT_ID, TOON_NAME;
    Intent intent;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_comm);
        layout_insert_comm_back = (LinearLayout) findViewById(R.id.layout_insert_comm_back);
        txt_insert_comm_contents = (EditText) findViewById(R.id.txt_insert_comm_contents);
        txt_insert_comm_counter = (TextView) findViewById(R.id.txt_insert_comm_counter);
        txt_insert_comm_id = (TextView) findViewById(R.id.txt_insert_comm_id);
        btn_insert_comm_submit = (Button) findViewById(R.id.btn_insert_comm_submit);
        maxCmtLen = getApplicationContext().getResources().getInteger(R.integer.max_comment_length);

        InputFilter[] filter = new InputFilter[1];
        filter[0] = new InputFilter.LengthFilter(maxCmtLen);
        txt_insert_comm_contents.setFilters(filter);

        api = Controller.getInstance().getServerInterface();
        intent = getIntent();
        TOON_ID = intent.getStringExtra("TOON_ID");
        DT_ID = intent.getStringExtra("DT_ID");
        TOON_NAME = intent.getStringExtra("TOON_NAME");

        if(Controller.getInstance().naverUserData.email != null){
            txt_insert_comm_id.setText(Controller.getInstance().naverUserData.email.toString());
            username = Controller.getInstance().naverUserData.email.substring(0,Controller.getInstance().naverUserData.email.indexOf('@'));
        } else {
            username = "test"+(int)(Math.random()*10000);
            txt_insert_comm_id.setText(username);
        }

        txt_insert_comm_contents.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                txt_insert_comm_counter.setText(s.length() + "/" + maxCmtLen);
            }
        });

        btn_insert_comm_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Controller.getInstance().naverUserData.email != null) {

                    String cmtId, content, dtId, ctId, cmtTp, userId, toonId, pageNum;

                    content = txt_insert_comm_contents.getText().toString();
                    dtId = DT_ID;
                    ctId = "0001";
                    cmtTp = "1";
                    userId = username;
                    toonId = TOON_ID;
                    pageNum = "1";

                    Calendar calendar = Calendar.getInstance();
                    java.util.Date date = calendar.getTime();
                    String today = (new SimpleDateFormat("yyyyMMddHHmmss").format(date));
                    cmtId = MD5(today + userId).substring(0,6);
                    Log.e("##", cmtId);
                    api.postInsertComment(cmtId, content, dtId, ctId, cmtTp, userId, toonId, pageNum, new Callback<List<commentList>>() {

                        @Override
                        public void success(List<commentList> commentLists, Response response) {
                            Context appContext = getApplicationContext();
                            if(appContext != null) {
                                Toast.makeText(appContext, "댓글 달기 성공", Toast.LENGTH_SHORT).show();
                            }
                            finish();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Context appContext = getApplicationContext();
                            if(appContext != null) {
                                Toast.makeText(appContext, "댓글 달기 실패", Toast.LENGTH_SHORT).show();
                            }
                            Log.e("Comment##", error.toString());
                        }
                    });

                    //postInsertComment(String cmtId, String content, String dtId, String ctId, String cmtTp, String userId, String toonId, String pageNum, Callback<List<commentList>> callback);
                } else {
                    Toast.makeText(getApplicationContext(),"댓글 기능은 로그인 후 이용하실 수 있습니다.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

        layout_insert_comm_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public static String MD5(String str) {
        String MD5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for(int i=0; i <byteData.length; i++) {
                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
            }
            MD5 = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            MD5 = null;
        }
        return MD5;
    }
}
