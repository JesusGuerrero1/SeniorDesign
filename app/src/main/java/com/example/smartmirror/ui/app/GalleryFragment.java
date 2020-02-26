package com.example.smartmirror.ui.app;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.smartmirror.R;
import com.example.smartmirror.data;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class GalleryFragment extends Fragment {
    private ImageView imgPreview;
    private Uri image;
    private String pictureFilePath;
    private String res;
    private static final int REQUEST_PICTURE_CAPTURE = 1;
    private static final int REQUEST_IMAGE = 100;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        Button btnCapture = root.findViewById(R.id.capture);
        Button btnGallery = root.findViewById(R.id.open_gallery);
        Button btnUpload = root.findViewById(R.id.upload_image);
        imgPreview = root.findViewById(R.id.picture);

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTakePictureIntent();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UploadImage().execute();

            }
        });
        return root;
    }

    /**
     * Sends a request to open the phone's gallery to choose an image
     */
    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,REQUEST_IMAGE);
    }

    /**
     * This method opens the camera, finds the file, and sends the intent
     */
    private void sendTakePictureIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //cameraIntent.putExtra( MediaStore.EXTRA_FINISH_ON_COMPLETION, true);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File pictureFile = null;
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        pictureFile = getPictureFile();
                    }else{
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }

                }else{
                    pictureFile = getPictureFile();
                }

            } catch (IOException ex) {
                Log.e("Photo: ", ex + "");
                return;
            }
            if (pictureFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.example.android.fileprovider",
                        pictureFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, REQUEST_PICTURE_CAPTURE);
            }
        }
    }

    /**
     * Gives the file a temp file name and designation in the phones external storage
     * @return
     * @throws IOException
     */
    private File getPictureFile() throws IOException {
        String timeStamp = new SimpleDateFormat("dd-M-yyyy hh:mm:ss").format(new Date());
        String pictureFile = data.personName + timeStamp;
        //File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(pictureFile,".jpg", storageDir);
        pictureFilePath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.v("Camera","Permission: "+permissions[0]+ "was "+grantResults[0]);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PICTURE_CAPTURE && resultCode == RESULT_OK) {
            File imgFile = new  File(pictureFilePath);
            image = Uri.fromFile(imgFile);
            if(imgFile.exists())            {
                Glide.with(this).load(image).into(imgPreview);
                // imgPreview.setImageURI(Uri.fromFile(imgFile));
            }
        }
        else if(requestCode == REQUEST_IMAGE && resultCode == RESULT_OK){
            image = data.getData();
            Glide.with(this).load(image).into(imgPreview);
        }
    }


    /**
     * This uploads the image to database
     */
    class UploadImage extends AsyncTask<String, String, String> {
        private ProgressDialog pDialog;

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Uploading Image..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Uploading Image
         * */
        protected String doInBackground(String... args) {
            String link = "http://10.72.64.24/android/uploadImage.php";

            String timeStamp = new SimpleDateFormat("dd-M-yyyy hh:mm:ss").format(new Date());
            String pictureName = data.personName + timeStamp;
            pictureName = pictureName.replace(" ", "");

            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), image);
            } catch (IOException e) {
                Log.e("Bitmap",e+"");
            }
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            assert bitmap != null;
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream); //compress to which format you want.
            byte [] byte_arr = stream.toByteArray();
            String image_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);


            // Building Parameters
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("userID", data.personId+""));
            params.add(new BasicNameValuePair("imageName", pictureName));
            params.add(new BasicNameValuePair("image", image_str));

            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(link);
            try {
                post.setEntity(new UrlEncodedFormEntity(params));
                HttpResponse response = client.execute(post);
                BufferedReader rd = new BufferedReader(new InputStreamReader(
                        response.getEntity().getContent()));
                String line = "";
                while ((line = rd.readLine()) != null) {
                    System.out.println(line);
                }
                return new String("Data Sent Successfully");
            } catch (Exception e){
                Log.e("Connection",e+"");
                return new String("Connection Unsuccessful");
            }

        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String response) {
            // dismiss the dialog once done
            pDialog.dismiss();
            Toast.makeText(getActivity(),res,Toast.LENGTH_SHORT).show();
        }

    }
}
