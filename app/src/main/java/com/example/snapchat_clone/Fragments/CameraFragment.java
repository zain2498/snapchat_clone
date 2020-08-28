package com.example.snapchat_clone.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.snapchat_clone.FindUser;
import com.example.snapchat_clone.R;
import com.example.snapchat_clone.ShowCaptureImage;
import com.example.snapchat_clone.SplashScreen.Splash_Screen;
import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class CameraFragment extends Fragment implements SurfaceHolder.Callback {

    final int CAMERA_REQUEST_CODE = 1;
    Camera camera;
    SurfaceView mySurfaceVW;
    SurfaceHolder mySufraceHolder;

    Camera.PictureCallback jpegCallback;

    Button btn_logout, btn_capture, btn_findUser;

    public static CameraFragment newInstance(){
        CameraFragment cameraFragment = new CameraFragment();
        return cameraFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vw;
        vw = inflater.inflate(R.layout.fragment_camera,container,false);

        //handler join the layout with the variable !!!
        mySurfaceVW = vw.findViewById(R.id.surfaceVW);
        //holder basically controls the surfaceVw and it also allows us to add something in the surfaceVW
        mySufraceHolder = mySurfaceVW.getHolder();

       if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA)
               != PackageManager.PERMISSION_GRANTED){
           ActivityCompat.requestPermissions(getActivity(), new String[]{ android.Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
       }else {

           mySufraceHolder.addCallback(this);
           mySufraceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
       }
        // --------buttons handler--------
       btn_findUser = (Button) vw.findViewById(R.id.btn_finduser);
       btn_logout = (Button) vw.findViewById(R.id.btn_logout);
       btn_capture = (Button) vw.findViewById(R.id.btn_capture);
       btn_logout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //method call
               Logout();
           }
       });

       //FIND USER ON CLICK LISTERNER

        btn_findUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                find_Users();
            }
        });

       // ------CAPTURE BUTTON ONCLICK LISTENER
        btn_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //----function call
                captureImage();
            }
        });

        jpegCallback = new Camera.PictureCallback(){
            @Override
            public void onPictureTaken(byte[] bytes, Camera camera) {
            Bitmap decodeBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            Bitmap rotateBitmap = rotate(decodeBitmap);

            String fileLocation = SaveImagetoStorage(rotateBitmap);
            if (fileLocation  != null){
                    Intent i = new Intent(getActivity(), ShowCaptureImage.class);
                    startActivity(i);
                    return;
                }
            }
        };
        return vw;
    }


    // --------Saving the image temporarily to a file ---------
    public String SaveImagetoStorage(Bitmap bitmap){
        String filename = "imageToSend";
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
            FileOutputStream fo = getContext().openFileOutput(filename, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            fo.close();
        }catch (Exception e){
            e.printStackTrace();
            filename = null;
        }
        return filename;
    }

    private Bitmap rotate(Bitmap decodeBitmap) {
        int width = decodeBitmap.getWidth();
        int height = decodeBitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.setRotate(90);

        return Bitmap.createBitmap(decodeBitmap, 0, 0, width, height, matrix, true);

    }

    //------------PICTURE TAKEN FUNCTION--------------
    private void captureImage() {
        camera.takePicture(null,null, jpegCallback);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        camera = Camera.open();

        Camera.Parameters parameters;
        parameters = camera.getParameters();
        camera.setDisplayOrientation(90);
        parameters.setPreviewFrameRate(30);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);

        Camera.Size bestSize = null;
        List<Camera.Size> sizeList = camera.getParameters().getSupportedPreviewSizes();
        bestSize = sizeList.get(0);

        for (int i = 1; i < sizeList.size(); i++ ){
            if ((sizeList.get(i).width* sizeList.get(i).height) > (bestSize.width * bestSize.height)){
                bestSize = sizeList.get(i);
            }
        }
        parameters.setPreviewSize(bestSize.width, bestSize.height);
        camera.setParameters(parameters);

        try {
            camera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mySufraceHolder.addCallback(this);
                    mySufraceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
                } else {
                    Toast.makeText(getContext(), "pls provide the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

// ------SignOut method -----
    private void Logout() {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(getContext(), Splash_Screen.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        return;
    }

    // ------------- FIND USER FUNCTION -----------
    private void find_Users() {
        Intent i = new Intent(getContext(), FindUser.class);
        startActivity(i);
        return;
    }
}
