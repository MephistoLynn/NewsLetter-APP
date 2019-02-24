package com.newspaper.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.newspaper.R;
import com.newspaper.model.Constant.IntentKey;
import com.newspaper.model.Constant.SharedConstant;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by str on 2016/10/21.
 */

public class WebDetailActivity extends AppCompatActivity {

    @BindView(R.id.wv1)
    WebView webView;
    @BindView(R.id.pb1)
    ProgressBar progressBar;
    @BindView(R.id.web_toolbar)
    Toolbar toolbar;
    private WebSettings webSettings;

    private SharedPreferences sp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_detail);
        ButterKnife.bind(this);

        webSettings = webView.getSettings();
        initView();
        setStatus();

    }

    @Override
    protected void onStart() {
        super.onStart();


    }


    public void setStatus() {

        toolbar.inflateMenu(R.menu.main);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT)
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }


    String Url;
    String title;

    private void initView() {
        sp = getSharedPreferences(SharedConstant.FileName, MODE_PRIVATE);

        //初始化data
        Intent i = getIntent();
        Url = i.getStringExtra(IntentKey.URL_TO_WEB);
        title = i.getStringExtra(IntentKey.TITLE_TO_WEB);
        //初始化webview
        webChromeClient webViewClient = new webChromeClient();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(true);
        webViewClient.shouldOverrideUrlLoading(webView, Url);
        webView.setWebChromeClient(new webChromeClient());

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return;
        } else
            finish();
    }

    public class webChromeClient extends WebChromeClient {

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("http:") || url.startsWith("https:")) {
                view.loadUrl(url);
                return true;
            }
            return false;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            progressBar.setProgress(newProgress);
            if (newProgress == 100) {
                progressBar.setVisibility(view.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    Menu menu;


    //  收藏、分享功能//////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.webview_menu, menu);
        this.menu = menu;

//        检查收藏
       if (checkIsCollected())
       {
           menu.findItem(R.id.action_Collection).setIcon(R.drawable.web_collected);
       }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, webView.getUrl());
            intent.setType("text/plain");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(Intent.createChooser(intent, getTitle()));

        }

        if (id == R.id.action_Collection) {
//收藏事件
            collectionEvent();

        }

        return super.onOptionsItemSelected(item);
    }

    //检查收藏状态
    private boolean checkIsCollected() {
        String url_sp = sp.getString(SharedConstant.Collection_URL, "");
        if (Url.equals(url_sp)) {
            return true;
        }
        return false;
    }

    private void collectionEvent() {

        SharedPreferences.Editor editor = sp.edit();
        //如果已收藏则删除，没有则收藏
        if (checkIsCollected()) {

            editor.putString(SharedConstant.Collection_URL, "");
            editor.putString(SharedConstant.Collection_TITLE, "");
            editor.apply();
            menu.findItem(R.id.action_Collection).setIcon(R.drawable.web_collection);
            Snackbar.make(webView, "已取消", Snackbar.LENGTH_SHORT).show();
        } else {
            editor.putString(SharedConstant.Collection_URL, Url);
            editor.putString(SharedConstant.Collection_TITLE, title);
            editor.apply();
            menu.findItem(R.id.action_Collection).setIcon(R.drawable.web_collected);
            Snackbar.make(webView, "已收藏", Snackbar.LENGTH_SHORT).show();
        }
    }
}
