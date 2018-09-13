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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends Fragment {
     TextView t;Button loginbtn;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    EditText emailetx;
    EditText passetx;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_login, container, false);
        loginbtn= (Button) view.findViewById(R.id.btn_login);
         emailetx= (EditText) view.findViewById(R.id.input_email);
        passetx=(EditText)view.findViewById(R.id.input_password);
        t= (TextView) view.findViewById(R.id.clicktoregister);
        firebaseAuth=FirebaseAuth.getInstance();
//       FloatingActionButton fab= ((MainActivity)getActivity()).getFloatingActionButton();
//        fab.hide();
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.content,new Register()).commit();
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(emailetx.getText()) || TextUtils.isEmpty(passetx.getText())) {

                    Toast.makeText(getContext(), "Complete details !!", Toast.LENGTH_SHORT).show();
                }

                else{
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Logging in...");
                    progressDialog.show();

                    firebaseAuth.signInWithEmailAndPassword(emailetx.getText().toString() + "@gmail.com", passetx.getText()
                            .toString()).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "Login successfully", Toast.LENGTH_SHORT).show();
                                getFragmentManager().beginTransaction().
                                        replace(R.id.content, new Voting_Fragment()).commit();

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "Invalid user!!", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }

            }
        });
        return view;
    }



}
