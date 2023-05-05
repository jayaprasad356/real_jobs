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

import com.app.realjobs.R;
import com.app.realjobs.databinding.ActivityLoginBinding;
import com.app.realjobs.databinding.ActivityPaymentBinding;
import com.app.realjobs.helper.ApiConfig;
import com.app.realjobs.helper.Constant;
import com.app.realjobs.helper.Session;
import com.canhub.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {

    ActivityPaymentBinding binding;
    Session session;
    Activity activity;
    String filePath1;
    Uri imageUri;
    private static final int REQUEST_IMAGE_GALLERY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        session = new Session(activity);

        binding.cardView.setOnClickListener(v -> {
            pickImageFromGallery();
        });
        binding.uploadImage.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                order();
            }
        });
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_IMAGE_GALLERY);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void order() {

        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID, session.getData(Constant.USER_ID));
        Map<String, String> FileParams = new HashMap<>();
        FileParams.put(Constant.IMAGE, filePath1);


        ApiConfig.RequestToVolleyMulti((result, response) -> {
            if (result) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {

                        Toast.makeText(activity, "" + jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        JSONArray userArray = jsonObject.getJSONArray(Constant.DATA);

                        session.setData(Constant.PAYMENT_STATUS, userArray.getJSONObject(0).getString(Constant.MOBILE));


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, activity, Constant.PAYMENTS, params, FileParams);


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

                filePath1 = result.getUriFilePath(activity, true);

                File imgFile = new File(filePath1);

                if (imgFile.exists()) {
                    binding.uploadImage.setEnabled(true);

                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                    binding.ivImage.setImageBitmap(myBitmap);

                    //order(bookid);

                }

            }


        }
    }
}