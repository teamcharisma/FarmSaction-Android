package io.github.teamcharisma.farmsaction;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NewTransactionActivity extends AppCompatActivity {

    TextView mQRScanButton, mManualInputButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_transaction);
        mQRScanButton = findViewById(R.id.newScanQRButton);
        mManualInputButton = findViewById(R.id.newManualInputButton);

        mQRScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewTransactionActivity.this, ScanQRActivity.class);
                intent.putExtra("requestCode", 0);
                startActivityForResult(intent, 0);
                //setResult(RESULT_OK, intent)
            }
        });

        mManualInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewTransactionActivity.this, AddProductActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //goto the category intent based on
        String qrdata = data.getStringExtra("data");
    }
}
