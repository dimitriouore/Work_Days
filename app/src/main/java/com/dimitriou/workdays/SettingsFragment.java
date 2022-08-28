package com.dimitriou.workdays;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.dimitriou.workdays.Database.Repository;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        setHasOptionsMenu(true);
        requireActivity().setTitle(R.string.settings);

        androidx.preference.EditTextPreference editTextPreference = getPreferenceManager().findPreference("wage");
        assert editTextPreference != null;
        editTextPreference.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL));

        Preference wage = findPreference("wage");
        if (wage != null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireActivity());
            String value = sharedPreferences.getString("wage", "30");
            if(value.equals("")){
                value = "30";
            }
            wage.setSummary(getString(R.string.current_wage) + " " + value + "€");
        }

        Preference delete = findPreference("delete_data");
        assert delete != null;
        delete.setOnPreferenceClickListener(preference -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            builder.setTitle(R.string.delete_all)
                    .setIcon(R.drawable.warning)
                    .setPositiveButton(R.string.delete, (dialog, which) -> {
                        Repository repository = new Repository(requireActivity().getApplication());
                        repository.DaysDeleteAll();
                        Toast.makeText(requireContext(), R.string.deleted, Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton(R.string.cancel, (dialog, which) -> {

                    }).show();
            return true;
        });

        Preference github = findPreference("github");
        assert github != null;
        github.setOnPreferenceClickListener(preference -> {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(requireContext(), Uri.parse(getString(R.string.github_url)));
            return true;
        });

        Preference version = findPreference("info");
        if (version != null) {
            version.setSummary(BuildConfig.VERSION_NAME);
        }
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem item = menu.findItem(R.id.app_bar_calendar);
        item.setVisible(false);
        MenuItem settings = menu.findItem(R.id.app_bar_settings);
        settings.setVisible(false);
        MenuItem back = menu.findItem(R.id.app_bar_back);
        back.setVisible(true);
        back.setOnMenuItemClickListener(item1 -> {
            requireActivity().getSupportFragmentManager().popBackStack();
            return false;
        });
        super.onPrepareOptionsMenu(menu);
    }

}