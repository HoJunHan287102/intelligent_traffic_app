//package com.example.loginscreen;
//
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.SurfaceView;
//import android.view.View;
//import android.widget.ImageButton;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.result.ActivityResultCallback;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.NonNull;
//import androidx.annotation.OptIn;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.camera.core.AspectRatio;
//import androidx.camera.core.Camera;
//import androidx.camera.core.CameraSelector;
//import androidx.camera.core.ExperimentalGetImage;
//import androidx.camera.core.ImageAnalysis;
//import androidx.camera.core.ImageCapture;
//import androidx.camera.core.ImageCaptureException;
//import androidx.camera.core.ImageProxy;
//import androidx.camera.core.Preview;
//import androidx.camera.core.Preview.Builder;
//import androidx.camera.lifecycle.ProcessCameraProvider;
//import androidx.camera.view.PreviewView;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import com.google.common.util.concurrent.ListenableFuture;
//import com.google.mlkit.vision.common.InputImage;
//import com.google.mlkit.vision.text.TextRecognition;
//import com.google.mlkit.vision.text.TextRecognizer;
//import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
//
//import java.io.File;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class SmartScanner extends AppCompatActivity {
//    ImageButton capture, toggleFlash, flipCamera;
//    private PreviewView previewView;
//    int cameraFacing = CameraSelector.LENS_FACING_BACK;
//    private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
//        @Override
//        public void onActivityResult(Boolean result) {
//            if (result) {
//                startCamera(cameraFacing);
//            }
//        }
//    });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_smart_scanner);
//
//        previewView = findViewById(R.id.cameraPreview);
//        capture = findViewById(R.id.capture);
//        toggleFlash = findViewById(R.id.toggleFlash);
//        flipCamera = findViewById(R.id.flipCamera);
//
//        if (ContextCompat.checkSelfPermission(SmartScanner.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            activityResultLauncher.launch(Manifest.permission.CAMERA);
//        } else {
//            startCamera(cameraFacing);
//        }
//
//        flipCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (cameraFacing == CameraSelector.LENS_FACING_BACK) {
//                    cameraFacing = CameraSelector.LENS_FACING_FRONT;
//                } else {
//                    cameraFacing = CameraSelector.LENS_FACING_BACK;
//                }
//                startCamera(cameraFacing);
//            }
//        });
//    }
//
//    public void startCamera(int cameraFacing) {
//        int aspectRatio = aspectRatio(previewView.getWidth(), previewView.getHeight());
//        ListenableFuture<ProcessCameraProvider> listenableFuture = ProcessCameraProvider.getInstance(this);
//
//        listenableFuture.addListener(() -> {
//            try {
//                ProcessCameraProvider cameraProvider = (ProcessCameraProvider) listenableFuture.get();
//
//                Preview preview = new Preview.Builder().setTargetAspectRatio(aspectRatio).build();
//
//                ImageCapture imageCapture = new ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
//                        .setTargetRotation(getWindowManager().getDefaultDisplay().getRotation()).build();
//
//                CameraSelector cameraSelector = new CameraSelector.Builder()
//                        .requireLensFacing(cameraFacing).build();
//
//                cameraProvider.unbindAll();
//
//                Camera camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);
//
//                capture.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (ContextCompat.checkSelfPermission(SmartScanner.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                            activityResultLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//                        }
//                        takePicture(imageCapture);
//                    }
//                });
//
//                toggleFlash.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        setFlashIcon(camera);
//                    }
//                });
//
//                preview.setSurfaceProvider(previewView.getSurfaceProvider());
//            } catch (ExecutionException | InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, ContextCompat.getMainExecutor(this));
//    }
//
//    public void takePicture(ImageCapture imageCapture) {
//        final File file = new File(getExternalFilesDir(null), System.currentTimeMillis() + ".jpg");
//        ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(file).build();
//        imageCapture.takePicture(outputFileOptions, Executors.newCachedThreadPool(), new ImageCapture.OnImageSavedCallback() {
//            @Override
//            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(SmartScanner.this, "Image saved at: " + file.getPath(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//                startCamera(cameraFacing);
//            }
//
//            @Override
//            public void onError(@NonNull ImageCaptureException exception) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(SmartScanner.this, "Failed to save: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//                startCamera(cameraFacing);
//            }
//        });
//    }
//
//    private void setFlashIcon(Camera camera) {
//        if (camera.getCameraInfo().hasFlashUnit()) {
//            if (camera.getCameraInfo().getTorchState().getValue() == 0) {
//                camera.getCameraControl().enableTorch(true);
//                toggleFlash.setImageResource(R.drawable.baseline_flash_off_24);
//            } else {
//                camera.getCameraControl().enableTorch(false);
//                toggleFlash.setImageResource(R.drawable.baseline_flash_on_24);
//            }
//        } else {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(SmartScanner.this, "Flash is not available currently", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }
//
//    private int aspectRatio(int width, int height) {
//        double previewRatio = (double) Math.max(width, height) / Math.min(width, height);
//        if (Math.abs(previewRatio - 4.0 / 3.0) <= Math.abs(previewRatio - 16.0 / 9.0)) {
//            return AspectRatio.RATIO_4_3;
//        }
//        return AspectRatio.RATIO_16_9;
//    }



