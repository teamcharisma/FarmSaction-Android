package io.github.teamcharisma.farmsaction;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.time.temporal.TemporalQueries;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


class CropCard {
    String productName;
    ArrayList<CropElement> crops;
    Context context;
    View card;
    public CropCard (String productName, Context context) {
        this.productName = productName;
        crops = new ArrayList<>();

        this.context = context;
        card = LayoutInflater.from(context).inflate(R.layout.layout_card_add_crop, null, false);
        TextView tv = card.findViewById(R.id.addCrop_productName);
        tv.setText(productName);
        crops.add(new CropElement("Rice", 0));
        ImageView button = card.findViewById(R.id.addCrop_add);
        CropAdapter ca = new CropAdapter(context, crops);
        ListView lv = card.findViewById(R.id.addCrop_elementList);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crops.add(new CropElement("Rice", 0));
                Log.d("Shyam", String.valueOf(crops.size()));
                ca.notifyDataSetChanged();
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) lv.getLayoutParams();
                lp.height = lp.height * crops.size() / (crops.size()-1) ;
                lv.setLayoutParams(lp);
            }
        });

        lv.setAdapter(ca);
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductName() {
        return productName;
    }
    public void addCrop(CropElement c){
        crops.add(c);
    }
    public CropElement getCrop(int position) {
        return crops.get(position);
    }
    public View getView(){
        return card;
    }
}

class CropElement {
    String crop;
    float value;

    public CropElement(String crop, float value) {
        this.crop = crop;
        this.value = value;

    }
    public void setCrop(String crop) {
        this.crop = crop;
    }
    public void setValue(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public String getCrop () {
        return crop;
    }
}

class CropAdapter extends ArrayAdapter<CropElement> {
    Context mContext;
    ArrayList<String> crops;
    ArrayList<CropElement> objects;

    public CropAdapter(Context context, ArrayList<CropElement> objects) {
        super(context, 0, objects);
        mContext = context;
        crops = new ArrayList<>();
        crops.add("Rice");
        crops.add("wheat");
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_crop_element, parent, false);
        CropElement c = getItem(position);
        Spinner spinner = convertView.findViewById(R.id.addCrop_cropSpinner);

        EditText editText = convertView.findViewById(R.id.addCrop_money);
        editText.setText(String.valueOf(c.getValue()));
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                c.setCrop(s.toString());
            }
        });
        spinner.setAdapter(new SpinnerAdapter() {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                if (convertView == null)
                    convertView = new TextView(mContext);
                TextView tv = (TextView) convertView;
                tv.setPadding(16, 16,16,16);
                tv.setText(crops.get(position));

                return convertView;
            }

            @Override
            public CropElement getItem(int position)
            {
                return objects.get(position);
            }

            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public int getCount() {
                return crops.size();
            }


            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null)
                    convertView = new TextView(mContext);
                ((TextView)convertView).setText(crops.get(position));
                return convertView;
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        });
        spinner.setSelection(crops.indexOf(c.getCrop()));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                c.setCrop(crops.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                c.setCrop(crops.get(0));
                spinner.setSelection(0);
            }
        });


        return convertView;
    }
}

public class AddCropActivity extends AppCompatActivity  {

    ArrayList<CropCard> cropCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_crop);
        Intent receivedIntent = getIntent();

        ArrayList<String> products = new ArrayList<>();
        products.add("Labour");
        products.add("Electricity");
        products.add("Fertilizers");
        products.add("Pesticides");
        products.add("Machinery");
        products.add("Property Taxes");
        products.add("Custom Work");
        products.add(("Lifestock"));
        products.add("Rent");
        products.add(("Fuel"));
        products.add(("Loan"));

        cropCards = new ArrayList<>();
        LinearLayout list = findViewById(R.id.addCrop_mainList);

        for (String p : products) {
            if (receivedIntent.getBooleanExtra(p, false)) {
                Log.d("Shyam", p + " is there!");
                cropCards.add(new CropCard(p, this));
                list.addView(cropCards.get(cropCards.size() - 1).getView());
            }
        }

    }
}
