package gabriel.com.nwebtoon_android.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import gabriel.com.nwebtoon_android.R;
import gabriel.com.nwebtoon_android.model.LocalData;
import gabriel.com.nwebtoon_android.network.Controller;

public class SettingActivity extends Activity {

    LinearLayout layout_setting_back ;
    CheckBox chk_rotation, chk_scroll, chk_network ;
    LocalData localdata ;


    private static String OAUTH_CLIENT_ID = "yxvQuXWQY72biKivtbLq";
    private static String OAUTH_CLIENT_SECRET = "Qn7TjzRMUj";
    private static String OAUTH_CLIENT_NAME = "네이버 아이디로 로그인";
    private OAuthLoginButton mOAuthLoginButton;
    private static OAuthLogin mOAuthLoginInstance;
    static private Context mContext;
    ImageView btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_setting);
        localdata = LocalData.findById(LocalData.class, 1);

        layout_setting_back = (LinearLayout) findViewById(R.id.layout_setting_back);
        layout_setting_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //Naver Api Initialization
        btn_logout = (ImageView) findViewById(R.id.btn_logout);
        Glide.with(this).load(R.drawable.ic_logout).into(btn_logout);
        mOAuthLoginHandler = new OAuthLoginHandler() {
            @Override
            public void run(boolean success) {
                if (success) {
                    String accessToken = mOAuthLoginInstance.getAccessToken(mContext);
                    String refreshToken = mOAuthLoginInstance.getRefreshToken(mContext);
                    long expiresAt = mOAuthLoginInstance.getExpiresAt(mContext);
                    String tokenType = mOAuthLoginInstance.getTokenType(mContext);
                /*mOauthAT.setText(accessToken);
                mOauthRT.setText(refreshToken);
                mOauthExpires.setText(String.valueOf(expiresAt));
                mOauthTokenType.setText(tokenType);
                mOAuthState.setText(mOAuthLoginInstance.getState(mContext).toString());*/
                    Log.e("MainActivity##", "OAuthLoginHandler run - success");
                    RequestApiTask requestApi = (RequestApiTask) new RequestApiTask().execute();

                } else {
                    Log.e("MainActivity##", "OAuthLoginHandler run - not success");
                    String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
                    String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);
                    Log.e("##errocode", "errorCode:" + errorCode + ", errorDesc:" + errorDesc);
                    Toast.makeText(mContext, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
                }
            }
        };
        mContext = this;
        mOAuthLoginInstance = Controller.getInstance().getNaverApi();
        mOAuthLoginInstance.init(this, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);
        mOAuthLoginButton = (OAuthLoginButton) findViewById(R.id.btn_login);
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);
        Glide.with(this).load(R.drawable.ic_login).into(mOAuthLoginButton);
        mOAuthLoginHandler.run(true);
        //mOAuthLoginInstance.startOauthLoginActivity(MainActivity.this, mOAuthLoginHandler);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context appContext = getApplicationContext();
                mOAuthLoginInstance.logout(mContext);
                RequestApiTask requestApi = (RequestApiTask) new RequestApiTask().execute();
                if (appContext != null) {
                    Toast.makeText(appContext, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
                }
                Controller.getInstance().flushNaverUserData();
                localdata.initnaver_id();
                localdata.save();
            }
        });


        chk_rotation = (CheckBox) findViewById(R.id.chk_rotation);
        chk_rotation.setChecked(localdata.getRotation());
        chk_rotation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                localdata.setRotation(isChecked);
                localdata.save();
            }
        });
        chk_scroll = (CheckBox) findViewById(R.id.chk_scroll);
        chk_scroll.setChecked(localdata.getScroll());
        chk_scroll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                localdata.setScroll(isChecked);
                localdata.save();
            }
        });
        chk_network = (CheckBox) findViewById(R.id.chk_network);
        chk_network.setChecked(localdata.getNetwork());
        chk_network.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                localdata.setNetwork(isChecked);
                localdata.save();
            }
        });
    }

    static private OAuthLoginHandler mOAuthLoginHandler = null;

    //RequestApiTask requestApi = (RequestApiTask) new RequestApiTask().execute();

    private class RequestApiTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            Log.e("MainActivity##","onPreExecute()");
            mOAuthLoginButton.setVisibility(View.GONE);
            btn_logout.setVisibility(View.GONE);
            //mApiResultText.setText((String) "");
        }
        @Override
        protected String doInBackground(Void... params) {
            String url = "https://openapi.naver.com/v1/nid/getUserProfile.xml";
            String at = mOAuthLoginInstance.getAccessToken(mContext);
            return mOAuthLoginInstance.requestApi(mContext, at, url);
        }
        protected void onPostExecute(String content) {
            try{
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                InputStream istream = new ByteArrayInputStream(content.getBytes("utf-8"));
                Document doc = builder.parse(istream);
                Element order = doc.getDocumentElement();

                NodeList items = order.getElementsByTagName("message");
                Node item = items.item(0);
                Node text = item.getFirstChild();
                String ItemName = text.getNodeValue();
                Log.e("XML parser##",ItemName);
                Log.e("XML parser##",String.valueOf(ItemName.equals("success")));
                Log.e("XML parser##",mOAuthLoginInstance.getState(mContext).toString());
                if(ItemName.equals("success") && mOAuthLoginInstance.getState(mContext).toString().equals("OK")){//로그인된 상태
                    Log.e("auth response##",content);
                    //Authentication success
                    items = order.getElementsByTagName("email");
                    item = items.item(0);
                    text = item.getFirstChild();
                    Controller.naverUserData.email = text.getNodeValue();

                    localdata.setnaver_id( Controller.naverUserData.email.split("@")[0]);
                    localdata.save();

                    items = order.getElementsByTagName("nickname");
                    item = items.item(0);
                    text = item.getFirstChild();
                    Controller.naverUserData.nickname = text.getNodeValue();

                    items = order.getElementsByTagName("enc_id");
                    item = items.item(0);
                    text = item.getFirstChild();
                    Controller.naverUserData.enc_id = text.getNodeValue();

                    items = order.getElementsByTagName("profile_image");
                    item = items.item(0);
                    text = item.getFirstChild();
                    Controller.naverUserData.profile_image = text.getNodeValue();

                    items = order.getElementsByTagName("age");
                    item = items.item(0);
                    text = item.getFirstChild();
                    Controller.naverUserData.age = text.getNodeValue();

                    items = order.getElementsByTagName("gender");
                    item = items.item(0);
                    text = item.getFirstChild();
                    Controller.naverUserData.gender = text.getNodeValue();

                    items = order.getElementsByTagName("id");
                    item = items.item(0);
                    text = item.getFirstChild();
                    Controller.naverUserData.id = text.getNodeValue();

                    items = order.getElementsByTagName("name");
                    item = items.item(0);
                    text = item.getFirstChild();
                    Controller.naverUserData.name = text.getNodeValue();

                    items = order.getElementsByTagName("birthday");
                    item = items.item(0);
                    text = item.getFirstChild();
                    Controller.naverUserData.birthday = text.getNodeValue();

                    btn_logout.setVisibility(View.VISIBLE);
                } else {
                    //Authentication failure
                    mOAuthLoginButton.setVisibility(View.VISIBLE);
                }

            }catch (Exception e){
                e.printStackTrace();
                Log.e("NaverLogin##", "로그인 response xml 메시지 파싱 에러");
            }
        }
    }
}
