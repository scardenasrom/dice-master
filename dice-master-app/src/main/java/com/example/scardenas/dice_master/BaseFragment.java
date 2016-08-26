package com.example.scardenas.dice_master;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.AnimationUtils;

public class BaseFragment extends Fragment {

    protected void makeViewAppear(Context context, View view) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
            view.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in));
        }
    }

    protected void makeViewDisappear(Context context, View view) {
        if (view.getVisibility() != View.GONE) {
            view.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out));
            view.setVisibility(View.GONE);
        }
    }

}
