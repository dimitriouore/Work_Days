package com.dimitriou.workdays;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.dimitriou.workdays.Database.Days;
import com.dimitriou.workdays.Database.Repository;
import com.google.android.material.chip.Chip;

public class MainFragment extends Fragment {

    String type = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        requireActivity().setTitle(R.string.app_name);

        DatePicker datePicker = view.findViewById(R.id.date);

        type = getString(R.string.error);

        Spinner spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(), R.array.list, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = spinner.getSelectedItem().toString();
                if (type.equals(getString(R.string.half))) {
                    type = "Half";
                }
                if (type.equals(getString(R.string.Double))) {
                    type = "Double";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Chip save = view.findViewById(R.id.chip_action);
        save.setOnClickListener(v -> {
            if(type.equals(getString(R.string.error))){
                Toast.makeText(requireContext(),R.string.error,Toast.LENGTH_SHORT).show();
            }else {
                Days days = new Days(String.valueOf(datePicker.getDayOfMonth()), String.valueOf(datePicker.getMonth() + 1),
                        String.valueOf(datePicker.getYear()), type, "No");
                Repository repository = new Repository(requireActivity().getApplication());
                repository.DaysInsert(days);
                Toast.makeText(requireContext(),R.string.saved,Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}