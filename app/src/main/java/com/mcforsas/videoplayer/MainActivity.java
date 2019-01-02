package com.mcforsas.videoplayer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


    NotificationCompat.Builder notification;
    private static final int uniqueID = 15454;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button takePhoto = findViewById(R.id.buttonTakePhoto);
        photoView = findViewById(R.id.takenPhotoView);

        //Disable the button if user has no camera
        if(!hasCamera()){
            takePhoto.setEnabled(false);
        }

        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);
    }

    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }


    public void launchCamera(View view) {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Take a picture and pass resul
        startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            //get the photo
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            photo = invertImage(photo);
            photoView.setImageBitmap(photo);

            MediaStore.Images.Media.insertImage(getContentResolver(), photo, "Inverted photo", "Creepy!");
        }
    }

    private Bitmap invertImage(Bitmap image){
        Bitmap result = Bitmap.createBitmap(image.getWidth(), image.getHeight(), image.getConfig());

        int A, R, G, B;
        int pxColor;

        int widht = image.getWidth();
        int height = image.getHeight();

        for(int y = 0; y < height; y++){
            for(int x = 0; x < widht; x++){
                pxColor = image.getPixel(x, y);
                A = Color.alpha(pxColor);
                R = 255 - Color.red(pxColor);
                G = 255 - Color.green(pxColor);
                B = 255 - Color.blue(pxColor);

                result.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        return result;
    }

    public void launchNotification(View view) {
        //Build notification
        notification.setSmallIcon(R.drawable.ic_launcher_background);
        notification.setTicker("This is a ticker");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("Title goes here");
        notification.setContentText("Hello world!");
        Intent i = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        //Build notification
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID, notification.build());
    }

    public void goToLogin(View view) {
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }
}
