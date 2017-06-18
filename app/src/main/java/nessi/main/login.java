package nessi.main;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import nessi.main.HallOfFameList.Users;

public class login extends AppCompatActivity {

    Button buttonLogin, buttonCreateAcc;
    EditText etUsername;
    EditText etPassword;

    // Initialize the DatabaseReference
    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

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
                startActivity(new Intent(getApplicationContext(), homescreen.class));
            }
        });

        buttonCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCreateDialog();
            }
        });
    }

    public void showCreateDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.user_create_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final EditText editTextPassword = (EditText) dialogView.findViewById(R.id.editTextPassword);
        final Button buttonCreateUser = (Button) dialogView.findViewById(R.id.buttonCreateUser);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonCreateUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String rank = "1000";
                if(TextUtils.isEmpty(name)){
                    editTextName.setError("Name required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    editTextPassword.setError("Password required");
                    return;
                }

                String id = databaseUsers.push().getKey();
                Users user  = new Users(id, name, password, rank);

                databaseUsers.child(id).setValue(user);

                addUser();
                alertDialog.dismiss();
            }
        });
    }

    /**
     * Adds a toast by creating a new User by clicking the create account-button
     */
    private void addUser() {
            Toast.makeText(this, "Account created", Toast.LENGTH_LONG).show();
    }
}
