package io.github.teamcharisma.farmsaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.PropertyPermission;
import java.util.concurrent.TimeUnit;

public class IntroActivity extends AppCompatActivity {

    int screenHeight, screenWidth;
    static final String TAG = IntroActivity.class.getSimpleName();
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        mAuth = FirebaseAuth.getInstance();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        new Handler().postDelayed(this::startAnimation, 1000);
    }

    public String getStr(int id) {
        return getResources().getString(id);
    }

    private void startAnimation() {
        if(mAuth.getCurrentUser()!=null || GoogleSignIn.getLastSignedInAccount(this)!=null)
            startActivity(new Intent(IntroActivity.this, DashboardActivity.class));
        ImageView farmLogo = findViewById(R.id.farm_logo),
                charismaLogo = findViewById(R.id.charisma_logo);

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        String language = prefs.getString(getStr(R.string.language_key), "");
        if (!TextUtils.isEmpty(language)) {
            setLanguage(this, language);
            inflateLoginCard();
        } else {
            inflateLanguageCard();
        }
        farmLogo.animate()
                .scaleX(0.5f)
                .scaleY(0.5f)
                .translationY(-1.25f * farmLogo.getY() + px(20))
                .setDuration(600);
        charismaLogo.animate()
                .translationY(screenHeight - charismaLogo.getY())
                .alpha(0)
                .setDuration(600);
    }

    LinearLayout languageCard;

    public void onLanguageSelect(View view) {
        //
        String text = ((TextView) view).getText().toString();
        String language = "en";
        if (text.equals(getStr(R.string.language_ta))) {
            language = "ta";
        } else if (text.equals(getStr(R.string.language_hi))) {
            language = "hi";
        }

        setLanguage(this, language);
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.language_key), language);
        editor.apply();

        hideLanguageCard();
        // now login
        inflateLoginCard();
    }

    public static void setLanguage(Context context, String languageToLoad) {
        Log.d(TAG, "setting language");
        Locale locale = new Locale(languageToLoad); //e.g "sv"
        Locale systemLocale = Locale.getDefault();
        if (systemLocale != null && systemLocale.equals(locale)) {
            Log.d(TAG, "Already correct language set");
            return;
        }
        SecurityManager securityManager = System.getSecurityManager();
        try {
            if (securityManager != null)
                securityManager.checkPermission(new PropertyPermission("user.language", "write"));
            Locale.setDefault(locale);
            android.content.res.Configuration config = new android.content.res.Configuration();
            config.locale = locale;
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
            Log.d(TAG, "Language set");

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void inflateLanguageCard() {
        ImageView farmLogo = findViewById(R.id.farm_logo);
        LinearLayout layout = (LinearLayout) LayoutInflater.from(this)
                .inflate(R.layout.language_card, null);
        languageCard = layout;
        int height = screenHeight - 2 * (int) px(20) - farmLogo.getHeight() / 2;
        layout.setAlpha(0);
        layout.setY(px(20) + farmLogo.getHeight() + farmLogo.getY());
        ((RelativeLayout) findViewById(R.id.intro_root)).addView(layout, RelativeLayout.LayoutParams.MATCH_PARENT, height);
        Log.v(TAG, "LO:" + layout.getLayoutParams().height);

        layout.findViewById(R.id.english_select).setOnClickListener(this::onLanguageSelect);
        layout.findViewById(R.id.hindi_select).setOnClickListener(this::onLanguageSelect);
        layout.findViewById(R.id.tamil_select).setOnClickListener(this::onLanguageSelect);

        RelativeLayout.LayoutParams layoutParams =
                (RelativeLayout.LayoutParams) layout.getLayoutParams();
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);

        layout.setLayoutParams(layoutParams);
        layout.animate()
                .translationY(2 * px(20) + farmLogo.getHeight() / 2.0f)
                .alpha(1)
                .setDuration(600);

        View view = new View(this);
        view.setY(screenHeight);
        view.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        ((RelativeLayout) findViewById(R.id.intro_root)).addView(view, RelativeLayout.LayoutParams.MATCH_PARENT, (int) px(30));
    }

    private void hideLanguageCard() {
        languageCard.animate()
                .translationX(-languageCard.getWidth())
                .alpha(0)
                .setDuration(600);
    }

    private void showLanguageCard(LinearLayout layout) {
        languageCard.animate()
                .translationX(languageCard.getWidth())
                .alpha(1)
                .setDuration(600);
    }

    /* LOGIN HELPERS */

    static int REQUEST_CODE_SIGN_IN = 1;
    DriveServiceHelper mDriveServiceHelper;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private void configureLoginEditText(EditText edt) {
        edt.setText("+91 ");
        Selection.setSelection(edt.getText(), edt.getText().length());


        edt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().startsWith("+91 ")) {
                    edt.setText("+91 ");
                }

            }
        });
    }

    private void inflateLoginCard() {
        ImageView farmLogo = findViewById(R.id.farm_logo);
        LinearLayout layout = (LinearLayout) LayoutInflater.from(this)
                .inflate(R.layout.login_card, null);
        int height = screenHeight - 2 * (int) px(20) - farmLogo.getHeight() / 2;
        ((RelativeLayout) findViewById(R.id.intro_root)).addView(layout, RelativeLayout.LayoutParams.MATCH_PARENT, height);
        EditText phoneNumberEditText = layout.findViewById(R.id.phone_login_edittext);
        configureLoginEditText(phoneNumberEditText);
        findViewById(R.id.google_login_button).setOnClickListener(this::requestSignIn);
        findViewById(R.id.phone_login_button).setOnClickListener(this::requestSignIn);

        layout.setX(0);
        layout.setY(2*px(80));
    }

    public void requestSignIn(View view) {
        Log.d(TAG, "Requesting sign-in");
        switch (view.getId()) {
            case R.id.google_login_button: {
                GoogleSignInOptions signInOptions =
                        new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(getResources().getString(R.string.api_key))
                                .requestScopes(new Scope(DriveScopes.DRIVE_FILE))
                                .build();
                GoogleSignInClient client = GoogleSignIn.getClient(this, signInOptions);

                // The result of the sign-in Intent is handled in onActivityResult.
                startActivityForResult(client.getSignInIntent(), REQUEST_CODE_SIGN_IN);
            }
            case R.id.phone_login_button: {

                String phoneNumber = ((EditText) findViewById(R.id.phone_login_edittext)).getText().toString().replace(" ", "");
                mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential credential) {
                        // This callback will be invoked in two situations:
                        // 1 - Instant verification. In some cases the phone number can be instantly
                        //     verified without needing to send or enter a verification code.
                        // 2 - Auto-retrieval. On some devices Google Play services can automatically
                        //     detect the incoming verification SMS and perform verification without
                        //     user action.
                        Log.d(TAG, "onVerificationCompleted:" + credential);
                        signInWithPhoneAuthCredential(credential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        // This callback is invoked in an invalid request for verification is made,
                        // for instance if the the phone number format is not valid.

                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            // Invalid request
                            // ...
                            Log.w(TAG, "FirebaseAuthInvalidCredentialsException", e);
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            // The SMS quota for the project has been exceeded
                            // ...
                            Log.w(TAG, "FirebaseTooManyRequestsException", e);
                        } else
                            Log.w(TAG, "onVerificationFailed", e);


                        // Show a message and update the UI
                        // ...
                    }

                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken token) {
                        // The SMS verification code has been sent to the provided phone number, we
                        // now need to ask the user to enter the code and then construct a credential
                        // by combining the code with a verification ID.
                        Log.d(TAG, "onCodeSent:" + verificationId);

                        // Save verification ID and resending token so we can use them later
                        Intent otp = new Intent(IntroActivity.this, OTPActivity.class);
                        otp.putExtra("verificationId", verificationId);
                        startActivity(otp);
                    }
                };

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phoneNumber,        // Phone number to verify
                        60,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        this,               // Activity (for callback binding)
                        mCallbacks);
            }
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        Log.v(TAG, credential+"");
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(IntroActivity.this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        startActivity(new Intent(this, DashboardActivity.class));
                        // ...
                    } else {
                        // Sign in failed, display a message and update the UI
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                        }
                    }
                });
    }

    /**
     * Handles the {@code result} of a completed sign-in activity initiated from {@link
     * #requestSignIn(View)}.
     */
    private void handleSignInResult(Intent result) {
        GoogleSignIn.getSignedInAccountFromIntent(result)
                .addOnSuccessListener(googleAccount -> {
                    Log.d(TAG, "Signed in as " + googleAccount.getEmail());
                    firebaseAuthWithGoogle(googleAccount);
                    signInToDrive(googleAccount);
                })
                .addOnFailureListener(exception ->
                        Log.e(TAG, "Unable to sign in.", exception)
                );
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        startActivity(new Intent(this, DashboardActivity.class));
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                    }

                    // ...
                });
    }

    void signInToDrive(GoogleSignInAccount googleAccount) {
        List<String> list = new ArrayList<>();
        list.add(DriveScopes.DRIVE_APPDATA);
        list.add(DriveScopes.DRIVE_FILE);
        GoogleAccountCredential credential =
                GoogleAccountCredential.usingOAuth2(
                        this, list);
        credential.setSelectedAccount(googleAccount.getAccount());
        Drive googleDriveService =
                new Drive.Builder(
                        AndroidHttp.newCompatibleTransport(),
                        new GsonFactory(),
                        credential)
                        .setApplicationName(getResources().getString(R.string.app_name))
                        .build();

        // The DriveServiceHelper encapsulates all REST API and SAF functionality.
        // Its instantiation is required before handling any onClick actions.
        AsyncTask.execute(() -> mDriveServiceHelper =
                new DriveServiceHelper(googleDriveService, IntroActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_SIGN_IN) {
            if (data != null)
                handleSignInResult(data);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /*-- END LOGIN HELPERS --*/

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public float px(float dp) {
        return dp * ((float) getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @return A float value to represent dp equivalent to px value
     */
    public float dp(float px) {
        return px / ((float) getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
