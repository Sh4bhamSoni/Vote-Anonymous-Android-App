package com.ssgmail.shubhammsoni.materialapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class Create_poll_Fragment extends Fragment {
    private EditText enter_question_et;
    private Button create_poll_btn;
    private Integer code;
    private String getQuestion;
    private String key;

    View view;
    private FirebaseAuth firebaseAuth;
    Question questionobj;
    Integer countAgree = 0;
    Integer countDisagree = 0;
    DatabaseReference myroot = FirebaseDatabase.getInstance().getReference();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**********************************ON CREATE VIEW METHOD******************************************/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            view = inflater.inflate(R.layout.fragment_create_poll_, container, false);
            enter_question_et = (EditText) view.findViewById(R.id.enter_question_edittext);
            create_poll_btn = (Button) view.findViewById(R.id.create_poll_btn);

            create_poll_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(TextUtils.isEmpty(enter_question_et.getText())) {

                        Toast.makeText(getContext(), "Question can't be empty !!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Random random = new Random();

                        code = random.nextInt(5678) + 1234;
                        getQuestion = enter_question_et.getText().toString();
                        key = myroot.push().getKey();


                        questionobj = new Question(code, getQuestion, key, countAgree, countDisagree);
                        myroot.child(key).setValue(questionobj);


                        Bundle code_bundle = new Bundle();
                        code_bundle.putString("code", code.toString());
                        Dialog_Fragment dialog_fragment = new Dialog_Fragment();
                        dialog_fragment.setArguments(code_bundle);
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content, dialog_fragment).commit();
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









