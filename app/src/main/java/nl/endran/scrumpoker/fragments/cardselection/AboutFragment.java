/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.scrumpoker.fragments.cardselection;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.psdev.licensesdialog.LicensesDialog;
import nl.endran.scrumpoker.R;

public class AboutFragment extends Fragment {

    @Bind(R.id.textViewVersion)
    TextView textViewVersion;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this, rootView);

        textViewVersion.setText(getVersion(rootView.getContext()));

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private String getVersion(final Context context) {
        try {
            final PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            Resources resources = context.getResources();
            return String.format(resources.getString(R.string.version_info), packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
        }
        return context.getString(R.string.unknown);
    }

    @OnClick(R.id.buttonLicenses)
    public void onLicensesClicked(View view) {
        LicensesDialog licensesDialog = new LicensesDialog.Builder(view.getContext()).setNotices(R.raw.notices).build();
        licensesDialog.show();
    }
}
