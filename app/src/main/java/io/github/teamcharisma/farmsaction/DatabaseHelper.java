package io.github.teamcharisma.farmsaction;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class DatabaseHelper {

    private Context mContext;

    DatabaseHelper(Context context) {
        mContext = context;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");
        DatabaseReference myRef = ref.child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()));
    }

}
