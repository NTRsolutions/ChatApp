package com.example.sagar.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import static android.R.id.message;

/**
 * Created by sagar on 17/4/17.
 */

public class Test extends Activity
{
    Button button,register,signin,signout;
    EditText email,password;
    String Email,Password;
    FirebaseAnalytics fa;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        button=(Button)findViewById(R.id.button);
        register=(Button)findViewById(R.id.signup);
        signin=(Button)findViewById(R.id.signin);
        signout=(Button)findViewById(R.id.signout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an instance of FirebaseAnalytics
                fa = FirebaseAnalytics.getInstance(getApplicationContext());

                // Create a Bundle containing information about
                // the analytics event
                Bundle eventDetails = new Bundle();
                eventDetails.putString("my_message", "Clicked that special button");

                // Log the event
                fa.logEvent("my_custom_event", eventDetails);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email=email.getText().toString();
                Password=password.getText().toString();
                Log.e("Checking String",Email);
                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    // User registered successfully
                                    Log.e("Registration Successful",Email);
                                }
                                else
                                {
                                    FirebaseException e = (FirebaseException)task.getException();
                                    Toast.makeText(getApplicationContext(), "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                 }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email=email.getText().toString();
                Password=password.getText().toString();
                final FirebaseAuth auth = FirebaseAuth.getInstance();
                if (auth.getCurrentUser() != null) {
                    // Already signed in
                    Toast.makeText(getApplicationContext(),"AlreadySignedIN",Toast.LENGTH_LONG).show();
                    // Do nothing
                } else {
                    auth.signInWithEmailAndPassword(Email, Password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // User signed in successfully
                                        Toast.makeText(getApplicationContext(),auth.getCurrentUser().getEmail(),Toast.LENGTH_LONG).show();

                                    }
                                    else
                                    {
                                        FirebaseException e = (FirebaseException)task.getException();
                                        Toast.makeText(getApplicationContext(), "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(),"Signed Out",Toast.LENGTH_LONG).show();
            }
        });
    }
}
