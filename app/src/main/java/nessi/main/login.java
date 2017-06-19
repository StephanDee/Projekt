package nessi.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class login extends AppCompatActivity {

    Button buttonLogin, buttonCreateAcc;
    EditText etUsername;
    EditText etPassword;

    // Initialize the DatabaseReference
    // DatabaseReference databaseUsers;

    ProgressDialog progressDialog;

    // Initialize the AuthReference
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //profile acitvity here
            finish();
            startActivity(new Intent(getApplicationContext(), homescreen.class));
        }

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        buttonLogin = (Button) findViewById(R.id.bLogin);
        buttonCreateAcc = (Button) findViewById(R.id.bCreateAcc);
    }

    @Override
    protected void onStart() {
        super.onStart();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        buttonCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCreateDialog();
            }
        });
    }

    public void userLogin(){
        String email = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        progressDialog = new ProgressDialog(this);

        if (TextUtils.isEmpty(email)) {
            etUsername.setError("Email required");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password required");
            return;
        }

        progressDialog.setMessage("Registering...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(getApplicationContext(), homescreen.class));
                } else {
                    addUserError();
                }
            }
        });


    }

    public void showCreateDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.user_create_dialog, null);
        dialogBuilder.setView(dialogView);

        final ProgressDialog progressDialog = new ProgressDialog(this);

        final EditText editTextEmail = (EditText) dialogView.findViewById(R.id.editTextName);
        final EditText editTextPassword = (EditText) dialogView.findViewById(R.id.editTextPassword);
        final Button buttonCreateUser = (Button) dialogView.findViewById(R.id.buttonCreateUser);

//        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
//        final EditText editTextPassword = (EditText) dialogView.findViewById(R.id.editTextPassword);
//        final Button buttonCreateUser = (Button) dialogView.findViewById(R.id.buttonCreateUser);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    editTextEmail.setError("Email required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    editTextPassword.setError("Password required");
                    return;
                }
                 progressDialog.setMessage("Registering Account...");
                 progressDialog.show();

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            addUser();
                            finish();
                            startActivity(new Intent(getApplicationContext(), homescreen.class));
                        } else {
                            addUserError();
                        }
                        progressDialog.dismiss();
                    }
                });

                alertDialog.dismiss();


//                String name = editTextName.getText().toString().trim();
//                String password = editTextPassword.getText().toString().trim();
//                String rank = "1000";
//                if(TextUtils.isEmpty(name)){
//                    editTextName.setError("Name required");
//                    return;
//                }
//                if (TextUtils.isEmpty(password)){
//                    editTextPassword.setError("Password required");
//                    return;
//                }
//
//                String id = databaseUsers.push().getKey();
//                Users user  = new Users(id, name, password, rank);
//
//                databaseUsers.child(id).setValue(user);
//
//                addUser();
//                alertDialog.dismiss();
            }
        });
    }

    /**
     * Adds a toast by creating a new User by clicking the create account-button
     */
    private void addUser() {
        Toast.makeText(this, "Registered Successfully", Toast.LENGTH_LONG).show();
    }

    private void addUserError() {
        Toast.makeText(this, "Could not register, Please try again.", Toast.LENGTH_LONG).show();
    }
}
