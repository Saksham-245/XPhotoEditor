package com.sakshammathur25web.xphotoeditor.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.cocosw.bottomsheet.BottomSheet;
import com.mlsdev.rximagepicker.RxImagePicker;
import com.mlsdev.rximagepicker.Sources;
import com.sakshammathur25web.xphotoeditor.BuildConfig;
import com.sakshammathur25web.xphotoeditor.Constants;
import com.sakshammathur25web.xphotoeditor.R;

import java.util.Objects;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("ApplySharedPref")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String action = intent.getAction();
        Uri uri2 = intent.getData();
        if (Intent.ACTION_SEND.equals(action)) {
            if (Objects.requireNonNull(bundle).containsKey(Intent.EXTRA_STREAM)) {
                Uri uri = bundle.getParcelable(Intent.EXTRA_STREAM);
                Intent recipientIntent = new Intent(MainActivity.this, EditorActivity.class);
                recipientIntent.setData(uri);
            }
        } else if (Intent.ACTION_VIEW.equals(action)) {
            Intent recipientsIntent = new Intent(MainActivity.this, EditorActivity.class);
            recipientsIntent.setData(uri2);
            startActivity(recipientsIntent);
            SharedPreferences preferences = getApplicationContext().getSharedPreferences("tapcore_consent", Context.MODE_PRIVATE);
            //Save to SharedPreferences user id, that you generate for this specific user
            preferences.edit().putString("user_id","<USER_ID_THAT_YOU_GENERATE>").apply();
            //Set 'true' if user agreed consent or set 'false' otherwise
            preferences.edit().putBoolean("consent_agreed", true).apply();
            com.sakshammathur25web.xphotoeditor.Runable.run(this);
        }
    }

    private static boolean start(Intent aIntent, Context c) {
        try {
            c.startActivity(aIntent);
            return false;
        } catch (ActivityNotFoundException e) {
            return true;
        }
    }

    @SuppressLint("RestrictedApi")
    private void aboutDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getString(R.string.app_name));
        final WebView webView = new WebView(this);
        String about = "<p>A Photo Editor with many editing tools.</p>" +
                "<p>Developed by <a href='mailto:saksham.mathur25@gmail.com'>Saksham Mathur</a></p>" +
                "<p>Launcher Icon provided by <a href='http://flaticon.com'>Freepix</a>, under the <a href='http://creativecommons.org/licenses/by/3.0/'>Creative Commons license</a>";
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.loadData(about, "text/html", "UTF-8");
        alertDialog.setView(webView, 32, 0, 32, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void camera(View view) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, Constants.CAMERA_PERMISSION);
            } else {
                openCamera();
            }
        } else {
            openCamera();
        }
    }

    @SuppressLint("CheckResult")
    private void openCamera() {
        RxImagePicker.with(this).requestImage(Sources.CAMERA).subscribe(new Consumer<Uri>() {
            @Override
            public void accept(Uri uri) {
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                mediaScanIntent.setData(uri);
                sendBroadcast(mediaScanIntent);
                Intent recipientsIntent = new Intent(MainActivity.this, EditorActivity.class);
                recipientsIntent.setData(uri);
                startActivity(recipientsIntent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.REQUEST_WRITE_STORAGE_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    launchGallery();
                    break;
                } else {
                    Toast.makeText(MainActivity.this, "You need to allow Storage permission first", Toast.LENGTH_SHORT).show();
                }
                break;

            case Constants.CAMERA_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(MainActivity.this, "You need to allow Camera permission first", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode == Constants.SELECT_PHOTO) {
                final Uri imageUri = data.getData();
                Intent recipientIntent2 = new Intent(MainActivity.this, EditorActivity.class);
                recipientIntent2.setData(imageUri);
                startActivity(recipientIntent2);
            }
        }
    }

    public void more(View view){
        openQuickMenuPro();
    }

    public void rate(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id="+BuildConfig.APPLICATION_ID));
        if (start(intent, MainActivity.this)){
            intent.setData(Uri.parse(BuildConfig.APPLICATION_ID));
            if (start(intent, MainActivity.this)){
                Toast.makeText(MainActivity.this,"Please download Google Play Store.",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void gallery(View view){
        if (Build.VERSION.SDK_INT>=23){
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},Constants.REQUEST_WRITE_STORAGE_PERMISSION);
            }else {
                launchGallery();
            }
        }else {
            launchGallery();
        }
    }

    private void launchGallery() {
        Intent photopickerIntent = new Intent(Intent.ACTION_PICK);
        photopickerIntent.setType("image/*");
        startActivityForResult(photopickerIntent,Constants.SELECT_PHOTO);
    }

    public void share(){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Download this cool photo editor made by Saksham Mathur on Google Play.");
        String shareMessage = getString(R.string.app_name)+"is a cool photo editing app for android check is out."+
                "\n https://play.google.com/store/apps/details?id="+BuildConfig.APPLICATION_ID;
        shareIntent.putExtra(Intent.EXTRA_TEXT,shareMessage);
        startActivity(Intent.createChooser(shareIntent,"Share with"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    private void openQuickMenuPro() {
        new BottomSheet.Builder(this).sheet(R.menu.menu).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case R.id.rate:
                        rate();
                        break;
                    case R.id.about:
                        aboutDialog();
                        break;
                }
            }
        }).show();
    }
}