package com.herd.ddnext;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.herd.ddnext.adapter.NavDrawerListAdapter;
import com.herd.ddnext.controllers.manage_mode.ChangeCharacterFragment;
import com.herd.ddnext.controllers.manage_mode.DeleteItemFragment;
import com.herd.ddnext.controllers.manage_mode.DiscardChangesFragment;
import com.herd.ddnext.controllers.manage_mode.DuplicateItemFragment;
import com.herd.ddnext.controllers.manage_mode.SaveChangesFragment;
import com.herd.ddnext.controllers.player_mode.AddCharacterFragment;
import com.herd.ddnext.controllers.player_mode.EditCharactersFragment;
import com.herd.ddnext.controllers.player_mode.HomeFragment;
import com.herd.ddnext.controllers.player_mode.ManageClassesFragment;
import com.herd.ddnext.controllers.player_mode.ManageRacesFragment;
import com.herd.ddnext.controllers.playing_mode.AttackFragment;
import com.herd.ddnext.controllers.playing_mode.ChangeCharacterPlayingFragment;
import com.herd.ddnext.controllers.playing_mode.CharacterInfoFragment;
import com.herd.ddnext.controllers.playing_mode.EditCharacterFragment;
import com.herd.ddnext.controllers.playing_mode.EquipmentFragment;
import com.herd.ddnext.controllers.playing_mode.FeaturesFragment;
import com.herd.ddnext.controllers.playing_mode.NotesFragment;
import com.herd.ddnext.controllers.playing_mode.SkillsFragment;
import com.herd.ddnext.controllers.playing_mode.SpellsFragment;
import com.herd.ddnext.controllers.playing_mode.StatisticsFragment;
import com.herd.ddnext.controllers.playing_mode.TraitsFragment;
import com.herd.ddnext.model.NavDrawerItem;

public class MainActivity extends Activity {
    private final String PLAYER_MODE = "playerMode";
    private final String PLAYING_MODE = "playingMode";
    private final String MANAGE_MODE = "manageMode";

    private String currentMode;
    private String[] currentMenuTitles;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private int position;
    private ArrayList<Fragment> fragmentStack;
    private ArrayList<Integer> positionStack;
    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] playerModeMenuTitles;
    private String[] playingModeMenuTitles;
    private String[] manageMenuTitles;

//    private TypedArray navMenuIcons;
    private TypedArray playerModeMenuIcons;
    private TypedArray playingModeMenuIcons;
    private TypedArray manageMenuIcons;

//    private ArrayList<NavDrawerItem> navDrawerItems;
    private ArrayList<NavDrawerItem> playerModeMenuItems;
    private ArrayList<NavDrawerItem> playingModeMenuItems;
    private ArrayList<NavDrawerItem> manageMenuItems;

//    private NavDrawerListAdapter adapter;
    private NavDrawerListAdapter playerModeMenuAdapter;
    private NavDrawerListAdapter playingModeMenuAdapter;
    private NavDrawerListAdapter manageMenuAdapter;
    private NavDrawerListAdapter currentMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize backwards navigation stack
        fragmentStack = new ArrayList<>();
        fragmentStack.add(new HomeFragment());
