package com.ssgmail.shubhammsoni.materialapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends Fragment {
private  Button registerbtn; EditText emailetx; EditText passetx;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_register, container, false);
        registerbtn= (Button) view.findViewById(R.id.btn_register);
        progressDialog =new ProgressDialog(getActivity());
         emailetx=(EditText)view.findViewById(R.id.newemail);
         passetx=(EditText)view.findViewById(R.id.new_registerpassword);
        FloatingActionButton fab= ((MainActivity)getActivity()).getFloatingActionButton();
        fab.hide();
        firebaseAuth=FirebaseAuth.getInstance();
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(emailetx.getText()) || TextUtils.isEmpty(passetx.getText())) {

                    Toast.makeText(getContext(), "Complete details !!", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressDialog.setMessage("Registering plzz wait...");
                    progressDialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(emailetx.getText().toString() + "@gmail.com"
                            , passetx.getText().toString()).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getActivity(), "Registered successfully", Toast.LENGTH_SHORT).show();
                                        getFragmentManager().beginTransaction().replace(R.id.content, new Login()).commit();
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(getActivity(), "Registeration failed !!", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }
                    );
                }
            }
        });

        return  view;
    }

}
