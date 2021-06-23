package com.sukdeb.drbooking;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends Fragment  {


    private FirebaseAuth mAuth;

    // TODO: Rename parameter arguments, choose names that match

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Login() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    EditText etemail, etpassword;
    Button btnlogin;
    TextView tvregister;
    ConstraintLayout frame;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
       super.onActivityCreated(savedInstanceState);

         etemail = getActivity().findViewById(R.id.etLemail);
        etpassword=getActivity().findViewById(R.id.etLpass);
        btnlogin = getActivity().findViewById(R.id.btnLog);
        getActivity().findViewById(R.id.tvLog);
        frame= getActivity().findViewById(R.id.consLayout);
        getActivity().findViewById(R.id.framLayoutLog);

        mAuth = FirebaseAuth.getInstance();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Login();
                String email = etemail.getText().toString().trim();
                String password = etpassword.getText().toString().trim();

                if (email.isEmpty()){
                    etemail.setError("Email must be Requerd");
                    etemail.requestFocus();
                    return;
                }
                System.out.println("Your password is" + email);
                if (password.isEmpty()){
                    etpassword.setError("Password must be Requerd");
                    etpassword.requestFocus();
                    return;
                }
                System.out.println("password is" + password);

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        System.out.println("Your email is "+email + "," + "Your password is " +password);
                        System.out.println("signInWithEmailAndPassword" + task);
                        if (task.isSuccessful()){
                            Toast.makeText(getContext(), "Login Sucess", Toast.LENGTH_SHORT).show();
                            frame.setVisibility(view.GONE);
                            Fragment fragment = new GetAppointment();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.frmlayout, fragment);
                            transaction.commit();

                        }
                        else {
                            Toast.makeText(getContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

}