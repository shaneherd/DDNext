package com.herd.ddnext.controllers.manage_mode;

/**
 * Created by sherd on 1/28/2015.
 */
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.herd.ddnext.R;

public class DuplicateItemFragment extends Fragment {

    public DuplicateItemFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.manage_mode_duplicate_item, container, false);

        return rootView;
    }
}
