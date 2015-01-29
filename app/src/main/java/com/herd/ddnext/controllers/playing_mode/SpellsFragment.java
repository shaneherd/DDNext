package com.herd.ddnext.controllers.playing_mode;

/**
 * Created by sherd on 1/28/2015.
 */
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.herd.ddnext.R;

public class SpellsFragment extends Fragment {

    public SpellsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.playing_mode_spells, container, false);

        return rootView;
    }
}
