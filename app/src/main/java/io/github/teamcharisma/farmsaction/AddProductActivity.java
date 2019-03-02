package io.github.teamcharisma.farmsaction;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Optional;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class ProductOption {
    String option;
    boolean checked;
    public ProductOption(String option) {
        this.option = option;
        checked = false;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean getChecked(){
        return checked;
    }

    public String getOption() {
        return option;
    }
}

class ProductOptionAdapter extends ArrayAdapter<ProductOption> {

    Context mContext;

    public ProductOptionAdapter(Context context, ArrayList<ProductOption> objects) {
        super(context, 0, objects);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_add_product_button, parent, false);

        TextView tv = convertView.findViewById(R.id.addProduct_productName);
        CheckBox cb = convertView.findViewById(R.id.addProduct_productChecked);
        cb.setChecked(getItem(position).getChecked());
        tv.setText(getItem(position).getOption());
        ProductOption currentOption = getItem(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            currentOption.setChecked(!currentOption.getChecked());
            cb.setChecked(currentOption.getChecked());
            }
        });

        return convertView;
    }

    public float px(float dp){
        return dp * ((float) mContext.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}

public class AddProductActivity extends AppCompatActivity {
    ListView mOptionList;
    ArrayList<ProductOption> products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        products = new ArrayList<>();
        products.add(new ProductOption("Labour"));
        products.add(new ProductOption("Electricity"));
        products.add(new ProductOption("Fertilizers"));
        products.add(new ProductOption("Pesticides"));
        products.add(new ProductOption("Machinery"));
        products.add(new ProductOption("Property Taxes"));
        products.add(new ProductOption("Custom Work"));
        products.add(new ProductOption("Lifestock"));
        products.add(new ProductOption("Rent"));
        products.add(new ProductOption("Fuel"));
        products.add(new ProductOption("Loan"));

        ProductOptionAdapter adapter = new ProductOptionAdapter(this, products);

        mOptionList = findViewById(R.id.addProduct_optionList);
        mOptionList.setAdapter(adapter);
    }

    public void onClickNext(View v){
        Intent intent = new Intent(this, AddCropActivity.class);
        for (ProductOption p : products){
            intent.putExtra("product-type:" + p.getOption(), p.getChecked());
        }
        startActivity(intent);
    }
}
