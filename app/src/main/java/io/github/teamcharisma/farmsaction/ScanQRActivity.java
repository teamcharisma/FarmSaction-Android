package io.github.teamcharisma.farmsaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ErrorCallback;
import com.google.zxing.Result;

import androidx.annotation.NonNull;

public class ScanQRActivity extends AppCompatActivity {

    CodeScanner codeScanner;
    TextView resultText;
    Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

        resultText = findViewById(R.id.resultText);
        resetButton = findViewById(R.id.resetButton);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultText.setText("Scanning QR");
                        codeScanner.startPreview();
                    }
                });
            }
        });

        CodeScannerView scannerView = findViewById(R.id.scannerView);
        codeScanner = new CodeScanner(this, scannerView);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultText.setText(result.getText());
                    }
                });
            }
        });

        codeScanner.setErrorCallback(new ErrorCallback() {
            @Override
            public void onError(@NonNull final Exception error) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) !=
                                PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(getApplicationContext(), "Provide access to camera", Toast.LENGTH_SHORT);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT);
                        }

                        resultText.setText("error: " + error.toString());
                    }
                });
            }
        });
        scannerView.setAutoFocusButtonVisible(false);
        codeScanner.startPreview();
    }

    @Override
    protected void onResume() {
        resultText.setText("Scanning QR");
        codeScanner.startPreview();
        super.onResume();

    }

    @Override
    protected void onRestart() {
        resultText.setText("Scanning QR");
        codeScanner.startPreview();
        super.onRestart();
    }


    @Override
    protected void onStop() {
        codeScanner.releaseResources();
        super.onStop();
    }
}
