package com.example.aplikacja4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.aplikacja4.fragments.FragmentActivity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private DrawingSurface mDrawingSurface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawingSurface = findViewById(R.id.drawing_surface);
        Listeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDrawingSurface.resumeDrawing();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDrawingSurface.pauseDrawing();
    }

    private void Listeners(){
        Button redButton = findViewById(R.id.redButton);
        Button orangeButton = findViewById(R.id.orangeButton);
        Button blueButton = findViewById(R.id.blueButton);
        Button greenButton = findViewById(R.id.greenButton);
        Button clearButton = findViewById(R.id.clearButton);

        redButton.setOnClickListener(view -> mDrawingSurface.setPaint(0xFFCC0000));
        orangeButton.setOnClickListener(view -> mDrawingSurface.setPaint(0xFFFF8800));
        blueButton.setOnClickListener(view -> mDrawingSurface.setPaint(0xFF0099CC));
        greenButton.setOnClickListener(view -> mDrawingSurface.setPaint(0xFF669900));
        clearButton.setOnClickListener(view -> mDrawingSurface.clear());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.save){
            saveImagePermissions();
            return true;
        } else if (id == R.id.showAll) {
            browseImagesPermissions();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void browseImages(){
        Intent intent = new Intent(MainActivity.this, FragmentActivity.class);
        MainActivity.this.startActivity(intent);
    }

    private static final int WRITE_CODE = 1;
    private static final int READ_CODE = 2;

    private void browseImagesPermissions() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED ||
                android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            browseImages();
        } else {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.READ_EXTERNAL_STORAGE}, READ_CODE);
        }
    }

    private void saveImagePermissions() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ||
                android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            saveImage();
        } else {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int taskCode, @NonNull String[] permissions, @NonNull int[] decisions) {
        super.onRequestPermissionsResult(taskCode, permissions, decisions);
        if (taskCode == WRITE_CODE) {
            if (permissions.length > 0 && permissions[0]
                    .equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && decisions[0] == PackageManager.PERMISSION_GRANTED) {
                saveImage();
            } else {
                Toast.makeText(this, getString(R.string.no_save_permissons),
                        Toast.LENGTH_LONG).show();
            }
        } else if (taskCode == READ_CODE) {
            if (permissions.length > 0 && permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && decisions[0] == PackageManager.PERMISSION_GRANTED) {
                browseImages();
            } else {
                Toast.makeText(this, getString(R.string.no_browse_permissons),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void saveImage() {
        ContentResolver resolver = getApplicationContext().getContentResolver();

        Uri imageCollection;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            imageCollection = MediaStore.Images.Media
                    .getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        }
        else {
            imageCollection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }

        ContentValues imageDetails = new ContentValues();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH)
                .format(new Date());
        String fileName = "IMG_" + timeStamp + ".png";
        imageDetails.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            imageDetails.put(MediaStore.Images.Media.IS_PENDING, 1);
        }

        Uri imageUri = resolver.insert(imageCollection, imageDetails);

        try (ParcelFileDescriptor pfd =
                     resolver.openFileDescriptor(imageUri, "w", null);
             FileOutputStream fos = new FileOutputStream(pfd.getFileDescriptor())) {

            synchronized (mDrawingSurface) {
                DisplayMetrics metrics = this.getResources().getDisplayMetrics();
                int deviceWidth;
                int deviceHeight;
                int height = metrics.heightPixels;
                int width = metrics.widthPixels;
                if(width < height){
                    deviceWidth  = width;
                    deviceHeight = height;
                }else{
                    deviceWidth  = height;
                    deviceHeight = width;
                }

                int lastRotation = DrawingSurface.getLastRotation();

                Bitmap bitmap = mDrawingSurface.getBitmap();
                if(lastRotation != 0) {
                    lastRotation = (-1) * lastRotation;
                    Matrix matrix = new Matrix();
                    matrix.postRotate(lastRotation);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                            bitmap.getHeight(), matrix, true);
                }

                Bitmap.createScaledBitmap(bitmap,deviceWidth,deviceHeight,false)
                        .compress(Bitmap.CompressFormat.PNG, 100, fos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            imageDetails.clear();
            imageDetails.put(MediaStore.Images.Media.IS_PENDING, 0);
            resolver.update(imageUri, imageDetails, null, null);
        }
    }

}