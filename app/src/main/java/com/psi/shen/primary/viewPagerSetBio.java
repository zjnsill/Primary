package com.psi.shen.primary;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class viewPagerSetBio extends Fragment {
    public interface setedBioEmail{
        public void set_Bio(String Bio);
        public void set_Email(String Email);
    }
    String originalEmail,OriginalBio;
    setedBioEmail bioEmail;
    EditText setedEmail,BioContent;
    Button savedBioBtn;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstaceState){
        View view = inflater.inflate(R.layout.viewpager_set_bio,null);
        setedEmail = view.findViewById(R.id.setedEmail);
        BioContent = view.findViewById(R.id.BioContnt);
        savedBioBtn = view.findViewById(R.id.saveBioBtn);

        setedEmail.setText(originalEmail);
        BioContent.setText(OriginalBio);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedinstance){
        super.onActivityCreated(savedinstance);
        savedBioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bioEmail.set_Bio(BioContent.getText().toString());
                bioEmail.set_Email(setedEmail.getText().toString());
            }
        });

    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        bioEmail=(setedBioEmail)context;
    }

    public Fragment newInstance(){
        viewPagerSetBio fragment = new viewPagerSetBio();
        return fragment;
    }


}
