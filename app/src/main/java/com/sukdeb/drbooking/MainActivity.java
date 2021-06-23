package com.sukdeb.drbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText etname, etcpass, etpas, etemail, etphone, etage;
    Button btnsubmit;
    FrameLayout FramLayout;
    ConstraintLayout main;
    TextView tvrlog;
    String name,email,pass,cpass;
    int age;
    long ph;


    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etname = (EditText) findViewById(R.id.etName);
        etage = (EditText) findViewById(R.id.etAge);
        etphone = (EditText) findViewById(R.id.etPh);
        etemail = (EditText) findViewById(R.id.etEmail);
        etpas = (EditText) findViewById(R.id.etPass);
        etcpass = (EditText) findViewById(R.id.etCpass);
        btnsubmit = (Button) findViewById(R.id.btnSub);
        main = (ConstraintLayout) findViewById(R.id.fmLay);
        tvrlog = findViewById(R.id.tvRlog);
        tvrlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.setVisibility(view.GONE);
                Fragment fragment = new Login();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frmlayout, fragment);
                transaction.commit();
            }
        });
        findViewById(R.id.frmlayout);

        mAuth = FirebaseAuth.getInstance();

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation();



               // System.out.println("xxxxxxxcreateUserWithEmailAndPassword");
                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       // System.out.println("createUserWithEmailAndPassword" + task);
                        if (task.isSuccessful()){
                            String email_ID = mAuth.getCurrentUser().getUid();
                            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");
                            Map map = new HashMap();
                            map.put("name",name);
                            map.put("age",age);
                            map.put("phone",ph);
                            map.put("email",email);
                            myRef.setValue(map);
                            Toast.makeText(getApplicationContext(), "User Regestration Sucessfull", Toast.LENGTH_SHORT).show();
                            main.setVisibility(view.GONE);
                            Fragment fragment = new Login();
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frmlayout, fragment);
                            transaction.commit();
                        }
                        else {
                            Toast.makeText(MainActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    public void Validation (){
        //System.out.println("Validation");
        name = etname.getText().toString().trim();
        age = Integer.parseInt(etage.getText().toString().trim());
        //String age2 = String.valueOf(age);
         ph = Long.parseLong(etphone.getText().toString().trim());
        //String phone = String.valueOf(ph);
         email = etemail.getText().toString().trim();
         pass = etpas.getText().toString().trim();
         cpass = etcpass.getText().toString().trim();

       // System.out.println("Validation1");
        if (name.isEmpty()){
            etname.setError("Name is Must be Requerd");
            etname.requestFocus();
            return;
        }
       /*if (age2.isEmpty()){
            etage.setError("Age Must be Requerd");
            etage.requestFocus();
            return;
        }*/

       if (email.isEmpty()){
            etemail.setError("Email is Must be Requerd");
            etemail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etemail.setError("Enter Valid Email");
            etemail.requestFocus();
            return;
        }
        //System.out.println("Validation3");
        if (etpas.length()<6){
            etpas.setError("Password length Should be minimum 6");
            etpas.requestFocus();
            return;
        }
        if (pass.isEmpty()){
            etpas.setError("Password is Must be Requerd");
            etpas.requestFocus();
            return;
        }
        //System.out.println("Your Password is" + pass + "," +"Your COnfurm Password is " + cpass);
        if (pass != cpass){
            etcpass.setError("Confarm password must be same as Password");
            etpas.requestFocus();
            return;
        }

    }

}