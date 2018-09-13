package com.ssgmail.shubhammsoni.materialapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Voting_Fragment extends Fragment {

    DatabaseReference myroot = FirebaseDatabase.getInstance().getReference();
    EditText enter_code;
    View view;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            view = inflater.inflate(R.layout.fragment_voting_, container, false);
            Button search_btn = (Button) view.findViewById(R.id.search_poll);
            enter_code = (EditText) view.findViewById(R.id.enter_code_et);
            search_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(TextUtils.isEmpty(enter_code.getText())) {

                        Toast.makeText(getContext(), "Enter Code !!", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setMessage("Searching...");
                        progressDialog.show();
                        myroot.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot question : dataSnapshot.getChildren()) {
                                    Question ques = question.getValue(Question.class);
                                    String quesString = ques.getQuestion().toString();
                                    String database_code = ques.getCode().toString();
                                    String Code = enter_code.getText().toString();
                                    if (database_code.equals(Code)) {
                                        Log.d("Condiiton", "true");
                                        Bundle bundle = new Bundle();
                                        Vote vote = new Vote();
                                        bundle.putString("Question", quesString);
                                        bundle.putString("code", database_code);
                                        vote.setArguments(bundle);
                                        FragmentManager manager = getFragmentManager();
                                        if (manager != null) {
                                            FragmentTransaction fragmentTransaction = manager.beginTransaction();
                                            fragmentTransaction.replace(R.id.content, vote, "vote");
                                            progressDialog.dismiss();
                                            fragmentTransaction.commit();
                                        } else {
                                            Log.d("manager", "IS NULL");
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(getContext(), "Invalid Code !!", Toast.LENGTH_SHORT).show();
                                        break;

                                    }


                                }
                            }


                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        progressDialog.dismiss();
                    }
                }
            });
            return view;
        } else {
            getFragmentManager().beginTransaction().replace(R.id.content, new Login()).commit();
            Toast.makeText(getActivity(), "Plzz Sign in first", Toast.LENGTH_LONG).show();
            return view;
        }

    }


}
