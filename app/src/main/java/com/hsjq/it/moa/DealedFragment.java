package com.hsjq.it.moa;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;//新建的Fragment一律使用新版的引用
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class DealedFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dealed, container, false);
    }


}
