package in.developer.justbeforeyousleep;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import in.developer.justbeforeyousleep.utils.Functions;

public class FullScreenActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView fullscreen_iv;
    FloatingActionMenu fabMenu;
    FloatingActionButton fabDownload, fabSetWallpaper;
    Bitmap photoBitmap;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        init();
        setImage();
    }

    private void init() {
        fullscreen_iv = findViewById(R.id.fullscreen_img_view);
        fabMenu = findViewById(R.id.activity_fullscreen_photo_fab_menu);
        fabDownload = findViewById(R.id.fab_download);
        fabDownload.setOnClickListener(this);
        fabSetWallpaper = findViewById(R.id.setwallpaper);
        fabSetWallpaper.setOnClickListener(this);
    }

    public void setImage() {
        Bundle extras = getIntent().getExtras();
        byte[] image = extras.getByteArray("imageResource");
        Bitmap fo = BitmapFactory.decodeByteArray(image, 0, image.length);
        fullscreen_iv.setImageBitmap(fo);
        photoBitmap = fo;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_download:
                saveImage(photoBitmap);
                checkPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        STORAGE_PERMISSION_CODE);
                break;
            case R.id.setwallpaper:
                setFabWallpaper();
                break;
        }
    }

    public void setFabWallpaper() {
        if (photoBitmap != null) {
            if (Functions.setWallpaper(FullScreenActivity.this, photoBitmap)) {
                Toast.makeText(this, "Set Wallpaper successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Set Wallpaper fail", Toast.LENGTH_LONG).show();
            }
        }
        fabMenu.close(true);
    }

    public void saveImage(Bitmap resource) {

        String savedImagePath = null;
        String imageFileName = "image" + ".png";


        final File storageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/JBSThoughts");

        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }

        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                resource.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Add the image to the system gallery
            galleryAddPic(savedImagePath);
            Toast.makeText(FullScreenActivity.this, "IMAGE SAVED in JBSThoughts", Toast.LENGTH_LONG).show();
        }
    }

    // Add the image to the system gallery
    private void galleryAddPic(String imagePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    // Function to check and request permission.
    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(FullScreenActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(FullScreenActivity.this,
                    new String[]{permission},
                    requestCode);
        } else {
            Toast.makeText(FullScreenActivity.this,
                    "Permission already granted",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    // This function is called when the user accepts or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when the user is prompt for permission.

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

//        if (requestCode == CAMERA_PERMISSION_CODE) {
//            if (grantResults.length > 0
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(FullScreenActivity.this,
//                        "Camera Permission Granted",
//                        Toast.LENGTH_SHORT)
//                        .show();
//            } else {
//                Toast.makeText(FullScreenActivity.this,
//                        "Camera Permission Denied",
//                        Toast.LENGTH_SHORT)
//                        .show();
//            }
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(FullScreenActivity.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
//                Toast.makeText(FullScreenActivity.this,
//                        "Storage Permission Denied",
//                        Toast.LENGTH_SHORT)
//                        .show();
            }
        }
    }
}
