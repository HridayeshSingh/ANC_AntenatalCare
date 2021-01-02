package com.gmail.anc.antenatalcare.anc_antenatalcare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ImageSamplesActivity extends AppCompatActivity {

    private ImageView imageSample1, imageSample2, imageSample3, imageSample4;

    private boolean isPermissionGranted = false;
    private String mode, selectedPatient;
    private String currentUserID;
    private FirebaseAuth firebaseAuth;
    private StorageReference rootRefImage1, rootRefImage2, rootRefImage3, rootRefImage4;

    private static final int galleryPick1 = 1;
    private static final int galleryPick2 = 2;
    private static final int galleryPick3 = 3;
    private static final int galleryPick4 = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_samples);

        setTitle("Image Samples");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        mode = getIntent().getStringExtra("mode");
        selectedPatient = getIntent().getStringExtra("selectedPatient");

        if(mode.equals("patient"))
            currentUserID = firebaseAuth.getCurrentUser().getUid();
        else
            currentUserID = selectedPatient;

        rootRefImage1 = FirebaseStorage.getInstance().getReference().child("Image Samples").child("Image Sample 1");
        rootRefImage2 = FirebaseStorage.getInstance().getReference().child("Image Samples").child("Image Sample 2");
        rootRefImage3 = FirebaseStorage.getInstance().getReference().child("Image Samples").child("Image Sample 3");
        rootRefImage4 = FirebaseStorage.getInstance().getReference().child("Image Samples").child("Image Sample 4");

        imageSample1 = findViewById(R.id.imageSample1);
        imageSample2 = findViewById(R.id.imageSample2);
        imageSample3 = findViewById(R.id.imageSample3);
        imageSample4 = findViewById(R.id.imageSample4);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            isPermissionGranted = false;
            requestPermission();
        } else {
            isPermissionGranted = true;
        }

        StorageReference filePath = rootRefImage1.child(currentUserID + ".jpg");
        filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imageSample1);
            }
        });

        StorageReference filePath2 = rootRefImage2.child(currentUserID + ".jpg");
        filePath2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imageSample2);
            }
        });

        StorageReference filePath3 = rootRefImage3.child(currentUserID + ".jpg");
        filePath3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imageSample3);
            }
        });

        StorageReference filePath4 = rootRefImage4.child(currentUserID + ".jpg");
        filePath4.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imageSample4);
            }
        });

        imageSample1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(ImageSamplesActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this image? \n" +
                                "क्या आप इस छवि को हटाना चाहते हैं?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                imageSample1.setImageDrawable(getDrawable(R.drawable.logo));
                                rootRefImage1.child(currentUserID + ".jpg").delete();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });

        imageSample2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(ImageSamplesActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this image? \n" +
                                "क्या आप इस छवि को हटाना चाहते हैं?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                imageSample2.setImageDrawable(getDrawable(R.drawable.logo));
                                rootRefImage2.child(currentUserID + ".jpg").delete();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });

        imageSample3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(ImageSamplesActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this image? \n" +
                                "क्या आप इस छवि को हटाना चाहते हैं?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                imageSample3.setImageDrawable(getDrawable(R.drawable.logo));
                                rootRefImage3.child(currentUserID + ".jpg").delete();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });

        imageSample4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(ImageSamplesActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this image? \n" +
                                "क्या आप इस छवि को हटाना चाहते हैं?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                imageSample4.setImageDrawable(getDrawable(R.drawable.logo));
                                rootRefImage4.child(currentUserID + ".jpg").delete();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
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
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.image_sample_1:
                if(!isPermissionGranted) {
                    requestPermission();
                } else {
                    Toast.makeText(this, "Add image", Toast.LENGTH_SHORT).show();

                    Intent galleryIntent = new Intent();
                    galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent, galleryPick1);
                }
                return true;
            case R.id.image_sample_2:
                if(!isPermissionGranted) {
                    requestPermission();
                } else {
                    Toast.makeText(this, "Add image", Toast.LENGTH_SHORT).show();

                    Intent galleryIntent2 = new Intent();
                    galleryIntent2.setAction(Intent.ACTION_GET_CONTENT);
                    galleryIntent2.setType("image/*");
                    startActivityForResult(galleryIntent2, galleryPick2);
                }
                return true;
            case R.id.image_sample_3:
                if(!isPermissionGranted) {
                    requestPermission();
                } else {
                    Toast.makeText(this, "Add image", Toast.LENGTH_SHORT).show();

                    Intent galleryIntent3 = new Intent();
                    galleryIntent3.setAction(Intent.ACTION_GET_CONTENT);
                    galleryIntent3.setType("image/*");
                    startActivityForResult(galleryIntent3, galleryPick3);
                }
                return true;
            case R.id.image_sample_4:
                if(!isPermissionGranted) {
                    requestPermission();
                } else {
                    Toast.makeText(this, "Add image", Toast.LENGTH_SHORT).show();

                    Intent galleryIntent4 = new Intent();
                    galleryIntent4.setAction(Intent.ACTION_GET_CONTENT);
                    galleryIntent4.setType("image/*");
                    startActivityForResult(galleryIntent4, galleryPick4);
                }
                return true;
            default:
                return false;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == galleryPick1 && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();

            final StorageReference fileRef1 = rootRefImage1.child(currentUserID + ".jpg");
            fileRef1.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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

        else if (requestCode == galleryPick2 && resultCode == RESULT_OK && data != null) {
            Uri imageUri2 = data.getData();

            final StorageReference fileRef2 = rootRefImage2.child(currentUserID + ".jpg");
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

        else if (requestCode == galleryPick3 && resultCode == RESULT_OK && data != null) {
            Uri imageUri3 = data.getData();

            final StorageReference fileRef3 = rootRefImage3.child(currentUserID + ".jpg");
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

        else if (requestCode == galleryPick4 && resultCode == RESULT_OK && data != null) {
            Uri imageUri4 = data.getData();

            final StorageReference fileRef4 = rootRefImage4.child(currentUserID + ".jpg");
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

    private void requestPermission() {
        ActivityCompat.requestPermissions(ImageSamplesActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 5);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 5) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    isPermissionGranted = true;
                }
            } else {
                isPermissionGranted = false;
            }
        }
    }

}
