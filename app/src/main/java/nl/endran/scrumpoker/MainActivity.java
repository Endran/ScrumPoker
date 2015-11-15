/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import java.nio.charset.StandardCharsets;

import nl.endran.scrumpoker.fragments.cardselection.AboutFragment;
import nl.endran.scrumpoker.fragments.cardselection.CardDisplayFragment;
import nl.endran.scrumpoker.fragments.cardselection.CardSelection;
import nl.endran.scrumpoker.fragments.cardselection.CardSelectionFragment;
import nl.endran.scrumpoker.fragments.cardselection.DeckType;
import nl.endran.scrumpoker.fragments.cardselection.SelectionBackgroundFragment;
import nl.endran.scrumpoker.fragments.cardselection.SettingsFragment;
import nl.endran.scrumpoker.nearby.NearbyHelper;
import nl.endran.scrumpoker.nearby.PermissionCheckCallback;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends BaseActivity {

    private static final int REQUEST_RESOLVE_ERROR = 892374;

    public static final byte[] SHOWING_CARD = "ShowingCard".getBytes();
    public static final byte[] SELECTING_CARD = "SelectingCard".getBytes();
    public static final byte[] CARD_SELECTED = "CardSelected".getBytes();

    private CardDisplayFragment cardDisplayFragment;
    private CardSelectionFragment cardSelectionFragment;
    private SelectionBackgroundFragment quickSettingsFragment;
    private DrawerLayout drawer;
    private FragmentManager supportFragmentManager;
    private Preferences preferences;
    private NearbyHelper nearbyHelper;
    private byte[] currentMessage;

    @Override
    @CallSuper
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferences = new Preferences(getApplicationContext());

        supportFragmentManager = getSupportFragmentManager();

        quickSettingsFragment = (SelectionBackgroundFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentSelectionBackground);
        cardSelectionFragment = (CardSelectionFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentCardSelection);
        cardDisplayFragment = (CardDisplayFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentCardDisplay);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem item) {
                return handleNavigationItemSelected(item);
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        nearbyHelper = new NearbyHelper(getApplicationContext(), preferences);
        quickSettingsFragment.setNearbyHelper(nearbyHelper);

        DeckType standard = preferences.getDeckType();
        setCardsAndShow(standard);

        quickSettingsFragment.setPreferences(preferences);
    }

    private void setCardsAndShow(final DeckType deckType) {

        closeDrawer();
        resetMenuScreens();
        preferences.setDeckType(deckType);
        cardSelectionFragment.setCardValues(deckType.getValues());
        showCardSelection();
    }

    private void showCardSelection() {
        publishState(SELECTING_CARD);

        cardDisplayFragment.hide();
        quickSettingsFragment.hide();
        cardSelectionFragment.show(new CardSelectionFragment.Listener() {
            @Override
            public void onCardSelected(final CardSelection cardSelection) {
                showSelectionBackgroundFragment(cardSelection);
            }
        });
    }

    private void showSelectionBackgroundFragment(final CardSelection cardSelection) {
        publishState(CARD_SELECTED);

        cardDisplayFragment.hide();
        cardSelectionFragment.hide();
        quickSettingsFragment.show(new SelectionBackgroundFragment.Listener() {
            @Override
            public void onShowCardClicked() {
                showCardDisplay(cardSelection);
            }

            @Override
            public void onNearbyPermissionRequested() {
                requestedNearbyPermission();
            }
        });
    }

    private void publishState(final byte[] message) {
        if (nearbyHelper.isNearbyAllowed() && nearbyHelper.isConnected()) {
            nearbyHelper.unPublishAll();
            nearbyHelper.publishMessage(message);
        } else {
            currentMessage = message;
        }
    }

    private void requestedNearbyPermission() {
        nearbyHelper.requestPermission(new PermissionCheckCallback.Listener() {
            @Override
            public void onPermissionAllowed() {
                preferences.setNearbyAllowed(true);
            }

            @Override
            public void onPermissionNotAllowed(@Nullable final Status status) {
                preferences.setNearbyAllowed(false);
                if (status != null && status.hasResolution()) {
                    try {
                        status.startResolutionForResult(MainActivity.this,
                                REQUEST_RESOLVE_ERROR);
                    } catch (IntentSender.SendIntentException e) {
                        Log.e("NearBy", "SendIntentException", e);
                        Toast.makeText(getApplicationContext(), R.string.error_google_api, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), R.string.error_google_api, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showCardDisplay(final CardSelection cardSelection) {
        publishState(SHOWING_CARD);
        cardSelectionFragment.hide();
        quickSettingsFragment.hide();
        cardDisplayFragment.show(cardSelection);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected String getPageName() {
        return "MainActivity";
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
        } else if (supportFragmentManager.getBackStackEntryCount() > 0) {
            resetMenuScreens();
        } else if (!cardSelectionFragment.isShowing()) {
            showCardSelection();
        } else {
            super.onBackPressed();
        }
    }

    private void resetMenuScreens() {
        int backStackEntryCount = supportFragmentManager.getBackStackEntryCount();
        for (int i = 0; i < backStackEntryCount; i++) {
            supportFragmentManager.popBackStack();
        }
    }

    private void closeDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        closeDrawer();
    }

    public boolean handleNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_standard) {
            setCardsAndShow(DeckType.STANDARD);
        } else if (id == R.id.nav_fibonacci) {
            setCardsAndShow(DeckType.FIBONACCI);
        } else if (id == R.id.nav_shirt) {
            setCardsAndShow(DeckType.SHIRT);
        } else if (id == R.id.nav_share) {
            shareApp();
        } else if (id == R.id.nav_about) {
            showFragment(new AboutFragment());
        } else if (id == R.id.nav_settings) {
            SettingsFragment fragment = new SettingsFragment();
            fragment.setListener(new SettingsFragment.Listener() {
                @Override
                public void onNearbyPermissionRequested() {
                    requestedNearbyPermission();
                }
            });
            showFragment(fragment);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void showFragment(final Fragment fragment) {
        resetMenuScreens();
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.setCustomAnimations(R.anim.fade_in, 0, 0, R.anim.fade_out);
        transaction.replace(R.id.contentFrame, fragment);
        transaction.commit();
    }

    private void shareApp() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TITLE, getString(R.string.app_name));
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Hey check out this awesome Scrum Poker app at: https://play.google.com/store/apps/details?id=nl.endran.scrumpoker");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        nearbyHelper.start(new NearbyHelper.Listener() {
            @Override
            public void onReady() {
                if (currentMessage != null) {
                    nearbyHelper.publishMessage(currentMessage);

                    nearbyHelper.subscribe(new MessageListener() {
                        @Override
                        public void onFound(final Message message) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String string = new String(message.getContent(), StandardCharsets.UTF_8);
                                    Toast.makeText(MainActivity.this, "Received " + string, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        nearbyHelper.stop();
    }

    // This is called in response to a button tap in the Nearby permission dialog. TODO
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_RESOLVE_ERROR) {
            if (resultCode == RESULT_OK) {
                preferences.setNearbyAllowed(true);
            } else {
                preferences.setNearbyAllowed(false);
                Toast.makeText(this, R.string.please_allow_nearby, Toast.LENGTH_SHORT).show();
            }
            showCardSelection();
        }
    }
}
