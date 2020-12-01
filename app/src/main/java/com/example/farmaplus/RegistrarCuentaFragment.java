package com.example.farmaplus;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.farmaplus.client.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

public class RegistrarCuentaFragment extends Fragment implements View.OnClickListener {
    TextInputEditText txt_correo, txt_user, txt_password, txt_password2;
    NavController navController;
    private FirebaseAuth mAuth;

    DatabaseReference reference;
    long maxId = 0;

    ProgressDialog progressDialog;

    public RegistrarCuentaFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registrar_cuenta, container, false);
        Button buttonCancelar = view.findViewById(R.id.buttonCancelar);
        buttonCancelar.setOnClickListener(this);
        Button buttonCrearCuenta = view.findViewById(R.id.buttonCrearCuenta);
        buttonCrearCuenta.setOnClickListener(this);

        txt_correo = view.findViewById(R.id.input_correo);
        txt_user = view.findViewById(R.id.input_user);
        txt_password = view.findViewById(R.id.input_password);
        txt_password2 = view.findViewById(R.id.input_password2);

        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        Navigation.setViewNavController(view, navController);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonCrearCuenta:
                if(!txt_correo.getText().toString().isEmpty() && !txt_user.getText().toString().isEmpty() && !txt_password.getText().toString().isEmpty()
                        && !txt_password2.getText().toString().isEmpty()){
                    if(txt_password.getText().toString().equals(txt_password2.getText().toString())){
                        progressDialog.setMessage("REALIZANDO NUEVO REGISTRO");
                        progressDialog.show();
                        String email = txt_correo.getText().toString();
                        String password = txt_password.getText().toString();
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            User users = new User();
                                            String usuario = txt_user.getText().toString();

                                            users.setCorreo(usuario);
                                            users.setPassword(password);

                                            reference.child(String.valueOf(maxId+1)).setValue(users);

                                            FirebaseUser user = mAuth.getCurrentUser();
                                            user.sendEmailVerification();

                                            progressDialog.dismiss();
                                            Toast.makeText(getContext(), "Autenticación Exitosa, por favor verifica tu cuenta de correo",
                                                    Toast.LENGTH_LONG).show();
                                            navController.popBackStack();
                                            //  updateUI(user);
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            //  Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                            progressDialog.dismiss();
                                            Toast.makeText(getContext(), "La contraseña debe tener al menos 6 caracteres",
                                                    Toast.LENGTH_SHORT).show();
                                            // updateUI(null);
                                        }

                                        // ...
                                    }
                                });
                    }else{
                        Toast.makeText(getContext(), "Las contraseñas son diferentes",
                                Toast.LENGTH_SHORT).show();
                    }
                }else
                {
                    Toast.makeText(getContext(), "Campos vacios, Completa el formulario",
                            Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.buttonCancelar:
                navController.popBackStack();
                break;
        }
    }
}