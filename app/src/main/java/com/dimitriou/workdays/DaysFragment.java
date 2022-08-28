package com.dimitriou.workdays;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dimitriou.workdays.AdapterViewModel.DaysAdapter;
import com.dimitriou.workdays.AdapterViewModel.ViewModel;
import com.dimitriou.workdays.Database.Repository;

public class DaysFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_days, container, false);
        requireActivity().setTitle(R.string.calendar);

        Repository repository = new Repository(requireActivity().getApplication());

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);

        final DaysAdapter daysAdapter = new DaysAdapter();
        recyclerView.setAdapter(daysAdapter);

        ViewModel viewModel = new ViewModelProvider(this,
                (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(this.requireActivity().getApplication())).get(ViewModel.class);
        viewModel.getAllDays().observe(requireActivity(), daysAdapter::submitList);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Repository repository = new Repository(requireActivity().getApplication());
                repository.DaysDelete(daysAdapter.getDaysAt(viewHolder.getAdapterPosition()));
                Toast.makeText(requireContext(),R.string.deleted,Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                daysAdapter.getDaysAt(viewHolder.getAdapterPosition());
                if (daysAdapter.getDaysAt(viewHolder.getAdapterPosition()).getPaid().equals("No")) {
                    repository.makePayYes(daysAdapter.getDaysAt(viewHolder.getAdapterPosition()).getId());
                }else if (daysAdapter.getDaysAt(viewHolder.getAdapterPosition()).getPaid().equals("Yes")){
                    repository.makePayNo(daysAdapter.getDaysAt(viewHolder.getAdapterPosition()).getId());
                }
            }
        }).attachToRecyclerView(recyclerView);
        return view;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem item = menu.findItem(R.id.app_bar_calendar);
        item.setVisible(false);
        MenuItem settings = menu.findItem(R.id.app_bar_settings);
        settings.setVisible(false);
        MenuItem money = menu.findItem(R.id.app_bar_money);
        money.setVisible(true);
        money.setOnMenuItemClickListener(item12 -> {
            BottomSheet bottomSheet = new BottomSheet();
            bottomSheet.show(requireActivity().getSupportFragmentManager(),"BottomSheet");
            return true;
        });
        MenuItem back = menu.findItem(R.id.app_bar_back);
        back.setVisible(true);
        back.setOnMenuItemClickListener(item1 -> {
            requireActivity().getSupportFragmentManager().popBackStack();
            return false;
        });
        super.onPrepareOptionsMenu(menu);
    }
}