package com.newspaper.utils.MyView;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by mephisto- on 2016/11/1.
 */

public class Snack {
    public static void showForError(View view)
    {
        Snackbar.make(view,"网络异常",Snackbar.LENGTH_LONG).setAction("刷新", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
