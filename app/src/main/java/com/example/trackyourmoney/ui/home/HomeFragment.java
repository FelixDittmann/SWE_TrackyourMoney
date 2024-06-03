package com.example.trackyourmoney.ui.home;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.trackyourmoney.R;
import com.example.trackyourmoney.databinding.FragmentHomeBinding;
import com.trackyourmoney.java.AppDataBase;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView monthlyBudget = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), monthlyBudget::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}