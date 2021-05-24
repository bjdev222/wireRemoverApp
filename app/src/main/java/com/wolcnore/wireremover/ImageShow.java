package com.wolcnore.wireremover;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import at.markushi.ui.CircleButton;

public class ImageShow extends AppCompatActivity {

    ImageView imageView;
    String url;

    CircleButton download,share;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);
        Intent i = getIntent();
        String s = i.getStringExtra("key");
        Log.e("new url 2 is",s);

        download=findViewById(R.id.download);

        share=findViewById(R.id.share);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadImage();

            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareImage();
            }
        });

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.animation)
                .error(R.drawable.loaderr)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();
        TextView desc2=(TextView)findViewById(R.id.desc2);

        //url="https://firebasestorage.googleapis.com/v0/b/dscscet-c0923.appspot.com/o/images%2FWhatsApp%20Image%202020-12-03%20at%2012.22.24%20PM.jpeg?alt=media&token=23565563-8b34-45a5-a7c0-7824466ad9ed";

        url=s;
        imageView=findViewById(R.id.FinalImage);

        Glide.with(this).load(url).apply(options).into(imageView);
        desc2.setVisibility(View.GONE);


    }

    public void DownloadImage() {

        DownloadManager downloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downloadManager.enqueue(request);
        Toast.makeText(this, "Downloading start..Check Your Notification...", Toast.LENGTH_LONG).show();
       /* if(interstitialAd != null && interstitialAd.isAdLoaded())
        {
            interstitialAd.show();
        }*/

    }


    public void ShareImage(){

        Bitmap bitmap=getBitmapFromView(imageView);
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        try {

            File file=new File(this.getExternalCacheDir(),"wallpaper.jpg");
            FileOutputStream fout=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fout);
            fout.flush();
            fout.close();
            file.setReadable(true,false);
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_TEXT, "Download this Amazing Wire Remover app from here" +
                    " https://play.google.com/store/apps/details?id=" + ImageShow.this.getPackageName());
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));

            intent.setType("*/*");

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            // Toast.makeText(this, "Image", Toast.LENGTH_SHORT).show();
            startActivity(Intent.createChooser(intent,"Share Image"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("ResourceAsColor")
    private Bitmap getBitmapFromView(View view){
        Bitmap returnBitmap=Bitmap.createBitmap(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(returnBitmap);
        Drawable bgDrawable=view.getBackground();
        if(bgDrawable !=null){
            bgDrawable.draw(canvas);
        }else{
            canvas.drawColor(android.R.color.white);
        }
        view.draw(canvas);
        return returnBitmap;
    }
}