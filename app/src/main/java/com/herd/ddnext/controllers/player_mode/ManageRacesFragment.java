package com.herd.ddnext.controllers.player_mode;

/**
 * Created by sherd on 1/28/2015.
 */
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.herd.ddnext.R;

public class ManageRacesFragment extends Fragment {

    public ManageRacesFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.player_mode_manage_races, container, false);

        return rootView;
    }
}
