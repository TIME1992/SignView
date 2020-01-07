package com.signview;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @time 2020-01-06
 */
public class SecondActivity extends Activity {

    private SignView mSignView;
    private FrameLayout mProgressBar;
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            finish();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mSignView = findViewById(R.id.linepathview);
        mProgressBar = findViewById(R.id.progressBar);
    }

    public void sign(View view) {
        File image = createImageFile();
        mSignView.save(image, false, 0);
        SharedPreferences sp = getSharedPreferences("sign", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("sign", image.getAbsolutePath());
        editor.apply();
        mProgressBar.setVisibility(View.VISIBLE);
        mHandler.sendEmptyMessageDelayed(1,3000);
    }

    public void clear(View view){
        mSignView.clear();
    }

    private File createImageFile() {
        File imagePath = new File(getExternalFilesDir(""), "images");
        if (!imagePath.exists()) {
            imagePath.mkdirs();
        }

        File image = new File(imagePath, generateFileName() + ".png");
        if (!image.exists()) {
            try {
                image.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return image;
    }

    public static String generateFileName() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }
}
