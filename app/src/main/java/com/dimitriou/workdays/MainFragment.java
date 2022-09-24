package com.dimitriou.workdays;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.dimitriou.workdays.Database.Days;
import com.dimitriou.workdays.Database.Repository;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

public class MainFragment extends Fragment {

    String type = "";
    Days days;

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

        Chip chipNotes = view.findViewById(R.id.chip_notes);
        Chip chipTime = view.findViewById(R.id.chip_time);
        TextInputLayout notesLayout = view.findViewById(R.id.notes_layout);
        TextInputEditText editText = view.findViewById(R.id.edit_notes);
        Button startTime = view.findViewById(R.id.time_start);
        Button endTime = view.findViewById(R.id.time_end);
        LinearLayout timeLayout = view.findViewById(R.id.time_layout);

        chipNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chipNotes.isChecked()) {
                    notesLayout.setVisibility(View.VISIBLE);
                } else {
                    notesLayout.setVisibility(View.GONE);
                    editText.setText("");
                }
            }
        });
        chipTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chipTime.isChecked()) {
                    timeLayout.setVisibility(View.VISIBLE);
                } else {
                    timeLayout.setVisibility(View.GONE);
                    startTime.setText(R.string.time_start);
                    endTime.setText(R.string.time_end);
                }
            }
        });

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .build();
                timePicker.show(requireActivity().getSupportFragmentManager(), "timePicker");
                timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View v) {
                        String minute = String.valueOf(timePicker.getMinute());
                        String hour = String.valueOf(timePicker.getHour());
                        if (minute.length() == 1) {
                            minute = "0" + minute;
                        }
                        if (hour.length() == 1) {
                            hour = "0" + hour;
                        }
                        startTime.setText(hour + ":" + minute);
                    }
                });
            }
        });
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .build();
                timePicker.show(requireActivity().getSupportFragmentManager(), "timePicker");
                timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View v) {
                        String minute = String.valueOf(timePicker.getMinute());
                        String hour = String.valueOf(timePicker.getHour());
                        if (minute.length() == 1) {
                            minute = "0" + minute;
                        }
                        if (hour.length() == 1) {
                            hour = "0" + hour;
                        }
                        endTime.setText(hour + ":" + minute);
                    }
                });
            }
        });

        TextView reset = view.findViewById(R.id.reset_button);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime.setText(R.string.time_start);
                endTime.setText(R.string.time_end);
            }
        });

        Button save = view.findViewById(R.id.button_action);
        save.setOnClickListener(v -> {
            if (type.equals(getString(R.string.error))) {
                Toast.makeText(requireContext(), R.string.error, Toast.LENGTH_SHORT).show();
            } else if (startTime.getText() == getText(R.string.time_start) && chipTime.isChecked() || endTime.getText() == getText(R.string.time_end) && chipTime.isChecked()) {
                Toast.makeText(requireContext(), getText(R.string.time_error), Toast.LENGTH_SHORT).show();
            } else {
                if (chipNotes.isChecked() && chipTime.isChecked()) {
                    days = new Days(String.valueOf(datePicker.getDayOfMonth()), String.valueOf(datePicker.getMonth() + 1),
                            String.valueOf(datePicker.getYear()), type, "No", editText.getText().toString().trim(), startTime.getText().toString() + "-" + endTime.getText().toString());
                } else if (chipNotes.isChecked()) {
                    days = new Days(String.valueOf(datePicker.getDayOfMonth()), String.valueOf(datePicker.getMonth() + 1),
                            String.valueOf(datePicker.getYear()), type, "No", editText.getText().toString().trim(), "");
                } else if (chipTime.isChecked()) {
                    days = new Days(String.valueOf(datePicker.getDayOfMonth()), String.valueOf(datePicker.getMonth() + 1),
                            String.valueOf(datePicker.getYear()), type, "No", "", startTime.getText() + "-" + endTime.getText());
                } else {
                    days = new Days(String.valueOf(datePicker.getDayOfMonth()), String.valueOf(datePicker.getMonth() + 1),
                            String.valueOf(datePicker.getYear()), type, "No", "", "");
                }
                Repository repository = new Repository(requireActivity().getApplication());
                repository.DaysInsert(days);
                Toast.makeText(requireContext(), R.string.saved, Toast.LENGTH_SHORT).show();
                chipNotes.setChecked(false);
                chipTime.setChecked(false);
                startTime.setText(R.string.time_start);
                endTime.setText(R.string.time_end);
                editText.setText("");
                notesLayout.setVisibility(View.GONE);
                timeLayout.setVisibility(View.GONE);
                InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

            }
        });
        return view;
    }
}