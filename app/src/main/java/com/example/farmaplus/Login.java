package com.example.farmaplus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.farmaplus.client.Sucursal;
import com.example.farmaplus.client.User;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Login extends Fragment implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {
    Spinner spinner_userType;
    EditText txt_userName, txt_password;
    Button btn_registro;

    private GoogleApiClient googleApiClient;
    Geocoder geocoder;
    List<Address> direccion;
    LatLng latLng;


    DatabaseReference reference;
    long maxId = 0;


    public Login() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
        
      //  insertaRepertidor();
    }

    private void insertaRepertidor() {
        Repartidor repartidor = new Repartidor();
        User user = new User();
        FirebaseDatabase database =  FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("REPARTIDOR");
        DatabaseReference reference2 = database.getReference("USER");

        repartidor.setFechaRegistro("21/11/2020");
        repartidor.setNombre("Armando");
        repartidor.setApellidos("Lopez Gomez");
        repartidor.setPassword("1234");
        repartidor.setUsuario("arm");
        reference.push().setValue(repartidor);

        user.setCorreo("arm");
        user.setPassword("1234");
        reference2.push().setValue(user);

        repartidor.setFechaRegistro("21/11/2020");
        repartidor.setNombre("Pedro");
        repartidor.setApellidos("Luna Ramos");
        repartidor.setPassword("1234");
        repartidor.setUsuario("ped");
        reference.push().setValue(repartidor);

        user.setCorreo("ped");
        user.setPassword("1234");
        reference2.push().setValue(user);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        txt_userName = view.findViewById(R.id.editText_UserName);
        txt_password = view.findViewById(R.id.editText_Password);
        spinner_userType = view.findViewById(R.id.spinner_UserType);
        btn_registro = view.findViewById(R.id.button_signUp);
        view.findViewById(R.id.button_login).setOnClickListener(this);

        FirebaseDatabase database =  FirebaseDatabase.getInstance();
        reference = database.getReference("USER");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    maxId = (snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //REGISTRO TEMPORAL EN EL BOTON REGISTRO
                User user = new User();
                String password = txt_password.getText().toString();
                String correo = txt_userName.getText().toString();

                user.setCorreo(correo);
                user.setPassword(password);

              //  reference.child(String.valueOf(maxId+1)).setValue(user);

                NavController navController = Navigation.findNavController(getView());
                navController.navigate(R.id.action_login_to_registrarCuenta);

             //   reference.push().setValue(user);
            }
        });

      /*  if(LogginHandler.isLoggedIn(getActivity()) == true)
        {
            NavController navController = Navigation.findNavController(getActivity(), R.id.fragment_navigation);
            navController.navigate(R.id.action_login_to_principalCliente);
        } */

        return view;
    }

    @Override
    public void onClick(View view) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        final String password = txt_password.getText().toString();
        final String correo = txt_userName.getText().toString();

        Query query = reference.orderByChild("correo").equalTo(correo);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds: snapshot.getChildren()){
                    User p = ds.getValue(User.class);
                    p.setIdUser(ds.getKey());
                  //  System.out.println("id"+ p.getIdUser());
                    if(p.getPassword().equals(password)) {
                        //   Log.e("info", "================>"+p.getComentarios());
                        Toast.makeText(getActivity(), "Hola "+p.getCorreo(), Toast.LENGTH_SHORT).show();
                        LogginHandler.setLoggedIn(getActivity(), true, UserType.CLIENTE, p.getIdUser());
                        NavController navController;
                      //  navController.navigate(R.id.action_login_to_principalCliente);
                        switch (spinner_userType.getSelectedItem().toString()){
                            case "Repartidor":
                                Bundle bundle = new Bundle();
                                bundle.putString("user", correo);
                                NavController navController3;
                                navController3 = Navigation.findNavController(getView());
                                navController3.navigate(R.id.action_login_fragment_to_principalRepartidorFragment, bundle);
                                break;
                            case "Cliente":
                                navController = Navigation.findNavController(getView());
                                navController.navigate(R.id.action_login_to_principalCliente);
                                break;
                            case "Farmacia":
                                NavController navController2;
                                navController2 = Navigation.findNavController(getView());
                                navController2.navigate(R.id.action_login_fragment_to_principalFarmaciaFragment);
                                break;
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {



    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}