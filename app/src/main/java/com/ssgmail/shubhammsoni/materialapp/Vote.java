package com.ssgmail.shubhammsoni.materialapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Vote extends Fragment {
    RadioGroup radioGroup;
    RadioButton rb;
    Button viewChartbtn;
    String key;
    boolean new_user = true;
    EditText commentetx;
    private FirebaseAuth firebaseAuth;
    private Integer agree_count = 0;
    private Integer disagree_count = 0;
    ArrayList<String> voterlist;
    DatabaseReference myroot = FirebaseDatabase.getInstance().getReference();


    @Override
    public void onStart() {
        super.onStart();
        Log.d("Vote", "onStart: ");


        firebaseAuth = FirebaseAuth.getInstance();
        Log.d("myroot reference", myroot.toString());
        myroot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("vote OnDatacHange", "executing");
                for (DataSnapshot question : dataSnapshot.getChildren()) {
                    Question ques = question.getValue(Question.class);

                    Log.d("Question obj", ques.getCountAgree().toString() + ques.getCountDisagree());
                    String database_code = ques.getCode().toString();
                    Log.d("Your databse code", database_code);
                    key = ques.getKey();
                    Log.d("key", key);

                    Bundle bundle1 = getArguments();
                    String Code = bundle1.getString("code");
                    Log.d("entered code", Code);


                    if (database_code.equals(Code)) {
                        Log.d("Condition", "true");
                        agree_count = ques.getCountAgree();
                        disagree_count = ques.getCountDisagree();


                        DatabaseReference myvoters = myroot.child(key).child("voters");

                        myvoters.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                voterlist = new ArrayList<String>();

                                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                    voterlist.add(String.valueOf(dsp.getValue()));
                                    Log.d("arraylist", "building");
                                }

                                for (int i = 0; i < voterlist.size(); i++) {
                                    Log.d("arraylist", "fetching");
                                    Log.d("uid", firebaseAuth.getCurrentUser().getUid());
                                    Log.d("voterid", voterlist.get(i));
                                    if (voterlist.get(i).equals(firebaseAuth.getCurrentUser().getUid())) {
                                        new_user = false;
                                        Log.d("newuser", "false");
                                        break;
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                                    Toast.makeText(getContext(),"Invalid Code !!", Toast.LENGTH_SHORT).show();

                            }
                        });


                        Log.d("Count values", "Agree" + agree_count + "Dis" + disagree_count);

                        break;
                    }



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("vote", "onCreateView: ");

        View view = inflater.inflate(R.layout.fragment_vote, container, false);
        radioGroup = (RadioGroup) view.findViewById(R.id.group_rb);
        viewChartbtn = (Button) view.findViewById(R.id.view_chart);
        final Bundle bundle = getArguments();
        TextView displayQues = (TextView) view.findViewById(R.id.display_question);
        displayQues.setText(bundle.getString("Question"));
        Button Submit_btn = (Button) view.findViewById(R.id.submit);
        Submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selected_radio_btn = radioGroup.getCheckedRadioButtonId();
                rb = (RadioButton) view.findViewById(selected_radio_btn);

                if (new_user == false) {
                    Toast.makeText(getActivity(), "You have already voted", Toast.LENGTH_LONG).show();
                }

                if (new_user == true) {

                    if (selected_radio_btn == R.id.agree_rb) {

                        agree_count++;
                        Log.d("key while updating", key);
                        Log.d("Agree button is clicked", agree_count.toString());
                        myroot.child(key).child("countAgree").setValue(agree_count);
                        myroot.child(key).child("voters").push().setValue(firebaseAuth.getCurrentUser().getUid());
                        Toast.makeText(getActivity(), "Response Submitted", Toast.LENGTH_SHORT).show();

                    }

                    if (selected_radio_btn == R.id.disagree_rb) {
                        Log.d("key while updating", key);
                        disagree_count++;
                        Log.d("Dis button is clicked", disagree_count.toString());
                        myroot.child(key).child("countDisagree").setValue(disagree_count);
                        myroot.child(key).child("voters").push().setValue(firebaseAuth.getCurrentUser().getUid());
                        Toast.makeText(getActivity(), "Response Submitted", Toast.LENGTH_SHORT).show();
                    }

                }
            }

        });


        viewChartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle chartBundle = new Bundle();
                chartBundle.putInt("agree", agree_count);
                chartBundle.putInt("disagree", disagree_count);
                Chart chart = new Chart();
                chart.setArguments(chartBundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content, chart).commit();

            }
        });

        return view;
    }


}