//    private ExecutorService cameraExecutor;
//    TextView tvPrediction;
//    PreviewView previewView = findViewById(R.id.previewView);
//    private static final String TAG = "ImageRec";
//    private static final int REQUEST_CODE_PERMISSIONS = 10;
//    private static final String[] REQUIRED_PERMISSIONS = {Manifest.permission.CAMERA};
//
//    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_smart_scanner);
//        Log.d(TAG, "Context obtained by findViewById: " + findViewById(R.id.previewView));
//        previewView = findViewById(R.id.previewView);
//        tvPrediction = findViewById(R.id.tvPrediction);
//        cameraExecutor = Executors.newSingleThreadExecutor();
//
//        // Request camera permissions
//        if (allPermissionsGranted()) {
//            startCamera();
//        } else {
//            ActivityCompat.requestPermissions(
//                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
//            );
//        }
//    }
//
//    private void startCamera() {
//        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
//
//        cameraProviderFuture.addListener(() -> {
//            // Used to bind the lifecycle of cameras to the lifecycle owner
//            ProcessCameraProvider cameraProvider = null;
//            try {
//                cameraProvider = cameraProviderFuture.get();
//            } catch (Exception e) {
//                Log.e(TAG, "Error getting camera provider", e);
//            }
//
//            if (cameraProvider != null) {
//                // Preview
//                Preview preview = new Builder().build();
//
//                preview.setSurfaceProvider(previewView.getSurfaceProvider());
//
//                ImageAnalysis imageAnalysis =
//                        new ImageAnalysis.Builder()
//                                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//                                .build();
//
//                imageAnalysis.setAnalyzer(cameraExecutor, new ObjectDetectorImageAnalyzer());
//
//                // Select back camera as a default
//                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
//
//                try {
//                    // Unbind use cases before rebinding
//                    cameraProvider.unbindAll();
//
//                    // Bind use cases to camera
//                    cameraProvider.bindToLifecycle(
//                            SmartScanner.this, cameraSelector, preview, imageAnalysis
//                    );
//                } catch (Exception exc) {
//                    Log.e(TAG, "Use case binding failed", exc);
//                }
//            }
//        }, ContextCompat.getMainExecutor(this));
//    }
//
//    private final TextRecognizer mTextRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
//
//    class ObjectDetectorImageAnalyzer implements ImageAnalysis.Analyzer {
//        @Override
//        public void analyze(@NonNull ImageProxy imageProxy) {
//            @OptIn(markerClass = ExperimentalGetImage.class)
//            ImageProxy mediaImage = imageProxy;
//            if (mediaImage != null) {
//                InputImage image =
//                        InputImage.fromMediaImage(mediaImage.getImage(), imageProxy.getImageInfo().getRotationDegrees());
//
//                mTextRecognizer.process(image)
//                        .addOnCompleteListener(task -> {
//                            if (task.isSuccessful()) {
//                                tvPrediction.setText(task.getResult().getText());
//                            }
//                            // TO AVOID: com.google.mlkit.common.MlKitException: Internal error has occurred when executing ML Kit tasks
//                            imageProxy.close();
//                        });
//            }
//        }
//    }
//
//    private boolean allPermissionsGranted() {
//        for (String permission : REQUIRED_PERMISSIONS) {
//            if (ContextCompat.checkSelfPermission(this, permission)
//                    != PackageManager.PERMISSION_GRANTED) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        cameraExecutor.shutdown();
//    }
//
//    @Override
//    public void onRequestPermissionsResult(
//            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_CODE_PERMISSIONS) {
//            if (allPermissionsGranted()) {
//                startCamera();
//            } else {
//                Toast.makeText(
//                        this,
//                        "Permissions not granted by the user.",
//                        Toast.LENGTH_SHORT
//                ).show();
//                finish();
//            }
//        }
//    }
//}

