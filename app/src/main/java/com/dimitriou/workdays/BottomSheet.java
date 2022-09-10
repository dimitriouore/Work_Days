package com.dimitriou.workdays;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import com.dimitriou.workdays.Database.Repository;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.elevation.SurfaceColors;

public class BottomSheet extends BottomSheetDialogFragment {

    String wage;
    private final Handler mainHandler = new Handler();
    double half,twice;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_layout, container, false);

        TextView daysWorked = view.findViewById(R.id.daysCount);
        TextView daysPaid = view.findViewById(R.id.daysPaid);
        TextView moneyPay = view.findViewById(R.id.moneyPay);
        TextView moneyOwn = view.findViewById(R.id.moneyOwn);

        Spinner spinner = view.findViewById(R.id.spinner_year);
        LinearLayout layout = view.findViewById(R.id.january);
        TextView january1 = view.findViewById(R.id.january1);
        TextView january2 = view.findViewById(R.id.january2);
        TextView january3 = view.findViewById(R.id.january3);

        LinearLayout february = view.findViewById(R.id.february);
        TextView february1 = view.findViewById(R.id.february1);
        TextView february2 = view.findViewById(R.id.february2);
        TextView february3 = view.findViewById(R.id.february3);

        LinearLayout march = view.findViewById(R.id.march);
        TextView march1 = view.findViewById(R.id.march1);
        TextView march2 = view.findViewById(R.id.march2);
        TextView march3 = view.findViewById(R.id.march3);

        LinearLayout april = view.findViewById(R.id.april);
        TextView april1 = view.findViewById(R.id.april1);
        TextView april2 = view.findViewById(R.id.april2);
        TextView april3 = view.findViewById(R.id.april3);

        LinearLayout may = view.findViewById(R.id.may);
        TextView may1 = view.findViewById(R.id.may1);
        TextView may2 = view.findViewById(R.id.may2);
        TextView may3 = view.findViewById(R.id.may3);

        LinearLayout june = view.findViewById(R.id.june);
        TextView june1 = view.findViewById(R.id.june1);
        TextView june2 = view.findViewById(R.id.june2);
        TextView june3 = view.findViewById(R.id.june3);

        LinearLayout july = view.findViewById(R.id.july);
        TextView july1 = view.findViewById(R.id.july1);
        TextView july2 = view.findViewById(R.id.july2);
        TextView july3 = view.findViewById(R.id.july3);

        LinearLayout august = view.findViewById(R.id.august);
        TextView august1 = view.findViewById(R.id.august1);
        TextView august2 = view.findViewById(R.id.august2);
        TextView august3 = view.findViewById(R.id.august3);

        LinearLayout september = view.findViewById(R.id.september);
        TextView september1 = view.findViewById(R.id.september1);
        TextView september2 = view.findViewById(R.id.september2);
        TextView september3 = view.findViewById(R.id.september3);

        LinearLayout october = view.findViewById(R.id.october);
        TextView october1 = view.findViewById(R.id.october1);
        TextView october2 = view.findViewById(R.id.october2);
        TextView october3 = view.findViewById(R.id.october3);

        LinearLayout november = view.findViewById(R.id.november);
        TextView november1 = view.findViewById(R.id.november1);
        TextView november2 = view.findViewById(R.id.november2);
        TextView november3 = view.findViewById(R.id.november3);

        LinearLayout december = view.findViewById(R.id.december);
        TextView december1 = view.findViewById(R.id.december1);
        TextView december2 = view.findViewById(R.id.december2);
        TextView december3 = view.findViewById(R.id.december3);

        OverallDates overallDates = new OverallDates(daysWorked, daysPaid);
        new Thread(overallDates).start();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireActivity());
        wage = sharedPreferences.getString("wage", "30");
        if (wage.equals("")) {
            wage = "30";
        }

        try {

            half = (Double.parseDouble(wage) / 2);
            twice = (Double.parseDouble(wage) * 2);

            FillDates fillDates = new FillDates(wage, half, twice, moneyPay, moneyOwn, spinner);
            new Thread(fillDates).start();

        } catch (Exception ignored) {
            TextView title = view.findViewById(R.id.total_headline);
            title.setVisibility(View.GONE);

            TextView slash = view.findViewById(R.id.slash);
            slash.setVisibility(View.GONE);

            TextView euro = view.findViewById(R.id.euro);
            euro.setVisibility(View.GONE);

            moneyPay.setVisibility(View.GONE);
            moneyOwn.setText(R.string.money_error);
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                layout.setVisibility(View.GONE);
                february.setVisibility(View.GONE);
                march.setVisibility(View.GONE);
                april.setVisibility(View.GONE);
                may.setVisibility(View.GONE);
                june.setVisibility(View.GONE);
                july.setVisibility(View.GONE);
                august.setVisibility(View.GONE);
                september.setVisibility(View.GONE);
                october.setVisibility(View.GONE);
                november.setVisibility(View.GONE);
                december.setVisibility(View.GONE);

                OverallMonthly overallMonthly = new OverallMonthly(spinner,position, layout, february, april, march, may, june, july, august, september, october, november, december,
                        january1, february1, april1, march1, may1, june1, july1, august1, september1, october1, november1, december1,
                        january2, february2, april2, march2, may2, june2, july2, august2, september2, october2, november2, december2,
                        january3, february3, april3, march3, may3, june3, july3, august3, september3, october3, november3, december3);

                new Thread(overallMonthly).start();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    class FillDates implements Runnable {
        String wage;
        double half;
        double twice;
        TextView moneyPay;
        TextView moneyOwn;
        Spinner spinner;

        FillDates(String wage, Double half, Double twice, TextView moneyPay, TextView moneyOwn, Spinner spinner) {
            this.wage = wage;
            this.half = half;
            this.twice = twice;
            this.moneyOwn = moneyOwn;
            this.moneyPay = moneyPay;
            this.spinner = spinner;
        }

        @Override
        public void run() {
            Repository repository = new Repository(requireActivity().getApplication());
            String pay = String.valueOf((half * repository.getHalfPayDays()) +
                    (twice * repository.getDoublePayDays()) + (Double.parseDouble(wage) * repository.getRestPayDays()));
            String work = String.valueOf(((half * repository.getHalfWorkDays()) +
                    (twice * repository.getDoubleWorkDays()) + (Double.parseDouble(wage) * repository.getRestWorkDays())));
            String[] years = repository.getYears();
            mainHandler.post(() -> {
                moneyPay.setText(pay);

                moneyOwn.setText(work);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_item_sheet, years);
                adapter.setDropDownViewResource(R.layout.spinner_dropdown);
                spinner.setAdapter(adapter);
            });
        }
    }

    class OverallDates implements Runnable {
        TextView daysWorked;
        TextView daysPaid;

        OverallDates(TextView daysWorked, TextView daysPaid) {
            this.daysWorked = daysWorked;
            this.daysPaid = daysPaid;
        }

        @Override
        public void run() {
            Repository repository = new Repository(requireActivity().getApplication());
            String daysWorkedText = String.valueOf(repository.getDaysWorked());
            String daysPaidText = String.valueOf(repository.getDaysPaid());
            mainHandler.post(() -> {
                daysWorked.setText(daysWorkedText);
                daysPaid.setText(daysPaidText);
            });
        }
    }

    class OverallMonthly implements Runnable {
        Spinner spinner;
        int position;
        LinearLayout layout, february, april, march, may, june, july, august, september, october, november, december;
        TextView january1, february1, april1, march1, may1, june1, july1, august1, september1, october1, november1, december1;
        TextView january2, february2, april2, march2, may2, june2, july2, august2, september2, october2, november2, december2;
        TextView january3, february3, april3, march3, may3, june3, july3, august3, september3, october3, november3, december3;

        OverallMonthly(Spinner spinner, int position,
                       LinearLayout layout, LinearLayout february, LinearLayout april, LinearLayout march, LinearLayout may, LinearLayout june,
                       LinearLayout july, LinearLayout august, LinearLayout september, LinearLayout october, LinearLayout november, LinearLayout december,
                       TextView january1, TextView february1, TextView april1, TextView march1, TextView may1, TextView june1,
                       TextView july1, TextView august1, TextView september1, TextView october1, TextView november1, TextView december1,
                       TextView january2, TextView february2, TextView april2, TextView march2, TextView may2, TextView june2,
                       TextView july2, TextView august2, TextView september2, TextView october2, TextView november2, TextView december2,
                       TextView january3, TextView february3, TextView april3, TextView march3, TextView may3, TextView june3,
                       TextView july3, TextView august3, TextView september3, TextView october3, TextView november3, TextView december3) {
            this.spinner = spinner;
            this.position = position;
            this.layout = layout;
            this.february = february;
            this.april = april;
            this.march = march;
            this.may = may;
            this.june = june;
            this.july = july;
            this.august = august;
            this.september = september;
            this.october = october;
            this.november = november;
            this.december = december;

            this.january1 = january1;
            this.february1 = february1;
            this.april1 = april1;
            this.march1 = march1;
            this.may1 = may1;
            this.june1 = june1;
            this.july1 = july1;
            this.august1 = august1;
            this.september1 = september1;
            this.october1 = october1;
            this.november1 = november1;
            this.december1 = december1;

            this.january2 = january2;
            this.february2 = february2;
            this.april2 = april2;
            this.march2 = march2;
            this.may2 = may2;
            this.june2 = june2;
            this.july2 = july2;
            this.august2 = august2;
            this.september2 = september2;
            this.october2 = october2;
            this.november2 = november2;
            this.december2 = december2;

            this.january3 = january3;
            this.february3 = february3;
            this.april3 = april3;
            this.march3 = march3;
            this.may3 = may3;
            this.june3 = june3;
            this.july3 = july3;
            this.august3 = august3;
            this.september3 = september3;
            this.october3 = october3;
            this.november3 = november3;
            this.december3 = december3;
        }

        @Override
        public void run() {
            Repository repository = new Repository(requireActivity().getApplication());
            int year = Integer.parseInt(spinner.getItemAtPosition(position).toString());
            String[] months = repository.getMonths(year);
            for (int i = 0; i < months.length; i++) {
                int month = Integer.parseInt(months[i]);
                switch (month) {
                    case 1:
                        String paydays = String.valueOf(repository.getAllMonthPayDays(1, year));
                        String workdays = String.valueOf(repository.getAllMonthDays(1, year));
                        String money = String.valueOf((half * repository.getAllMonthHalfPayDays(1, year)) +
                                (twice * repository.getAllMonthDoublePayDays(1, year)) + (Double.parseDouble(wage) * repository.getAllMonthRestPayDays(1, year)));
                        mainHandler.post(() -> {
                            layout.setVisibility(View.VISIBLE);
                            january1.setText(paydays);
                            january2.setText(workdays);
                            january3.setText(money);
                        });
                        break;
                    case 2:
                        paydays = String.valueOf(repository.getAllMonthPayDays(2, year));
                        workdays = String.valueOf(repository.getAllMonthDays(2, year));
                        money = String.valueOf((half * repository.getAllMonthHalfPayDays(2, year)) +
                                (twice * repository.getAllMonthDoublePayDays(2, year)) + (Double.parseDouble(wage) * repository.getAllMonthRestPayDays(2, year)));
                        mainHandler.post(() -> {
                            february.setVisibility(View.VISIBLE);
                            february1.setText(paydays);
                            february2.setText(workdays);
                            february3.setText(money);
                        });
                        break;
                    case 3:
                        paydays = String.valueOf(repository.getAllMonthPayDays(3, year));
                        workdays = String.valueOf(repository.getAllMonthDays(3, year));
                        money = String.valueOf((half * repository.getAllMonthHalfPayDays(3, year)) +
                                (twice * repository.getAllMonthDoublePayDays(3, year)) + (Double.parseDouble(wage) * repository.getAllMonthRestPayDays(3, year)));
                        mainHandler.post(() -> {
                            march.setVisibility(View.VISIBLE);
                            march1.setText(paydays);
                            march2.setText(workdays);
                            march3.setText(money);
                        });
                        break;
                    case 4:
                        paydays = String.valueOf(repository.getAllMonthPayDays(4, year));
                        workdays = String.valueOf(repository.getAllMonthDays(4, year));
                        money = String.valueOf((half * repository.getAllMonthHalfPayDays(4, year)) +
                                (twice * repository.getAllMonthDoublePayDays(4, year)) + (Double.parseDouble(wage) * repository.getAllMonthRestPayDays(4, year)));
                        mainHandler.post(() -> {
                            april.setVisibility(View.VISIBLE);
                            april1.setText(paydays);
                            april2.setText(workdays);
                            april3.setText(money);
                        });
                        break;
                    case 5:
                        paydays = String.valueOf(repository.getAllMonthPayDays(5, year));
                        workdays = String.valueOf(repository.getAllMonthDays(5, year));
                        money = String.valueOf((half * repository.getAllMonthHalfPayDays(5, year)) +
                                (twice * repository.getAllMonthDoublePayDays(5, year)) + (Double.parseDouble(wage) * repository.getAllMonthRestPayDays(5, year)));
                        mainHandler.post(() -> {
                            may.setVisibility(View.VISIBLE);
                            may1.setText(paydays);
                            may2.setText(workdays);
                            may3.setText(money);
                        });
                        break;
                    case 6:
                        paydays = String.valueOf(repository.getAllMonthPayDays(6, year));
                        workdays = String.valueOf(repository.getAllMonthDays(6, year));
                        money = String.valueOf((half * repository.getAllMonthHalfPayDays(6, year)) +
                                (twice * repository.getAllMonthDoublePayDays(6, year)) + (Double.parseDouble(wage) * repository.getAllMonthRestPayDays(6, year)));
                        mainHandler.post(() -> {
                            june.setVisibility(View.VISIBLE);
                            june1.setText(paydays);
                            june2.setText(workdays);
                            june3.setText(money);
                        });
                        break;
                    case 7:
                        paydays = String.valueOf(repository.getAllMonthPayDays(7, year));
                        workdays = String.valueOf(repository.getAllMonthDays(7, year));
                        money = String.valueOf((half * repository.getAllMonthHalfPayDays(7, year)) +
                                (twice * repository.getAllMonthDoublePayDays(7, year)) + (Double.parseDouble(wage) * repository.getAllMonthRestPayDays(7, year)));
                        mainHandler.post(() -> {
                            july.setVisibility(View.VISIBLE);
                            july1.setText(paydays);
                            july2.setText(workdays);
                            july3.setText(money);
                        });
                        break;
                    case 8:
                        paydays = String.valueOf(repository.getAllMonthPayDays(8, year));
                        workdays = String.valueOf(repository.getAllMonthDays(8, year));
                        money = String.valueOf((half * repository.getAllMonthHalfPayDays(8, year)) +
                                (twice * repository.getAllMonthDoublePayDays(8, year)) + (Double.parseDouble(wage) * repository.getAllMonthRestPayDays(8, year)));
                        mainHandler.post(() -> {
                            august.setVisibility(View.VISIBLE);
                            august1.setText(paydays);
                            august2.setText(workdays);
                            august3.setText(money);
                        });
                        break;
                    case 9:
                        paydays = String.valueOf(repository.getAllMonthPayDays(9, year));
                        workdays = String.valueOf(repository.getAllMonthDays(9, year));
                        money = String.valueOf((half * repository.getAllMonthHalfPayDays(9, year)) +
                                (twice * repository.getAllMonthDoublePayDays(9, year)) + (Double.parseDouble(wage) * repository.getAllMonthRestPayDays(9, year)));
                        mainHandler.post(() -> {
                            september.setVisibility(View.VISIBLE);
                            september1.setText(paydays);
                            september2.setText(workdays);
                            september3.setText(money);
                        });
                        break;
                    case 10:
                        paydays = String.valueOf(repository.getAllMonthPayDays(10, year));
                        workdays = String.valueOf(repository.getAllMonthDays(10, year));
                        money = String.valueOf((half * repository.getAllMonthHalfPayDays(10, year)) +
                                (twice * repository.getAllMonthDoublePayDays(10, year)) + (Double.parseDouble(wage) * repository.getAllMonthRestPayDays(10, year)));
                        mainHandler.post(() -> {
                            october.setVisibility(View.VISIBLE);
                            october1.setText(paydays);
                            october2.setText(workdays);
                            october3.setText(money);
                        });
                        break;
                    case 11:
                        paydays = String.valueOf(repository.getAllMonthPayDays(11, year));
                        workdays = String.valueOf(repository.getAllMonthDays(11, year));
                        money = String.valueOf((half * repository.getAllMonthHalfPayDays(11, year)) +
                                (twice * repository.getAllMonthDoublePayDays(11, year)) + (Double.parseDouble(wage) * repository.getAllMonthRestPayDays(11, year)));
                        mainHandler.post(() -> {
                            november.setVisibility(View.VISIBLE);
                            november1.setText(paydays);
                            november2.setText(workdays);
                            november3.setText(money);
                        });
                        break;
                    case 12:
                        paydays = String.valueOf(repository.getAllMonthPayDays(12, year));
                        workdays = String.valueOf(repository.getAllMonthDays(12, year));
                        money = String.valueOf((half * repository.getAllMonthHalfPayDays(12, year)) +
                                (twice * repository.getAllMonthDoublePayDays(12, year)) + (Double.parseDouble(wage) * repository.getAllMonthRestPayDays(12, year)));
                        mainHandler.post(() -> {
                            december.setVisibility(View.VISIBLE);
                            december1.setText(paydays);
                            december2.setText(workdays);
                            december3.setText(money);
                        });
                        break;
                }
            }


        }
    }

}