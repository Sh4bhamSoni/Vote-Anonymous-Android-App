package com.ssgmail.shubhammsoni.materialapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Dialog_Fragment extends Fragment {
    TextView textView_cod;
    Button ok_btn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_, container, false);
        textView_cod = (TextView) view.findViewById(R.id.code);
        Bundle bundle = getArguments();
        String code = bundle.getString("code");
        textView_cod.setText(code);

        ok_btn= (Button) view.findViewById(R.id.dialog_ok_btn);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager=(ClipboardManager)getActivity()
                        .getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData=ClipData.newPlainText("CODE",textView_cod.getText());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getContext(),"Code Copied to Clipboared",Toast.LENGTH_SHORT).show();
                FragmentManager manager=getFragmentManager();
                FragmentTransaction fragmentTransaction=manager.beginTransaction();
                fragmentTransaction.replace(R.id.content, new Voting_Fragment()).commit();

            }
        });



        return view;



    }


}

