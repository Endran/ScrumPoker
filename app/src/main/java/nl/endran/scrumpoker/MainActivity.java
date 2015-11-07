/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import de.psdev.licensesdialog.LicensesDialog;
import nl.endran.scrumpoker.fragments.cardselection.CardDisplayFragment;
import nl.endran.scrumpoker.fragments.cardselection.CardSelection;
import nl.endran.scrumpoker.fragments.cardselection.CardSelectionFragment;
import nl.endran.scrumpoker.fragments.cardselection.CardValue;
import nl.endran.scrumpoker.fragments.cardselection.SelectionBackgroundFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends BaseActivity {

    private CardDisplayFragment cardDisplayFragment;
    private CardSelectionFragment cardSelectionFragment;
    private SelectionBackgroundFragment selectionBackgroundFragment;
    private DrawerLayout drawer;

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

        selectionBackgroundFragment = (SelectionBackgroundFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentSelectionBackground);
        cardSelectionFragment = (CardSelectionFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentCardSelection);
        cardDisplayFragment = (CardDisplayFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentCardDisplay);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem item) {
                return handleNavigationItemSelected(item);
            }
        });

        setCardsAndShow(CardValue.getStandard());

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void setCardsAndShow(final CardValue[] cardValues) {
        cardSelectionFragment.setCardValues(cardValues);
        showCardSelection();
    }

    private void showCardSelection() {
        cardDisplayFragment.hide();
        selectionBackgroundFragment.hide();
        cardSelectionFragment.show(new CardSelectionFragment.Listener() {
            @Override
            public void onCardSelected(final CardSelection cardSelection) {
                showSelectionBackgroundFragment(cardSelection);
            }
        });
    }

    private void showSelectionBackgroundFragment(final CardSelection cardSelection) {
        cardDisplayFragment.hide();
        cardSelectionFragment.hide();
        selectionBackgroundFragment.show(new SelectionBackgroundFragment.Listener() {
            @Override
            public void onShowCardClicked() {
                showCardDisplay(cardSelection);
            }
        });
    }

    private void showCardDisplay(final CardSelection cardSelection) {
        cardSelectionFragment.hide();
        selectionBackgroundFragment.hide();
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
            drawer.closeDrawer(GravityCompat.START);
        } else if (!cardSelectionFragment.isShowing()) {
            showCardSelection();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_licenses) {
            showLicensesDialog(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showLicensesDialog(final Context context) {
        LicensesDialog licensesDialog = new LicensesDialog.Builder(context).setNotices(R.raw.notices).build();
        licensesDialog.show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public boolean handleNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_standard) {
            setCardsAndShow(CardValue.getStandard());
        } else if (id == R.id.nav_fibonacci) {
            setCardsAndShow(CardValue.getFibonacci());
        } else if (id == R.id.nav_shirt) {
            setCardsAndShow(CardValue.getShirt());
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
