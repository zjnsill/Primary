package com.psi.shen.primary;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import static com.psi.shen.primary.LoginRegisterEnterinfo.NEXT_LOGIN;
import static com.psi.shen.primary.LoginRegisterEnterinfo.NEXT_PHONEVERIFY;

public class LogUtilViewPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Integer> stepList;

    public LogUtilViewPagerAdapter(FragmentManager fm, ArrayList<Integer> stepList){
        super(fm);
        this.stepList=stepList;
    }

    @Override
    public Fragment getItem(int position){
        return switchFragment(stepList.get(position));
    }

    @Override
    public int getCount(){
        return stepList.size();
    }

    private Fragment switchFragment(@LoginRegisterEnterinfo.NEXT_STEP int step){
        Fragment out = new Fragment();
        switch (step) {
            case NEXT_LOGIN:
                out=viewPagerLogin.newInstance();
                break;
            case NEXT_PHONEVERIFY:
                out=viewPagerVerifyPhone.newInstance();
                break;
                default:
                    break;
        }
        return out;
    }
}
