package com.tommyputranto.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.tommyputranto.myapplication.api.core.MySubscriber;
import com.tommyputranto.myapplication.dagger.Injector;
import com.tommyputranto.myapplication.main.MainRequest;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    @BindView(R.id.edt_name) EditText edtname;
    @BindView(R.id.edt_alamat) EditText edtalamat;
    @BindView(R.id.edt_email) EditText edtemail;
    @BindView(R.id.edt_jenkel) EditText edtjenkel;
    @BindView(R.id.photo) CropImageView photo;

    private String id = "1_aGxkqY7nfH6cLVVfCSZji7RRMS0lA_Hefd83sDaolU"; // ID KEY GOOGLE SHEET
    private String sheet = "data"; // NAME SHEET

    private String imageEncodeBase64 = "";

    @Inject
    MainRequest mRequest;

    @NeedsPermission(Manifest.permission.CAMERA)
    void showCamera(){

    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void readStorage(){

    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void writeStorage(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Injector.component.inject(this);
        MainActivityPermissionsDispatcher.showCameraWithCheck(this);
        MainActivityPermissionsDispatcher.readStorageWithCheck(this);
        MainActivityPermissionsDispatcher.writeStorageWithCheck(this);
    }

    public void pickImage(View view) {
        CropImage.startPickImageActivity(this);
    }

    public void uploadImage(View view){
        showProgressDialog();
        mRequest.requestUpload(id, edtname.getText().toString(),edtalamat.getText().toString(), edtemail.getText().toString(), edtjenkel.getText().toString(), imageEncodeBase64, sheet)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<MainRequest.SuccessResponse>() {
                    @Override
                    public void onError(String errorMessage) {
                        hideProgressDialog();
                        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNextAndCompleted(MainRequest.SuccessResponse successResponse) {
                        hideProgressDialog();
                        Toast.makeText(getApplicationContext(), successResponse.getStatus(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            startCropImageActivity(imageUri);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    updateProfile(result.getUri());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setFixAspectRatio(true)
                .start(this);
    }

    public void updateProfile(Uri uri) throws IOException {
        if (uri == null) {
            photo.setImageResource(R.mipmap.ic_launcher);
        }
        photo.setImageUriAsync(uri);
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
        encode64Bitmap(bitmap);
    }

    public void encode64Bitmap(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        imageEncodeBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);

    }

    public void showProgressDialog() {
        mProgressDialog = ProgressDialog.show(this, null, getResources().getString(R.string.progress_message));
        mProgressDialog.show();
        mProgressDialog.setCancelable(false);
    }


    public void hideProgressDialog() {
        mProgressDialog.dismiss();
    }


}
