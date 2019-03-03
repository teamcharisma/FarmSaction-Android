package io.github.teamcharisma.farmsaction;

import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import static java.util.Arrays.asList;

public class AddRevenueItemActivity extends AppCompatActivity {

    EditText mItemName, mCost;
    Button mAdd;
    ArrayList<String> mCategories;
    ArrayList<String> mItemNames;
    ArrayList<String> mItemPrices;
    ArrayList<String> mCrops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_add);
        mItemName = findViewById(R.id.itemName);
        mCost = findViewById(R.id.cost);

        mCategories = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.category)));
        Intent oldIntent = getIntent();
        Bundle extras = oldIntent.getExtras();
        if(extras != null)
        {
            mItemNames = extras.getStringArrayList("itemnames");
            mCategories = extras.getStringArrayList("categories");
            mItemPrices = extras.getStringArrayList("itemprices");
            mCrops = extras.getStringArrayList("crops");
        }
        else {
            mItemNames = new ArrayList<>();
            mItemPrices = new ArrayList<>();
            mCategories = new ArrayList<>();
            mCrops = new ArrayList<>();
        }
    }

    public void onClick(View v){
       Intent intent = new Intent(getApplicationContext(), SelectCategoryActivity.class);

       mItemNames.add(mItemName.getText().toString());
       mItemPrices.add(mCost.getText().toString());

       intent.putExtra("itemnames", mItemNames);
       intent.putExtra("itemprices", mItemPrices);
       intent.putExtra("categories", mCategories);
       Log.d("shyams", String.valueOf(mItemNames.size()));
       intent.putExtra("crops", mCrops);

       startActivity(intent);
    }
}
