package com.example.farmaplus.client;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.farmaplus.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class BottomDialogNuevaDireccion extends BottomSheetDialogFragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    EditText nombreDireccion;
    TextView txt_direccion;
    String lat, lon, direccionInsert;

    private GoogleApiClient googleApiClient;
    Geocoder geocoder;
    List<Address> direccion;
    LatLng latLng;

    DatabaseReference reference;


    public BottomDialogNuevaDireccion() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog_nueva_direccion, container, false);
        Button btn_ubicacion = view.findViewById(R.id.button_ubicacionActual);
        Button btn_aceptar = view.findViewById(R.id.button_aceptar);
        Button btn_cancelar = view.findViewById(R.id.button_cancelar);
        nombreDireccion = view.findViewById(R.id.edit_nombreDIreccion);
        txt_direccion = view.findViewById(R.id.textviewDirecc);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference("DIRECCION");

        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
        btn_aceptar = view.findViewById(R.id.buttonAceptar);
        btn_cancelar = view.findViewById(R.id.buttonCancelar);
        btn_ubicacion = view.findViewById(R.id.button_ubicacionActual);

        btn_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                if (location != null) {
                    //Obtenemos la Longitut/Latitud
                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                }
                geocoder = new Geocoder(getContext(), Locale.getDefault());
                String city = null;

                try {

                    direccion = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    city = direccion.get(0).getAdminArea();
                    String address = direccion.get(0).getAddressLine(0);
                    // Toast.makeText(getContext(), address, Toast.LENGTH_LONG).show();
                    lat = String.valueOf(latLng.latitude);
                    lon = String.valueOf(latLng.longitude);
                    direccionInsert = address;
                    txt_direccion.setText(address);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Direccion direccion = new Direccion();
                direccion.setDireccion(direccionInsert);
                direccion.setIdUser(PrincipalCliente.idUser);
                direccion.setLatitud(lat);
                direccion.setLongitud(lon);
                direccion.setNombreDireccion(nombreDireccion.getText().toString());

                //INSERTAR EN FIREBASE
                reference.push().setValue(direccion);
                Toast.makeText(getContext(), "Dirección Creada con Exito", Toast.LENGTH_LONG).show();
                dismiss();
            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;
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
