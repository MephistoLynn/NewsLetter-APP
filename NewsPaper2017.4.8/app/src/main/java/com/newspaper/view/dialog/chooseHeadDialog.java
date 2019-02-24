package com.newspaper.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.newspaper.R;

/**
 * Created by mephisto- on 2017/2/21.
 */

public class chooseHeadDialog extends Dialog implements View.OnClickListener {


    public chooseHeadDialog(Context context) {
        this(context, R.style.Translucent_NoTitle_Dialog);
    }

    public chooseHeadDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    private ViewGroup layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupContent();

    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {

        findViewById(R.id.chooser_btn_to_camera).setOnClickListener(this);
        findViewById(R.id.chooser_btn_to_photo).setOnClickListener(this);
        findViewById(R.id.chooser_btn_to_cancel).setOnClickListener(this);

    }


    private void setupContent() {
        layout = (ViewGroup) LayoutInflater.from(this.getContext()).inflate(R.layout.nav_head_choose, null, false);

        this.setContentView(layout,
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
        Window window = this.getWindow();
        window.setWindowAnimations(R.style.choose_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindow().getWindowManager().getDefaultDisplay().getHeight();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        this.onWindowAttributesChanged(wl);
        this.setCanceledOnTouchOutside(true);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chooser_btn_to_camera:


                break;
            case R.id.chooser_btn_to_photo:
chooseImgInPhoto();

                break;
            case R.id.chooser_btn_to_cancel:
                break;
            default:
                break;
        }
        this.dismiss();
    }

    private void chooseImgInPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        getOwnerActivity().startActivityForResult(intent, 1);

    }


}
