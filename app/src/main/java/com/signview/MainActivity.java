package com.signview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;
    private boolean mBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.image_view);
    }

    public void sign(View view) {
        startActivity(new Intent(MainActivity.this, SecondActivity.class));
    }

    /**
     * 根据图片路径显示图片的方法
     */
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            mImageView.setImageBitmap(bitmap);
        } else {
            Toast.makeText(MainActivity.this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBack) {
            SharedPreferences sp = getSharedPreferences("sign", Context.MODE_PRIVATE);
            String path = sp.getString("sign", "path");
            if (!"path".equals(path)) {
                displayImage(path);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBack = true;
    }
}
