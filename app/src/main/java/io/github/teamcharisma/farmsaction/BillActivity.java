package io.github.teamcharisma.farmsaction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class BillActivity extends AppCompatActivity {

    Bundle extras;
    ArrayList<String> itemName, itemPrices, categories, crops;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        Intent oldIntent = getIntent();
        extras = oldIntent.getExtras();
        LinearLayout ll = findViewById(R.id.bill);
        if (extras != null) {
            itemName = extras.getStringArrayList("itemnames");
             itemPrices = extras.getStringArrayList("itemprices");
            categories = extras.getStringArrayList("categories");
            crops = extras.getStringArrayList("crops");

            for (int i = 0; i < itemName.size(); i++){
                Log.d("shyams", String.valueOf(i));
                View element = LayoutInflater.from(this).inflate(R.layout.layout_bill_item, ll, false);
                TextView itemname, itemprice, cropss, categoriest;
                itemname = element.findViewById(R.id.itemname);
                itemprice = element.findViewById(R.id.itemprice);
                categoriest = element.findViewById(R.id.categories);

                categoriest.setText(String.valueOf(categories.get(i)));
                itemname.setText(itemName.get(i));
                itemprice.setText("Rs " + itemPrices.get(i));

                ll.addView(element);
            }
        }
    }

    public void onAddClick(View w) {
        Intent intent =  new Intent(this, AddRevenueItemActivity.class);
        intent.putExtra("itemnames", itemName);
        intent.putExtra("itemprices", itemPrices);
        intent.putExtra("categories", categories);
        intent.putExtra("crops", crops);
        startActivity(intent);
    }

    public void onQrClick(View w) {
        Intent intent =  new Intent(this, AddRevenueItemActivity.class);
        intent.putExtra("itemnames", itemName);
        intent.putExtra("itemprices", itemPrices);
        intent.putExtra("categories", categories);
        intent.putExtra("crops", crops);
        startActivity(intent);
    }
}
