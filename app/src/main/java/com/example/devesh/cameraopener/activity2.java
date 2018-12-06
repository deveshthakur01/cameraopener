package com.example.devesh.cameraopener;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import static android.app.Activity.RESULT_OK;

public class activity2 extends AppCompatActivity {

    private static final int SELECT_PICTURE = 1;

    private String selectedImagePath;
    private ImageView img;
@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity2);

        img = (ImageView)findViewById(R.id.imageview);

        ((Button) findViewById(R.id.btngallery))
                .setOnClickListener(new OnClickListener()
                {
                    public void onClick(View arg0) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction
                                (Intent.ACTION_GET_CONTENT);
                        startActivityForResult
                                (Intent.createChooser(intent,"Select Picture"),
                                        SELECT_PICTURE);
                    }
                });
    }

    public void onActivityResult(int requestCode, int
            resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath
                        (selectedImageUri);
                System.out.println("Image Path : " +
                        selectedImagePath);
                img.setImageURI(selectedImageUri);
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {
                MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection,
                null, null, null);
        int column_index = cursor.getColumnIndexOrThrow
                (MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}

