package com.example.dialogopencamera;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getName();
    private final int REQUEST_CODE = 1111;
    private final int REQUEST_CODE_VIDEO = 222;

    private ImageView mImageView;
    private TextView mTextViewClickHere;
    private Button mButtonPicture;
    private Button mButtonVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.image);
        mTextViewClickHere = (TextView) findViewById(R.id.txtClickHere);

        mTextViewClickHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.layout_dialog);
        dialog.show();
        mButtonPicture = (Button) dialog.findViewById(R.id.btnPicture);
        mButtonVideo = (Button) dialog.findViewById(R.id.btnVideo);
        mButtonPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE);
                dialog.dismiss();
            }
        });
        mButtonVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_VIDEO);
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //image
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            mImageView.setImageBitmap(bitmap);

        }else if (requestCode == REQUEST_CODE_VIDEO && resultCode == RESULT_OK){

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(data.getData(), "video/mp4");
            startActivity(intent);
        }
    }


}
