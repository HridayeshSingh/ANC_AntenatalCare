package com.example.anc_antenatalcare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ImageSamplesActivity extends AppCompatActivity {

    private ImageView imageSample1, imageSample2, imageSample3, imageSample4;

    private String CurrentUserID;
    private FirebaseAuth firebaseAuth;
    private StorageReference RootRefImage, RootRefImage2, RootRefImage3, RootRefImage4;

    private static final int GalleryPick = 1;
    private static final int GalleryPick2 = 2;
    private static final int GalleryPick3 = 3;
    private static final int GalleryPick4 = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_samples);

        setTitle("Image Samples");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        CurrentUserID = firebaseAuth.getCurrentUser().getUid();
        RootRefImage = FirebaseStorage.getInstance().getReference().child("Image Samples").child("Image Sample 1");
        RootRefImage2 = FirebaseStorage.getInstance().getReference().child("Image Samples").child("Image Sample 2");
        RootRefImage3 = FirebaseStorage.getInstance().getReference().child("Image Samples").child("Image Sample 3");
        RootRefImage4 = FirebaseStorage.getInstance().getReference().child("Image Samples").child("Image Sample 4");

        imageSample1 = findViewById(R.id.imageSample1);
        imageSample2 = findViewById(R.id.imageSample2);
        imageSample3 = findViewById(R.id.imageSample3);
        imageSample4 = findViewById(R.id.imageSample4);

        StorageReference filePath = RootRefImage.child(CurrentUserID + ".jpg");
        filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imageSample1);
            }
        });

        StorageReference filePath2 = RootRefImage2.child(CurrentUserID + ".jpg");
        filePath2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imageSample2);
            }
        });

        StorageReference filePath3 = RootRefImage3.child(CurrentUserID + ".jpg");
        filePath3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imageSample3);
            }
        });

        StorageReference filePath4 = RootRefImage4.child(CurrentUserID + ".jpg");
        filePath4.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imageSample4);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.image_samples_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case R.id.image_sample_1:
                Toast.makeText(this, "Add image",Toast.LENGTH_SHORT).show();

                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GalleryPick);

                return true;
            case R.id.image_sample_2:
                Toast.makeText(this, "Add image",Toast.LENGTH_SHORT).show();

                Intent galleryIntent2 = new Intent();
                galleryIntent2.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent2.setType("image/*");
                startActivityForResult(galleryIntent2, GalleryPick2);

                return true;
            case R.id.image_sample_3:
                Toast.makeText(this, "Add image",Toast.LENGTH_SHORT).show();

                Intent galleryIntent3 = new Intent();
                galleryIntent3.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent3.setType("image/*");
                startActivityForResult(galleryIntent3, GalleryPick3);

                return true;
            case R.id.image_sample_4:
                Toast.makeText(this, "Add image",Toast.LENGTH_SHORT).show();

                Intent galleryIntent4 = new Intent();
                galleryIntent4.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent4.setType("image/*");
                startActivityForResult(galleryIntent4, GalleryPick4);

                return true;
            default:
                return false;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();

            final StorageReference fileRef = RootRefImage.child(CurrentUserID + ".jpg");
            fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri).into(imageSample1);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ImageSamplesActivity.this, "Error in Image Uploading.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        else if (requestCode == GalleryPick2 && resultCode == RESULT_OK && data != null) {
            Uri imageUri2 = data.getData();

            final StorageReference fileRef2 = RootRefImage2.child(CurrentUserID + ".jpg");
            fileRef2.putFile(imageUri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri).into(imageSample2);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ImageSamplesActivity.this, "Error in Image Uploading.", Toast.LENGTH_SHORT).show();
                }
            });

        }

        else if (requestCode == GalleryPick3 && resultCode == RESULT_OK && data != null) {
            Uri imageUri3 = data.getData();

            final StorageReference fileRef3 = RootRefImage3.child(CurrentUserID + ".jpg");
            fileRef3.putFile(imageUri3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri).into(imageSample3);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ImageSamplesActivity.this, "Error in Image Uploading.", Toast.LENGTH_SHORT).show();
                }
            });

        }

        else if (requestCode == GalleryPick4 && resultCode == RESULT_OK && data != null) {
            Uri imageUri4 = data.getData();

            final StorageReference fileRef4 = RootRefImage4.child(CurrentUserID + ".jpg");
            fileRef4.putFile(imageUri4).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef4.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri).into(imageSample4);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ImageSamplesActivity.this, "Error in Image Uploading.", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

}