//import android.Manifest;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.media.ThumbnailUtils;
//import android.net.Uri;
//import android.provider.MediaStore;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.loginscreen.ml.Model;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.ml.vision.FirebaseVision;
//import com.google.firebase.ml.vision.common.FirebaseVisionImage;
//import com.google.firebase.ml.vision.text.FirebaseVisionText;
//
//import org.tensorflow.lite.DataType;
//import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;
//
//import java.io.IOException;
//import java.nio.ByteBuffer;
//import java.nio.ByteOrder;
//import java.util.List;

//public class SmartScanner extends AppCompatActivity {

//    Button button_capture, button_copy;
//    TextView textview_data;
//    private Bitmap imageBitmap;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_smart_scanner);
//        button_capture = findViewById(R.id.button_capture);
//        button_copy = findViewById(R.id.detectBtn);
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
////                detectTxt();
//            }
//        });
//    }
//
//    static final int REQUEST_IMAGE_CAPTURE = 1;
//
//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            imageBitmap = (Bitmap) extras.get("data");
//            imageView.setImageBitmap(imageBitmap);
//        }
//    }
//    }


//use this
package com.example.loginscreen;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginscreen.ml.Model;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.mlkit.common.MlKitException;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

public class SmartScanner extends AppCompatActivity {

    private Button snapBtn;
    private Button detectBtn;
    private ImageView imageView;
    private TextView txtView;
    private Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_scanner);
        snapBtn = findViewById(R.id.snapBtn);
        detectBtn = findViewById(R.id.detectBtn);
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
//                detectTxt();
            }
        });
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }

//    private void detectTxt() {
//        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(imageBitmap);
//        FirebaseVisionTextDetector detector = FirebaseVision.getInstance().getVisionTextDetector();
//        detector.detectInImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
//            @Override
//            public void onSuccess(FirebaseVisionText firebaseVisionText) {
//                processTxt(firebaseVisionText);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
//    }
//
//    private void processTxt(FirebaseVisionText text) {
//        List<FirebaseVisionText.Block> blocks = text.getBlocks();
//        if (blocks.size() == 0) {
//            Toast.makeText(SmartScanner.this, "No Text :(", Toast.LENGTH_LONG).show();
//            return;
//        }
//        for (FirebaseVisionText.Block block : text.getBlocks()) {
//            String txt = block.getText();
//            txtView.setTextSize(24);
//            txtView.setText(txt);
//        }
//    }
//private void detectTxt() {
//    if (imageBitmap != null) {
//        InputImage image = InputImage.fromBitmap(imageBitmap, 0);
//        TextRecognizerOptions options =
//                new TextRecognizerOptions.Builder()
//                        // Set options as needed
//                        .build();
//        TextRecognizer textRecognizer = TextRecognition.getClient(options);
//        textRecognizer.process(image)
//                .addOnSuccessListener(new OnSuccessListener<Text>() {
//                    @Override
//                    public void onSuccess(Text visionText) {
//                        processTxt(visionText);
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        if (e instanceof MlKitException) {
//                            MlKitException mlKitException = (MlKitException) e;
//                            // Handle ML Kit exceptions
//                        } else {
//                            // Handle other exceptions
//                        }
//                    }
//                });
//    } else {
//        Toast.makeText(this, "Please take a picture first", Toast.LENGTH_SHORT).show();
//    }
//}
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
}

