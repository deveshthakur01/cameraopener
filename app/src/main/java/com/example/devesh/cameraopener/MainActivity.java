package com.example.devesh.cameraopener;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;


public class MainActivity extends AppCompatActivity implements View.OnClickListner {
    private Button btncapture, btngallery;
    private ImageView imageView;
    private File file;
    private Uri uri;
    private Intent CamIntent,GalIntent,CropIntent;
    public static final int RequestPermissionCode = 1;
    private DisplayMetrics displayMetrics;
    int width,height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUI();
    }

    public void setUI() {

        btncapture = (Button) findViewById(R.id.btncapture);
        btngallery = (Button) findViewById(R.id.btngallery);
        imageView = (ImageView) findViewById(R.id.imageview);

        EnableRuntimePermission();
    }

    @Override
            public void onClick(View view) {
        switch (view.getId()){

            case R.id.btncapture:  //btn2
                ClickImageFromCamera();
                break;

            case R.id.btngallery:  //btn1
                GetImageFromGallery();
                break;

            }
        }
    private void GetImageFromGallery(){
        GalIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(GalIntent,"Select image from gallery"), 2);
    }

    private void ClickImageFromCamera()

    {
        CamIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        file = new File(Environment.getExternalStorageDirectory(), "file" + String.valueOf(System.currentTimeMillis()) + ".jpeg");
        uri = Uri.fromFile(file);

        CamIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
        CamIntent.putExtra("return-data", true);

        startActivityForResult(CamIntent, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode ==0 && resultCode == RESULT_OK){
    ImageCropFunction();
    }
    else if (requestCode == 2){
        if(data != null)
        {
            uri = data.getData();
            ImageCropFunction();
        }
   }
   else if (requestCode == 1){
                if(data != null){
                Bundle bundle = data.getExtras();
                Bitmap bitmap = bundle.getParcelable("data");
                imageView.setImageBitmap(bitmap);
            }
        }
        }

        public void ImageCropFunction() {
            try {
                CropIntent = new Intent("com.android.camera.action.CROP");
                CropIntent.setDataAndType(uri, "image/*");
                CropIntent.putExtra("crop", true);
                CropIntent.putExtra("outputX", 180);
                CropIntent.putExtra("outputY", 180);
                CropIntent.putExtra("aspectX", 3);
                CropIntent.putExtra("aspectY", 4);
                CropIntent.putExtra("scaleUpIfneeded", true);
                CropIntent.putExtra("return-data", true);

                startActivityForResult(CropIntent);
                //img crop code
            } catch (ActivityNotFoundException e) {
            }
        }

        public void EnableRuntimePermission(){

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA))
            {
                Toast.makeText(MainActivity.this,"CAMERA permission allows us to Access CAMERA app",Toast.LENGTH_LONG).show();
            }
            else{
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                        Manifest.permission.CAMERA}, RequestPermissionCode);
                }
            }
    }