package io.github.teamcharisma.farmsaction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class BillActivity extends AppCompatActivity {

    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        Intent oldIntent = getIntent();
        extras = oldIntent.getExtras();
        LinearLayout ll = findViewById(R.id.bill);
        if (extras != null) {
            ArrayList<String> itemName = extras.getStringArrayList("itemnames");
            float itemPrices[] = extras.getFloatArray("itemprices");
            boolean categories[] = extras.getBooleanArray("categories");
            boolean crops[] = extras.getBooleanArray("crops");

            for (int i = 0; i < itemName.size(); i++){
                View element = LayoutInflater.from(this).inflate(R.layout.layout_bill_item, ll, false);
                TextView itemname, itemprice, removeButton;
                itemname = element.findViewById(R.id.itemname);
                itemprice = element.findViewById(R.id.itemprice);
                removeButton = element.findViewById(R.id.removeButton);

                itemname.setText(itemName.get(i));
                itemprice.setText("Rs " + itemPrices[i]);

                ll.addView(element);
            }


        }
    }
}
