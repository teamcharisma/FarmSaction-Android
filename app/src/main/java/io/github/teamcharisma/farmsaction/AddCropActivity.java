package io.github.teamcharisma.farmsaction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import java.util.ArrayList;

public class AddCropActivity extends AppCompatActivity {

    ArrayList<TextView> buttons;
    Intent oldIntent;
    Bundle extras;
    String crop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_crop);

        oldIntent = getIntent();
        extras = oldIntent.getExtras();
        int currentId = -1;
        buttons = new ArrayList<>();

        int id[] = {
            R.id.rice,
            R.id.wheat,
            R.id.maize,
            R.id.cotton,
            R.id.bajra,
            R.id.rabi,
            R.id.tea
        };

        for(int i = 0; i < id.length; i++)
            buttons.add(findViewById(id[i]));

        View.OnClickListener onclick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i < id.length; i++) {
                    if (v.getId() == id[i]) {

                        crop = ((TextView)v).getText().toString();
                        //crops.set(i, !crops.get(i));
                       // if (crops.get(i))
                       //     buttons.get(i).setBackgroundColor(getResources().getColor(R.color.colorSelected));
                       // else
                         //   buttons.get(i).setBackgroundColor(getResources().getColor(R.color.colorTitle));
                    }
                }
            }
        };

        for (TextView t : buttons)
            t.setOnClickListener(onclick);
    }

    public void onclick(View w){
        Intent intent = new Intent(getApplicationContext(), BillActivity.class);
        intent.putExtra("itemnames", extras.getStringArrayList("itemnames"));
        intent.putExtra("itemprices", extras.getStringArrayList("itemprices"));
        intent.putExtra("categories", extras.getStringArrayList("categories"));
        ArrayList<String> crops;
        if (oldIntent.hasExtra("crops"))
            crops = extras.getStringArrayList("crops");
        else
            crops = new ArrayList<>();
        crops.add(crop);
        intent.putExtra("crops", crops);
        startActivity(intent);
    }
}