//import androidx.annotation.Nullable;
//        import androidx.appcompat.app.AppCompatActivity;
//
//        import android.Manifest;
//        import android.content.Intent;
//        import android.content.pm.PackageManager;
//        import android.graphics.Bitmap;
//        import android.media.ThumbnailUtils;
//        import android.net.Uri;
//        import android.os.Bundle;
//        import android.provider.MediaStore;
//        import android.util.Log;
//        import android.view.View;
//        import android.widget.Button;
//        import android.widget.ImageView;
//        import android.widget.TextView;
//
//        import com.example.loginscreen.ml.Model;
//
//        import org.tensorflow.lite.DataType;
//        import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;
//
//        import java.io.IOException;
//        import java.nio.ByteBuffer;
//        import java.nio.ByteOrder;
//
//public class SmartScanner extends AppCompatActivity {
//
//    Button camera, gallery;
//    ImageView imageView;
//    TextView result;
//    int imageSize = 32;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_smart_scanner);
//
//        camera = findViewById(R.id.button);
//        gallery = findViewById(R.id.button2);
//
//        result = findViewById(R.id.result);
//        imageView = findViewById(R.id.imageView);
//
//        camera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(cameraIntent, 3);
//                } else {
//                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
//                }
//            }
//        });
//        gallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(cameraIntent, 1);
//            }
//        });
//    }
//
//    public void classifyImage(Bitmap image){
//        try {
//            Model model = Model.newInstance(SmartScanner.this);
//
//            // Creates inputs for reference.
//            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 32, 32, 3}, DataType.FLOAT32);
//            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
//            byteBuffer.order(ByteOrder.nativeOrder());
//
//            int[] intValues = new int[imageSize * imageSize];
//            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
//            int pixel = 0;
//            //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
//            for(int i = 0; i < imageSize; i ++){
//                for(int j = 0; j < imageSize; j++){
//                    int val = intValues[pixel++]; // RGB
//                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 1));
//                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 1));
//                    byteBuffer.putFloat((val & 0xFF) * (1.f / 1));
//                }
//            }
//
//            inputFeature0.loadBuffer(byteBuffer);
//
//            // Runs model inference and gets result.
//            Model.Outputs outputs = model.process(inputFeature0);
//            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
//
//            float[] confidences = outputFeature0.getFloatArray();
//            // find the index of the class with the biggest confidence.
//            int maxPos = 0;
//            float maxConfidence = 0;
//            for (int i = 0; i < confidences.length; i++) {
//                if (confidences[i] > maxConfidence) {
//                    maxConfidence = confidences[i];
//                    maxPos = i;
//                }
//            }
//            String[] classes = {"Apple", "Banana", "Orange"};
//            result.setText(classes[maxPos]);
//
//            // Releases model resources if no longer used.
//            model.close();
//        } catch (IOException e) {
//            // TODO Handle the exception
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(resultCode == RESULT_OK){
//            if(requestCode == 3){
//                Bitmap image = (Bitmap) data.getExtras().get("data");
//                int dimension = Math.min(image.getWidth(), image.getHeight());
//                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
//                imageView.setImageBitmap(image);
//
//                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
//                classifyImage(image);
//            }else{
//                Uri dat = data.getData();
//                Bitmap image = null;
//                try {
//                    image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                imageView.setImageBitmap(image);
//
//                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
//                classifyImage(image);
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//}


