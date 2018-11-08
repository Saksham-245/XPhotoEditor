package com.sakshammathur25web.xphotoeditor.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.adobe.creativesdk.aviary.AdobeImageIntent;
import com.adobe.creativesdk.aviary.internal.headless.utils.MegaPixels;
import com.sakshammathur25web.xphotoeditor.Constants;
import com.sakshammathur25web.xphotoeditor.R;
import com.sakshammathur25web.xphotoeditor.Utils;

public class EditorActivity extends AppCompatActivity {
    Uri editedImageUri;
    protected Uri mMediaUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        try {
            mMediaUri = getIntent().getData();
        }catch (Exception e){
            e.printStackTrace();
        }
        Uri saved = Utils.getOutPutMeidaFileUri(this,Constants.MEDIA_TYPE_IMAGE,getString(R.string.app_name));

        assert saved != null;
        Intent imageEditorIntent = new AdobeImageIntent.Builder(this)
                .setData(mMediaUri)
                .withOutput(saved)
                .withOutputFormat(Bitmap.CompressFormat.JPEG)
                .withOutputQuality(90)
                .withOutputSize(MegaPixels.Mp30)
                .build();
        startActivityForResult(imageEditorIntent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case 1:
                    editedImageUri = data.getParcelableExtra(AdobeImageIntent.EXTRA_OUTPUT_URI);
                    Toast.makeText(this,"Saved to /sdcard/"+getString(R.string.app_name),Toast.LENGTH_SHORT).show();
                    break;
            }
        }else {
            Intent intent = new Intent(this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    public void shareImage(View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*image/*");
        intent.putExtra(Intent.EXTRA_STREAM,editedImageUri);
        startActivity(Intent.createChooser(intent,"Share with?"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
