package com.vladlanda.firebasetut;

import android.content.Intent;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by udusa on 02/12/2016.
 */

public class LoginActivity extends AppCompatActivity {


    EditText editTextEmail, editTextPassword;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        loginBtn = (Button)findViewById(R.id.btnLogin);

        firebaseAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("onAuthStateChanged", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("onAuthStateChanged", "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            firebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void loginBtnClicked(View v){
        String email = editTextEmail.getText().toString();
        String pass = editTextPassword.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Wrong editTextEmail or editTextPassword",Toast.LENGTH_SHORT);
                }else{
                    Toast.makeText(getApplicationContext(),"You are loged in successfully",Toast.LENGTH_SHORT);
                    startActivity(new Intent(getApplicationContext(),MessagesActivity.class));
                }
            }
        });
    }

    public void registerBtnClicked(View v){
        startActivity(new Intent(getApplicationContext(),MessagesActivity.class));
        String email = editTextEmail.getText().toString();
        String pass = editTextPassword.getText().toString();
        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Cannot connect to server",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}


