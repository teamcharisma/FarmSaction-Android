package io.github.teamcharisma.farmsaction;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import net.glxn.qrgen.android.QRCode;

public class ShowQRActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_qr);


        ImageView qrCode = findViewById(R.id.qr);
        Bitmap myBitmap = QRCode.from("www.example.org").bitmap();
        qrCode.setImageBitmap(myBitmap);
    }
}
