package com.dimitriou.workdays.AdapterViewModel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.dimitriou.workdays.Database.Days;
import com.dimitriou.workdays.Database.Repository;
import com.dimitriou.workdays.MainActivity;
import com.dimitriou.workdays.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Objects;

public class DaysAdapter extends ListAdapter<Days, DaysAdapter.DaysHolder> {

    Context context;

    public DaysAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    private static final DiffUtil.ItemCallback<Days> DIFF_CALLBACK = new DiffUtil.ItemCallback<Days>() {
        @Override
        public boolean areItemsTheSame(@NonNull Days oldItem, @NonNull Days newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Days oldItem, @NonNull Days newItem) {
            return oldItem.getDay().equals(newItem.getDay()) &&
                    oldItem.getMonth().equals(newItem.getMonth()) &&
                    oldItem.getYear().equals(newItem.getMonth()) &&
                    oldItem.getType().equals(newItem.getType()) &&
                    oldItem.getPaid().equals(newItem.getPaid()) &&
                    oldItem.getNotes().equals(newItem.getNotes()) &&
                    oldItem.getTime().equals(newItem.getTime());
        }
    };

    @NonNull
    @Override
    public DaysHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);
        return new DaysHolder(itemView);
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull DaysHolder holder, int position) {
        Days currentDay = getItem(position);
        String fullDate = currentDay.getDay() + "/" + currentDay.getMonth() + "/" + currentDay.getYear();
        holder.date.setText(fullDate);
        holder.editText.setText(currentDay.getNotes());
        String type = currentDay.getType();
        if (type.equals("Double")) {
            holder.type.setText(R.string.Double);
        } else if (type.equals("Half")) {
            holder.type.setText(R.string.half);
        } else {
            holder.type.setText(currentDay.getType());
        }
        String pay = currentDay.getPaid();
        if (pay.equals("Yes")) {
            holder.pay.setVisibility(View.VISIBLE);
        }
        if (pay.equals("No")) {
            holder.pay.setVisibility(View.GONE);
        }

        String time = currentDay.getTime();
        if (!time.equals("")) {
            String[] array = time.split("-");
            holder.startTime.setText(array[0]);
            holder.endTime.setText(array[1]);
        }

        holder.id.setText(String.valueOf(currentDay.getId()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.inputLayout.getVisibility() == View.VISIBLE) {
                    holder.button.setVisibility(View.GONE);
                    holder.inputLayout.setVisibility(View.GONE);
                    holder.timeLayout.setVisibility(View.GONE);
                } else {
                    holder.button.setVisibility(View.VISIBLE);
                    holder.inputLayout.setVisibility(View.VISIBLE);
                    holder.timeLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .build();
                timePicker.show(((MainActivity) context).getSupportFragmentManager(), "timePicker");
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
                        holder.startTime.setText(hour + ":" + minute);
                    }
                });
                timePicker.addOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        holder.startTime.setText(R.string.time_start);
                    }
                });
            }
        });

        holder.endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .build();
                timePicker.show(((MainActivity) context).getSupportFragmentManager(), "timePicker");
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
                        holder.endTime.setText(hour + ":" + minute);
                    }
                });
                timePicker.addOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        holder.endTime.setText(R.string.time_end);
                    }
                });
            }
        });

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.button.getText().equals(("Edit")) || holder.button.getText().equals(("Επεξεργασία"))) {
                    holder.button.setText(R.string.save);
                    holder.inputLayout.setEnabled(true);
                    holder.startTime.setEnabled(true);
                    holder.endTime.setEnabled(true);
                } else {
                    String date = holder.date.getText().toString();
                    String[] array = date.split("/");

                    String pay;
                    if (holder.pay.getVisibility() == View.VISIBLE) {
                        pay = "Yes";
                    } else {
                        pay = "No";
                    }

                    Repository repository = new Repository(((MainActivity) context).getApplication());
                    if (holder.startTime.getText() == context.getText(R.string.time_start) && holder.endTime.getText() == context.getText(R.string.time_end)) {
                        Days days = new Days(array[0], array[1], array[2], holder.type.getText().toString(), pay,
                                Objects.requireNonNull(holder.editText.getText()).toString(), "");
                        days.setId(Integer.parseInt(holder.id.getText().toString()));
                        repository.Update(days);
                        Toast.makeText(context, R.string.updated, Toast.LENGTH_SHORT).show();
                        holder.button.setText(R.string.edit);
                        holder.inputLayout.setEnabled(false);
                        holder.startTime.setEnabled(false);
                        holder.endTime.setEnabled(false);
                    } else if (holder.startTime.getText() == context.getText(R.string.time_start) || holder.endTime.getText() == context.getText(R.string.time_end)) {
                        Toast.makeText(context, context.getText(R.string.time_error), Toast.LENGTH_SHORT).show();
                    } else {
                        Days days = new Days(array[0], array[1], array[2], holder.type.getText().toString(), pay,
                                Objects.requireNonNull(holder.editText.getText()).toString(), holder.startTime.getText() + "-" + holder.endTime.getText());
                        days.setId(Integer.parseInt(holder.id.getText().toString()));
                        repository.Update(days);
                        Toast.makeText(context, R.string.updated, Toast.LENGTH_SHORT).show();
                        holder.button.setText(R.string.edit);
                        holder.inputLayout.setEnabled(false);
                        holder.startTime.setEnabled(false);
                        holder.endTime.setEnabled(false);
                    }
                }
            }
        });
    }

    public Days getDaysAt(int position) {
        return getItem(position);
    }

    static class DaysHolder extends RecyclerView.ViewHolder {
        private final TextView date;
        private final TextView type;
        private final TextView pay;
        private final TextView id;
        private final CardView cardView;
        private final Button button;
        private final Button startTime;
        private final Button endTime;
        private final TextInputLayout inputLayout;
        private final TextInputEditText editText;
        private final LinearLayout timeLayout;

        public DaysHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.datePick);
            type = itemView.findViewById(R.id.shiftShow);
            pay = itemView.findViewById(R.id.paid);
            id = itemView.findViewById(R.id.id);
            cardView = itemView.findViewById(R.id.recycler_view);
            button = itemView.findViewById(R.id.save_button);
            startTime = itemView.findViewById(R.id.time_start);
            endTime = itemView.findViewById(R.id.time_end);
            inputLayout = itemView.findViewById(R.id.edit_layout);
            editText = itemView.findViewById(R.id.edit_text);
            timeLayout = itemView.findViewById(R.id.time_layout);
        }
    }
}
