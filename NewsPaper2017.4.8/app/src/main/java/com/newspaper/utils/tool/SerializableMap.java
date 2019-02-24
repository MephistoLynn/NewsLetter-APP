package com.newspaper.utils.tool;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by mephisto- on 2016/10/30.
 */

public class SerializableMap implements Serializable {
    private Map<String, Bitmap> map;

    public Map<String, Bitmap> getMap() {
        return map;
    }

    public void setMap(Map<String, Bitmap> map) {
        this.map = map;
    }
}
