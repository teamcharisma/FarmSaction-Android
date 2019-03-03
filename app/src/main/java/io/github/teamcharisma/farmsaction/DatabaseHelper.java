package io.github.teamcharisma.farmsaction;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class DatabaseHelper {

    private Context mContext;
    private String TAG = "ASL";
    public ArrayList<Bill> bills;
    DatabaseReference database;

    DatabaseHelper(Context context) {
        mContext = context;
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                bills = dataSnapshot.getValue(new GenericTypeIndicator<ArrayList<Bill>>() {
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };

        database = FirebaseDatabase.getInstance().getReference();
        database.child("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .addValueEventListener(postListener);
    }

    void addItem(Bill b) {
        bills.add(b);
        database.child("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .setValue(bills);
    }

}
