package com.example.loginscreen;
//import android.Manifest;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.provider.MediaStore;
//
//import androidx.activity.result.ActivityResultCallback;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import androidx.activity.result.ActivityResult;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.mlkit.common.MlKitException;
//import com.google.mlkit.vision.common.InputImage;
//import com.google.mlkit.vision.text.Text;
//import com.google.mlkit.vision.text.TextRecognition;
//import com.google.mlkit.vision.text.TextRecognizer;
//import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
//import java.util.List;
//
//public class SmartScanner extends AppCompatActivity {
//
//    private Button snapBtn;
//    private Button detectBtn;
//    private ImageView imageView;
//    private TextView txtView;
//    private Bitmap imageBitmap;
//    private ActivityResultLauncher<Intent> cameraLauncher;
//    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_smart_scanner);
//        snapBtn = findViewById(R.id.snapBtn);
//        detectBtn = findViewById(R.id.detectBtn);
//        imageView = findViewById(R.id.imageView);
//        txtView = findViewById(R.id.txtView);
//        snapBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dispatchTakePictureIntent();
//            }
//        });
//        detectBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                detectTxt();
//            }
//        });
////    }
//        // Initialize the cameraLauncher
//        cameraLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//                        if (result.getResultCode() == RESULT_OK) {
//                            Bundle extras = result.getData().getExtras();
//                            if (extras != null) {
//                                imageBitmap = (Bitmap) extras.get("data");
//                                imageView.setImageBitmap(imageBitmap);
//                            }
//                        }
//                    }
//                });
//        // Check for camera permission
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED) {
//            // Permission is not granted
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
//                    CAMERA_PERMISSION_REQUEST_CODE);
//        }
//    }
//
//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            cameraLauncher.launch(takePictureIntent);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted
//                // You can proceed with using the camera
//            } else {
//                Toast.makeText(this, "Camera permission is required to use the camera feature.", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        }
//    }
//
//    private void detectTxt() {
//        if (imageBitmap != null) {
//            InputImage image = InputImage.fromBitmap(imageBitmap, 0);
//            TextRecognizerOptions options = new TextRecognizerOptions.Builder().build();
//            TextRecognizer textRecognizer = TextRecognition.getClient(options);
//            textRecognizer.process(image).addOnSuccessListener(new OnSuccessListener<Text>() {
//                        @Override
//                        public void onSuccess(Text visionText) {
//                            processTxt(visionText);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            if (e instanceof MlKitException) {
//                                MlKitException mlKitException = (MlKitException) e;
//                                // Handle ML Kit exceptions
//                            } else {
//                                // Handle other exceptions
//                            }
//                        }
//                    });
//        } else {
//            Toast.makeText(this, "Please take a picture first", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void processTxt(Text text) {
//        List<Text.TextBlock> blocks = text.getTextBlocks();
//        if (blocks.size() == 0) {
//            Toast.makeText(SmartScanner.this, "No Text :(", Toast.LENGTH_LONG).show();
//            return;
//        }
//        StringBuilder stringBuilder = new StringBuilder();
//        for (Text.TextBlock block : blocks) {
//            stringBuilder.append(block.getText()).append("\n");
//        }
//        String allText = stringBuilder.toString();
//        txtView.setTextSize(24);
//        txtView.setText(allText);
//    }
//}


//Modified
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;

import androidx.activity.result.ActivityResultCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.MlKitException;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import java.util.List;

public class SmartScanner extends AppCompatActivity {

    Button snapBtn, detectBtn, registerBtn;
    private ImageView imageView;
    private TextView txtView;
    private Bitmap imageBitmap;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    public static final String EXTRA_DETECTED_TEXT = "detected_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_scanner);
        snapBtn = findViewById(R.id.snapBtn);
        detectBtn = findViewById(R.id.detectBtn);
        registerBtn = findViewById(R.id.registerBtn);
        imageView = findViewById(R.id.imageView);
        txtView = findViewById(R.id.txtView);
        snapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
        detectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detectTxt();
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageBitmap != null) {
                    Intent intent = new Intent(SmartScanner.this, SmartRegister.class);
                    intent.putExtra(EXTRA_DETECTED_TEXT, txtView.getText().toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(SmartScanner.this, "Please take a picture first", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Initialize the cameraLauncher
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Bundle extras = result.getData().getExtras();
                            if (extras != null) {
                                imageBitmap = (Bitmap) extras.get("data");
                                imageView.setImageBitmap(imageBitmap);
                            }
                        }
                    }
                });
        // Check for camera permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            cameraLauncher.launch(takePictureIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission is granted.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Camera permission is required to use the camera feature.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void detectTxt() {
        if (imageBitmap != null) {
            InputImage image = InputImage.fromBitmap(imageBitmap, 0);
            TextRecognizerOptions options = new TextRecognizerOptions.Builder().build();
            TextRecognizer textRecognizer = TextRecognition.getClient(options);
            textRecognizer.process(image).addOnSuccessListener(new OnSuccessListener<Text>() {
                        @Override
                        public void onSuccess(Text visionText) {
                            processTxt(visionText);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (e instanceof MlKitException) {
                                MlKitException mlKitException = (MlKitException) e;
                                // Handle ML Kit exceptions
                            } else {
                                // Handle other exceptions
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "Please take a picture first", Toast.LENGTH_SHORT).show();
        }
    }

    private void processTxt(Text text) {
        List<Text.TextBlock> blocks = text.getTextBlocks();
        if (blocks.size() == 0) {
            Toast.makeText(SmartScanner.this, "No Text :(", Toast.LENGTH_LONG).show();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Text.TextBlock block : blocks) {
            stringBuilder.append(block.getText()).append("\n");
        }
        String allText = stringBuilder.toString();
        txtView.setTextSize(24);
        txtView.setText(allText);
    }
}




//IMAGE CROPPER
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.provider.MediaStore;
//import android.Manifest;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import android.os.Bundle;
//import android.util.SparseArray;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.vision.Frame;
//import com.google.android.gms.vision.text.TextRecognizer;
//import com.google.mlkit.vision.text.Text;
//
//import java.io.IOException;
//
//public class SmartScanner extends AppCompatActivity {
//
//    Button button_capture, button_copy;
//    TextView textview_data;
//    private static final int REQUEST_CAMERA_CODE = 100;
//    Bitmap bitmap;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_smart_scanner);
//        button_capture = findViewById(R.id.button_capture);
//        textview_data = findViewById(R.id.text_data);
//
//        if (ContextCompat.checkSelfPermission(SmartScanner.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(SmartScanner.this, new String[]{
//                    Manifest.permission.CAMERA
//            }, REQUEST_CAMERA_CODE);
//        }
//        button_capture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(SmartScanner.this);
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                Uri resultUri = result.getUri();
//                try {
//                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//    private void getTextFromImage(Bitmap bitmap){
//        TextRecognizer recognizer = new TextRecognizer.Builder(this).build();
//        if(!recognizer.isOperational()){
//            Toast.makeText(SmartScanner.this, "Error Occured!", Toast.LENGTH_SHORT).show();
//        } else{
//            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
//            SparseArray<Text.TextBlock> textBlockSparseArray = recognizer.detect(frame);
//            StringBuilder stringBuilder = new StringBuilder();
//            for (int i=0; i<textBlockSparseArray.size();i++) {
//                Text.TextBlock textBlock = textBlockSparseArray.valueAt(i);
//                stringBuilder.append(textBlock.getValue());
//                stringBuilder.append("\n");
//            }
//            textview_data.setText(stringBuilder.toString());
//        }
//    }
//}

