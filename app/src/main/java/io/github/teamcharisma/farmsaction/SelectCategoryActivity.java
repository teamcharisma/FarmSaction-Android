package io.github.teamcharisma.farmsaction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class SelectCategoryActivity extends AppCompatActivity {
    Intent oldintent;
    Bundle extras;
    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);
         oldintent = getIntent();

        extras = oldintent.getExtras();


        LinearLayout button_electricity = findViewById(R.id.icon_electricity);
        LinearLayout button_fertilizer = findViewById(R.id.icon_fertilizer);
        LinearLayout button_fuel = findViewById(R.id.icon_fuel);
        LinearLayout button_interest = findViewById(R.id.icon_interest);
        LinearLayout button_labour = findViewById(R.id.icon_labour);
        LinearLayout button_livestock = findViewById(R.id.icon_livestock);
        LinearLayout button_machinery = findViewById(R.id.icon_machinery);
        LinearLayout button_pesticides = findViewById(R.id.icon_pesticides);
        LinearLayout button_rent = findViewById(R.id.icon_rent);
        LinearLayout button_customwork = findViewById(R.id.icon_customwork);
        LinearLayout button_crop = findViewById(R.id.icon_crop);


        View.OnClickListener onclick = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("shyam","touched");
                switch (v.getId()){
                    case R.id.icon_electricity:
                        category = "electricity";
                        // (categories.get(0))
                           // button_electricity.setBackground(getResources().getDrawable(R.drawable.card_background));
                      //  else
                           // button_electricity.setBackground(getResources().getDrawable(R.drawable.card_background_dark));
                        break;

                    case R.id.icon_fertilizer:
                        category = "fertilizers";
                        // (categories.get(0))
                        // button_electricity.setBackground(getResources().getDrawable(R.drawable.card_background));
                        //  else
                        // button_electricity.setBackground(getResources().getDrawable(R.drawable.card_background_dark));
                        break;

                    case R.id.icon_fuel:
                        category = "fuel";
                        // (categories.get(0))
                        // button_electricity.setBackground(getResources().getDrawable(R.drawable.card_background));
                        //  else
                        // button_electricity.setBackground(getResources().getDrawable(R.drawable.card_background_dark));
                        break;

                    case R.id.icon_interest:
                        category = "interest";
                        // (categories.get(0))
                        // button_electricity.setBackground(getResources().getDrawable(R.drawable.card_background));
                        //  else
                        // button_electricity.setBackground(getResources().getDrawable(R.drawable.card_background_dark));\
                        break;

                    case R.id.icon_labour:
                        category = "labour";
                        // (categories.get(0))
                        // button_electricity.setBackground(getResources().getDrawable(R.drawable.card_background));
                        //  else
                        // button_electricity.setBackground(getResources().getDrawable(R.drawable.card_background_dark));
                        break;

                    case R.id.icon_livestock:
                        category = "livestock";
                        // (categories.get(0))
                        // button_electricity.setBackground(getResources().getDrawable(R.drawable.card_background));
                        //  else
                        // button_electricity.setBackground(getResources().getDrawable(R.drawable.card_background_dark));
                        break;

                    case R.id.icon_machinery:
                        category = "machinery";
                        // (categories.get(0))
                        // button_electricity.setBackground(getResources().getDrawable(R.drawable.card_background));
                        //  else
                        // button_electricity.setBackground(getResources().getDrawable(R.drawable.card_background_dark));
                        break;

                    case R.id.icon_pesticides:
                        category = "pesticides";
                        // (categories.get(0))
                        // button_electricity.setBackground(getResources().getDrawable(R.drawable.card_background));
                        //  else
                        // button_electricity.setBackground(getResources().getDrawable(R.drawable.card_background_dark));
                        break;

                    case R.id.icon_rent:
                        category = "rent";
                        // (categories.get(0))
                        // button_electricity.setBackground(getResources().getDrawable(R.drawable.card_background));
                        //  else
                        // button_electricity.setBackground(getResources().getDrawable(R.drawable.card_background_dark));
                        break;

                    case R.id.icon_customwork:
                        category = "customwork";
                        // (categories.get(0))
                        // button_electricity.setBackground(getResources().getDrawable(R.drawable.card_background));
                        //  else
                        // button_electricity.setBackground(getResources().getDrawable(R.drawable.card_background_dark));
                        break;

                    case R.id.icon_crop:
                        category = "crop";
                        // (categories.get(0))
                        // button_electricity.setBackground(getResources().getDrawable(R.drawable.card_background));
                        //  else
                        // button_electricity.setBackground(getResources().getDrawable(R.drawable.card_background_dark));
                        break;
                }

            }
        };

        button_electricity.setOnClickListener(onclick);
        button_fertilizer.setOnClickListener(onclick);
        button_fuel.setOnClickListener(onclick);
        button_interest.setOnClickListener(onclick);
        button_labour.setOnClickListener(onclick);
        button_livestock.setOnClickListener(onclick);
        button_machinery.setOnClickListener(onclick);
        button_pesticides.setOnClickListener(onclick);
        button_rent.setOnClickListener(onclick);
        button_customwork.setOnClickListener(onclick);
        button_crop.setOnClickListener(onclick);

    }

    public void onclick (View w) {
        Intent intent = new Intent(getBaseContext(), AddCropActivity.class);
        intent.putExtra("itemnames", extras.getStringArrayList("itemnames"));
        intent.putExtra("itemprices", extras.getStringArrayList("itemprices"));
        ArrayList<String> categories;
        if (oldintent.hasExtra("categories"))
            categories = extras.getStringArrayList("categories");
        else
            categories = new ArrayList<>();
        categories.add(category);
        intent.putExtra("categories", categories);
        startActivity(intent);
    }

}
