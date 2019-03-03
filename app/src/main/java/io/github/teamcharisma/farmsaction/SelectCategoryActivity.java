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
    ArrayList<Boolean> categories;
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

        categories = new ArrayList<>();
        for (int i = 0; i < 11; i++)
            categories.add(false);



        View.OnClickListener onclick = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("shyam","touched");
                switch (v.getId()){
                    case R.id.icon_electricity:
                        categories.set(0, !categories.get(0));
                        if (categories.get(0))
                            button_electricity.setBackground(getResources().getDrawable(R.drawable.card_background));
                        else
                            button_electricity.setBackground(getResources().getDrawable(R.drawable.card_background_dark));
                        break;

                    case R.id.icon_fertilizer:
                        categories.set(1, !categories.get(1));
                        if (categories.get(1))
                            button_fertilizer.setBackground(getResources().getDrawable(R.drawable.card_background));
                        else
                            button_fertilizer.setBackground(getResources().getDrawable(R.drawable.card_background_dark));
                        break;

                    case R.id.icon_fuel:
                        categories.set(2, !categories.get(2));
                        if (categories.get(2))
                            button_fuel.setBackground(getResources().getDrawable(R.drawable.card_background));
                        else
                            button_fuel.setBackground(getResources().getDrawable(R.drawable.card_background_dark));
                        break;

                    case R.id.icon_interest:
                        categories.set(3, !categories.get(3));
                        if (categories.get(3))
                            button_interest.setBackground(getResources().getDrawable(R.drawable.card_background));
                        else
                            button_interest.setBackground(getResources().getDrawable(R.drawable.card_background_dark));
                        break;

                    case R.id.icon_labour:
                        categories.set(4, !categories.get(4));
                        if (categories.get(4))
                            button_labour.setBackground(getResources().getDrawable(R.drawable.card_background));
                        else
                            button_labour.setBackground(getResources().getDrawable(R.drawable.card_background_dark));
                        break;

                    case R.id.icon_livestock:
                        categories.set(5, !categories.get(5));
                        if (categories.get(5))
                            button_livestock.setBackground(getResources().getDrawable(R.drawable.card_background));
                        else
                            button_livestock.setBackground(getResources().getDrawable(R.drawable.card_background_dark));
                        break;

                    case R.id.icon_machinery:
                        categories.set(6, !categories.get(6));
                        if (categories.get(6))
                            button_machinery.setBackground(getResources().getDrawable(R.drawable.card_background));
                        else
                            button_machinery.setBackground(getResources().getDrawable(R.drawable.card_background_dark));
                        break;

                    case R.id.icon_pesticides:
                        categories.set(7, !categories.get(7));
                        if (categories.get(7))
                            button_pesticides.setBackground(getResources().getDrawable(R.drawable.card_background));
                        else
                            button_pesticides.setBackground(getResources().getDrawable(R.drawable.card_background_dark));
                        break;

                    case R.id.icon_rent:
                        categories.set(8, !categories.get(8));
                        if (categories.get(8))
                            button_rent.setBackground(getResources().getDrawable(R.drawable.card_background));
                        else
                            button_rent.setBackground(getResources().getDrawable(R.drawable.card_background_dark));
                        break;

                    case R.id.icon_customwork:
                        categories.set(9, !categories.get(9));
                        if (categories.get(9))
                            button_customwork.setBackground(getResources().getDrawable(R.drawable.card_background));
                        else
                            button_customwork.setBackground(getResources().getDrawable(R.drawable.card_background_dark));
                        break;

                    case R.id.icon_crop:
                        categories.set(10, !categories.get(10));
                        if (categories.get(10))
                            button_crop.setBackground(getResources().getDrawable(R.drawable.card_background));
                        else
                            button_crop.setBackground(getResources().getDrawable(R.drawable.card_background_dark));
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

        Log.d("shyam", extras.getStringArrayList("itemnames").toString());

    }

    public void onclick (View w) {
        Intent intent = new Intent(getBaseContext(), AddCropActivity.class);
        intent.putExtra("itemnames", extras.getStringArrayList("itemnames"));
        intent.putExtra("itemprices", extras.getFloatArray("itemprices"));
        intent.putExtra("categories", categories);
        startActivity(intent);
    }

}
