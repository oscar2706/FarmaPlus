package com.example.farmaplus;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toolbar;

public class Login extends Fragment implements View.OnClickListener {
    Spinner spinner_userType;

    public Login() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        spinner_userType = view.findViewById(R.id.spinner_UserType);
        view.findViewById(R.id.button_login).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        LogginHandler.setLoggedIn(getActivity(), true, LogginHandler.UserType.CLIENTE);
        NavController navController = Navigation.findNavController(getView());
        navController.navigate(R.id.action_login_to_principalCliente);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }
}