//        fragmentStack.add(new CharacterInfoFragment());
//        fragmentStack.add(new SaveChangesFragment());

        positionStack = new ArrayList<>();
        positionStack.add(0);

        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        playerModeMenuTitles = getResources().getStringArray(R.array.player_mode_menu_items);
        playingModeMenuTitles = getResources().getStringArray(R.array.playing_mode_menu_items);
        manageMenuTitles = getResources().getStringArray(R.array.manage_menu_items);

        // nav drawer icons from resources
        playerModeMenuIcons = getResources().obtainTypedArray(R.array.player_mode_menu_icons);
        playingModeMenuIcons = getResources().obtainTypedArray(R.array.playing_mode_menu_icons);
        manageMenuIcons = getResources().obtainTypedArray(R.array.manage_menu_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        playerModeMenuItems = new ArrayList<NavDrawerItem>();
        playingModeMenuItems = new ArrayList<NavDrawerItem>();
        manageMenuItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        for (int i = 0; i < playerModeMenuTitles.length; i++) {
            playerModeMenuItems.add(new NavDrawerItem(playerModeMenuTitles[i], playerModeMenuIcons.getResourceId(i, -1)));
        }

        for (int i = 0; i < playingModeMenuTitles.length; i++) {
            playingModeMenuItems.add(new NavDrawerItem(playingModeMenuTitles[i], playingModeMenuIcons.getResourceId(i, -1)));
        }

        for (int i = 0; i < manageMenuTitles.length; i++) {
            manageMenuItems.add(new NavDrawerItem(manageMenuTitles[i], manageMenuIcons.getResourceId(i, -1)));
        }

        // Recycle the typed array
        playerModeMenuIcons.recycle();
        playingModeMenuIcons.recycle();
        manageMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // Create the adapters
        playerModeMenuAdapter = new NavDrawerListAdapter(getApplicationContext(), playerModeMenuItems);
        playingModeMenuAdapter = new NavDrawerListAdapter(getApplicationContext(), playingModeMenuItems);
        manageMenuAdapter = new NavDrawerListAdapter(getApplicationContext(), manageMenuItems);

        //set the mode to start in
        currentMode = PLAYER_MODE;
        currentMenuTitles = playerModeMenuTitles;
        currentMenuAdapter = playerModeMenuAdapter;

//        currentMode = PLAYING_MODE;
//        currentMenuTitles = playingModeMenuTitles;
//        currentMenuAdapter = playingModeMenuAdapter;

//        currentMode = MANAGE_MODE;
//        currentMenuTitles = manageMenuTitles;
//        currentMenuAdapter = manageMenuAdapter;

        // Set the adapter
        mDrawerList.setAdapter(currentMenuAdapter); //TODO - if this doesn't work, use if statements

        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
    }

    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* *
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Displaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
        this.position = position;
        Fragment fragment = null;
        FragmentManager fragmentManager;
        if (currentMode == PLAYER_MODE) {
            switch (position) {
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new AddCharacterFragment();
                    break;
                case 2:
                    fragment = new EditCharactersFragment();
                    break;
                case 3:
//                    fragment = new ManageRacesFragment();
                    //TODO - change menu to playing mode
                    currentMode = PLAYING_MODE;
                    currentMenuTitles = playingModeMenuTitles;
                    currentMenuAdapter = playingModeMenuAdapter;

                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, new CharacterInfoFragment()).commit();

                    mDrawerList.setAdapter(currentMenuAdapter);
                    mDrawerList.setItemChecked(0, true);
                    mDrawerList.setSelection(0);
                    setTitle(currentMenuTitles[0]);
                    mDrawerLayout.closeDrawer(mDrawerList);
                    break;
                case 4:
//                    fragment = new ManageClassesFragment();
                    //TODO - change menu to manage mode
                    currentMode = MANAGE_MODE;
                    currentMenuTitles = manageMenuTitles;
                    currentMenuAdapter = manageMenuAdapter;
                    mDrawerList.setAdapter(currentMenuAdapter);

                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, new SaveChangesFragment()).commit();

                    mDrawerList.setAdapter(currentMenuAdapter);
                    mDrawerList.setItemChecked(0, true);
                    mDrawerList.setSelection(0);
                    setTitle(currentMenuTitles[0]);
                    mDrawerLayout.closeDrawer(mDrawerList);
                    break;
                default:
                    break;
            }
        } else if (currentMode == PLAYING_MODE) {
            switch (position) {
                case 0:
                    fragment = new CharacterInfoFragment();
                    break;
                case 1:
                    fragment = new AttackFragment();
                    break;
                case 2:
                    fragment = new SpellsFragment();
                    break;
                case 3:
                    fragment = new FeaturesFragment();
                    break;
                case 4:
                    fragment = new SkillsFragment();
                    break;
                case 5:
                    fragment = new TraitsFragment();
                    break;
                case 6:
                    fragment = new EquipmentFragment();
                    break;
                case 7:
                    fragment = new StatisticsFragment();
                    break;
                case 8:
                    fragment = new NotesFragment();
                    break;
                case 9:
                    fragment = new EditCharacterFragment();
                    break;
                case 10:
//                    fragment = new ChangeCharacterPlayingFragment();
                    currentMode = PLAYER_MODE;
                    currentMenuTitles = playerModeMenuTitles;
                    currentMenuAdapter = playerModeMenuAdapter;
                    mDrawerList.setAdapter(currentMenuAdapter);

                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, new HomeFragment()).commit();

                    mDrawerList.setAdapter(currentMenuAdapter);
                    mDrawerList.setItemChecked(0, true);
                    mDrawerList.setSelection(0);
                    setTitle(currentMenuTitles[0]);
                    mDrawerLayout.closeDrawer(mDrawerList);
                    break;
                default:
                    break;
            }
        } else if (currentMode == MANAGE_MODE) {
            switch (position) {
                case 0:
                    fragment = new SaveChangesFragment();
                    break;
                case 1:
                    fragment = new DuplicateItemFragment();
                    break;
                case 2:
                    fragment = new DiscardChangesFragment();
                    break;
                case 3:
                    fragment = new DeleteItemFragment();
                    break;
                case 4:
//                    fragment = new ChangeCharacterFragment();
                    currentMode = PLAYER_MODE;
                    currentMenuTitles = playerModeMenuTitles;
                    currentMenuAdapter = playerModeMenuAdapter;
                    mDrawerList.setAdapter(currentMenuAdapter);

                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, new HomeFragment()).commit();

                    mDrawerList.setAdapter(currentMenuAdapter);
                    mDrawerList.setItemChecked(0, true);
                    mDrawerList.setSelection(0);
                    setTitle(currentMenuTitles[0]);
                    mDrawerLayout.closeDrawer(mDrawerList);
                    break;
                default:
                    break;
            }
        }
        if (fragment != null) {
//            Intent intent = new Intent("com.herd.ddnext.TEST");
//            startActivity(intent);
//            Intent intent = new Intent(this, fragment.getClass());
//            startActivity(intent);
            fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(currentMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void onBackPressed()
    {
        if (fragmentStack.size() == 1) {
            super.onBackPressed();
        } else {
            fragmentStack.remove(fragmentStack.size() - 1); //remove the last fragment from the array list
            positionStack.remove(positionStack.size() - 1); //remove the last int from the array list

            Fragment fragment = fragmentStack.get(fragmentStack.size() - 1); //get the new last fragment
            int position = positionStack.get(positionStack.size() - 1); //get the new last ing

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(currentMenuTitles[position]); //TODO - if this doesn't work use if statement on currentMode
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}