package io.github.teamcharisma.farmsaction;


import java.util.ArrayList;

public class Bill {
    public ArrayList<String> itemNames;
    public ArrayList<String> prices;
    public ArrayList<String> categories;
    public ArrayList<String> crops;

    Bill(){
        itemNames = new ArrayList<>();
        prices = new ArrayList<>();
        categories = new ArrayList<>();
        crops = new ArrayList<>();
    }

}
