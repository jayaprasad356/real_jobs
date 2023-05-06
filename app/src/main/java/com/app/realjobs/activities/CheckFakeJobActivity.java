package com.app.realjobs.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.realjobs.helper.ApiConfig;

import com.app.realjobs.R;
import com.app.realjobs.databinding.ActivityCheckFakeJobBinding;
import com.app.realjobs.helper.Session;

import org.json.JSONException;
import org.json.JSONObject;

import com.app.realjobs.helper.Constant;
import com.theartofdev.edmodo.cropper.CropImage;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CheckFakeJobActivity extends AppCompatActivity {
    private ActivityCheckFakeJobBinding binding;
    Activity activity;
    String filePath1="";
    Uri imageUri;
    Session session;
    private static final int REQUEST_IMAGE_GALLERY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckFakeJobBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        session = new Session(activity);
        binding.tvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, FakeJobHistoryActivity.class);
                startActivity(intent);

            }
        });
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImageFromGallery();

            }
        });
        binding.btnCheck.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (binding.etTitle.getText().toString().isEmpty()){
                    Toast.makeText(activity, "Please Enter Title", Toast.LENGTH_SHORT).show();
                }else if (binding.etDescription.getText().toString().isEmpty()){
                    Toast.makeText(activity, "Please Enter Description", Toast.LENGTH_SHORT).show();
                }else if(filePath1.isEmpty()){
                    Toast.makeText(activity, "Please select image", Toast.LENGTH_SHORT).show();
                }else {
                    order();
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void order() {

        Map<String, String> params = new HashMap<>();
        params.put(Constant.TITLE, binding.etTitle.getText().toString());
        params.put(Constant.DESCRIPTION, binding.etDescription.getText().toString());
        Map<String, String> FileParams = new HashMap<>();
        FileParams.put(Constant.SCREENSHOT, filePath1);


        ApiConfig.RequestToVolleyMulti((result, response) -> {
            if (result) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {

                        Toast.makeText(activity, "" + jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, activity, Constant.CHECKFAKEJOBS, params, FileParams);


    }

    private void pickImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_IMAGE_GALLERY);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_GALLERY) {
                if (requestCode == REQUEST_IMAGE_GALLERY) {
                    imageUri = data.getData();
                    CropImage.activity(imageUri)
//                            .setGuidelines(CropImageView.Guidelines.ON)
//                            .setOutputCompressQuality(90)
//                            .setRequestedSize(300, 300)
//                            .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
//                            .setAspectRatio(1, 1)
                            .start(activity);
                }

            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                assert result != null;

                filePath1 = result.getUri().getPath();

                File imgFile = new File(filePath1);

                if (imgFile.exists()) {

                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    binding.ivImage.setImageBitmap(myBitmap);
                    //order(bookid);

                }

            }


        }
    }
}