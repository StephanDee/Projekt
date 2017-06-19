package nessi.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class homescreen extends AppCompatActivity {

    Button buttonX, buttonC, buttonLogout;
    TextView TextViewCurrentUser;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, login.class));
        }

        buttonX = (Button) findViewById(R.id.x);
        buttonC = (Button) findViewById(R.id.c);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        TextViewCurrentUser = (TextView) findViewById(R.id.textViewCurrentUser);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        TextViewCurrentUser.setText("Welcome: " + user.getEmail());

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), login.class));
            }
        });

        buttonX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Executor.class));
            }
        });

        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ChallengeListScreen.class));
            }
        });
    }
}
