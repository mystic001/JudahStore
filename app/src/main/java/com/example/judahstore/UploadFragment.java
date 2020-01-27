package com.example.judahstore;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class UploadFragment extends Fragment {


    private static final int PICKER_REQUEST = 1;
    private MaterialButton choose;
    private ImageView image;
    private TextInputEditText name;
    private TextInputEditText desc;
    private TextInputEditText price;
    private TextInputEditText delivery;
    private ProgressBar bar;
    private MaterialButton upload;
    private MaterialButton check;

    private productModel model;



    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private Uri mImageUri;
    private StorageTask mUploads;



    public static Fragment newInstance(){

        return new UploadFragment();
    }


    public UploadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_upload, container, false);

        choose = view.findViewById(R.id.choose);
        image = view.findViewById(R.id.image_view);
        name = view.findViewById(R.id.name_text);
        desc = view.findViewById(R.id.description_edit_text);
        price = view.findViewById(R.id.price_edit_text);
        delivery = view.findViewById(R.id.delivery_fee_edit_text);
        upload = view.findViewById(R.id.upload);
        check = view.findViewById(R.id.check);
        bar = view.findViewById(R.id.progress_bar);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity =  (AppCompatActivity) getActivity();
        assert activity != null;
        activity.setSupportActionBar(toolbar);

        mStorageRef = FirebaseStorage.getInstance().getReference("goods");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("goods");

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFile();
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(CheckNetworkStatus.CheckNetwork(getActivity())) {
                    if (mUploads != null && mUploads.isInProgress()) {
                        Toast.makeText(getActivity(), "An upload is ongoing", Toast.LENGTH_LONG).show();
                    }
                    uploadFile();
                }else {

                    Toast.makeText(getActivity(),"Network is not available",Toast.LENGTH_LONG).show();
                }


            }
        });


        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUploads();
            }
        });

        return view;
    }

    private void showUploads() {
        if (CheckNetworkStatus.CheckNetwork(getActivity())) {
            Intent intent = new Intent(getActivity(), DisplayActivity.class);
            startActivity(intent);
        }else{

            Toast.makeText(getActivity(),"Network is not available",Toast.LENGTH_LONG).show();
        }
    }


    private String getFileExtension(Uri uri){
       // This was just a test
        ContextWrapper rapper = new ContextWrapper(getActivity());
        ContentResolver resolver = rapper.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(resolver.getType(uri));
    }



    private void uploadFile() {
        if (mImageUri != null){
            final StorageReference fileref = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));

            // This is where the image is added to the storage
            mUploads = fileref.putFile(mImageUri)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Handler hand = new Handler();
                                    hand.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            bar.setProgress(0);
                                        }
                                    },500);

                                    fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {

                                            Toast.makeText(getActivity(),"successfully uploaded",Toast.LENGTH_SHORT).show();
                                            model = new productModel(name.getText().toString().trim(), uri.toString(),desc.getText().toString(),
                                                    Integer.parseInt(price.getText().toString()),Integer.parseInt(delivery.getText().toString()));

                                            // The code below creates the random string that serves as
                                            // d id for the information we will be adding to the database
                                            String uploadId = mDatabaseRef.push().getKey();
                                            //The code below adds the name and the url of
                                            // the parameter in moses to the database
                                            mDatabaseRef.child(uploadId).setValue(model);
                                        }
                                    });
                                }
                            }

                    )
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.00 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            bar.setProgress( (int) progress);

                        }
                    });

        }else{

            Toast.makeText(getActivity(),"Nothing was collected",Toast.LENGTH_SHORT).show();
        }

    }

    private void openFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICKER_REQUEST);
    }

    // When you call StartActivityForResult() method
    // then you use onActivityResult to get the value of whatever you started
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICKER_REQUEST && resultCode == Activity.RESULT_OK && data != null  && data.getData() != null){
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).placeholder(R.drawable.shr_logo).into(image);

        }
    }
}

