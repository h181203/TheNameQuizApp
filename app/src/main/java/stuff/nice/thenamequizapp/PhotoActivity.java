package stuff.nice.thenamequizapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Debug;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class PhotoActivity extends AppCompatActivity {

    public static final String EXTRA_GUY = "GUY";
    public static final String EXTRA_URI = "URI";

    ImageView photoView;
    Uri currentUri;
    String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissionRequests();
        setContentView(R.layout.activity_photo);
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        photoView = findViewById(R.id.imageView);
    }

    public void goToCamera(View view){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {








            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            //...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                Uri photoURI = FileProvider.getUriForFile(
                        this,
                        BuildConfig.APPLICATION_ID,
                        photoFile
                );



                System.out.println("asdf       " +photoURI);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 1);
            }

        }
    }

    public void goToGallery(View view){
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 2);
        }
    }


    public void deletePeople(View view){
        Intent intent = new Intent(this, DeleteActivity.class);
    }


        private File createImageFile() throws IOException{
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "img" + timeStamp + "_";

            File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(imageFileName,".jpg", storageDir);

            // Save a file: path for use with ACTION_VIEW intents
            String mCurrentPhotoPath;
            mCurrentPhotoPath = image.getAbsolutePath();
            return image;
        }




        private void permissionRequests(){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 4);
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.MANAGE_DOCUMENTS}, 3);
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 1);
        }



    public void addGuy(View view){
        EditText guyName = (EditText) findViewById(R.id.guyName);
        Intent intent  = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_GUY, guyName.getText().toString());
        intent.putExtra(EXTRA_URI, currentPhotoPath);
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            Uri selectedImage = data.getData();
            currentUri = selectedImage;
            currentPhotoPath = "content://media" + selectedImage.getPath();
            photoView.setImageURI(selectedImage);
            photoView.getLayoutParams().height = 500;
            photoView.getLayoutParams().width = 500;

    }

}
