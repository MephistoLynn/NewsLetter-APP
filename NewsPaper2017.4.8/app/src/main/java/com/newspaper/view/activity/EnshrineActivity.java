package com.newspaper.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newspaper.R;
import com.newspaper.model.Constant.IntentKey;
import com.newspaper.model.Constant.SharedConstant;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by str on 2016/10/31.
 */

public class EnshrineActivity extends AppCompatActivity {

    @BindView(R.id.enshrine_toolbar)
    Toolbar toolbar;
    @BindView(R.id.enshrine_textview)
    TextView enshrinetextview;
    @BindView(R.id.enshrine_linear)
    LinearLayout collectionLayout;
    @BindView(R.id.enshrine_imagebutton)
    ImageButton delecteButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_enshrine);
        ButterKnife.bind(this);

        setStatus();
        initCollection();

    }


    private SharedPreferences sp;

    String url;
    String title;

    private void initCollection() {

        sp = getSharedPreferences(SharedConstant.FileName, MODE_PRIVATE);
        url = sp.getString(SharedConstant.Collection_URL, "");
        title = sp.getString(SharedConstant.Collection_TITLE, "");
        if (title.equals("")) {
            collectionLayout.setVisibility(View.INVISIBLE);
        } else {
            collectionLayout.setVisibility(View.VISIBLE);
            enshrinetextview.setText(title);
            initEventCollected();
        }

    }

    private void setStatus() {
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT)
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initEventCollected() {

        collectionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        enshrinetextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnshrineActivity.this, WebDetailActivity.class);
                intent.putExtra(IntentKey.URL_TO_WEB, url);
                intent.putExtra(IntentKey.TITLE_TO_WEB, title);
                startActivity(intent);
            }
        });

        //清除数据
        delecteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.edit().clear().apply();
                initCollection();
            }
        });
    }

}
