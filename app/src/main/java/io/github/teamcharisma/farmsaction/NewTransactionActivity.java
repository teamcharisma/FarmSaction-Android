package io.github.teamcharisma.farmsaction;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NewTransactionActivity extends AppCompatActivity {

    TextView  mManualInputButton, mBuyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_transaction);
        mManualInputButton = findViewById(R.id.newManualInputButton);
        mBuyButton = findViewById(R.id.newSellInputButton);

        mManualInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewTransactionActivity.this, AddRevenueItemActivity.class));
            }
        });

        mBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddExpenseItemActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //goto the category intent based on
        String qrdata = data.getStringExtra("data");
    }
}
