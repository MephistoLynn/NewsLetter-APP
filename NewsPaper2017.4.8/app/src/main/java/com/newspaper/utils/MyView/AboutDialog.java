package com.newspaper.utils.MyView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.newspaper.R;

/**
 * Created by mephisto- on 2016/11/1.
 */

public class AboutDialog extends Dialog {
    Context context;

    public AboutDialog(Context context) {
        super(context);
        this.context = context;
    }

    public AboutDialog(Context context, int themeResId, Context context1) {
        super(context, themeResId);
        this.context = context1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setWindowSize();
        setContentView(R.layout.about_dialog);

    }

    private void setWindowSize() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        Window window = this.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = width / 4 * 3;
        lp.height = height / 3 * 2;
        window.setAttributes(lp);
    }
}
