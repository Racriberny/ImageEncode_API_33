package com.cristobalbernal.imagenes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class EncodingImg {
    public static Bitmap decode(String sImage){
        byte[] bytes = Base64.decode(sImage,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
}