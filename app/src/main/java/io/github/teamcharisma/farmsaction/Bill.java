package io.github.teamcharisma.farmsaction;


import java.util.ArrayList;
import java.util.Date;

public class Bill {
    public ArrayList<String> itemNames;
    public ArrayList<Float> prices;
    public ArrayList<String> categories;
    public ArrayList<String> crops;
    public Date date;
    public boolean isSelling;

    Bill(ArrayList<String> itns, ArrayList<String> prcs, ArrayList<String> ctgrs, ArrayList<String> crps, Date dat, boolean b) {
        itemNames = itns;
        setPrices(prcs);
        categories = ctgrs;
        crops = crps;
        date = dat;
        isSelling = b;
    }

    void setPrices(ArrayList<String> p) {
        prices = new ArrayList<>();
        for (String pp : p) {
            prices.add(Float.valueOf(pp));
        }
    }

}
