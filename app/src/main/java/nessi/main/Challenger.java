package nessi.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
// import android.content.Intent;
// import android.widget.TextView;

import nessi.main.ChallengeList.Challenge;
// import nessi.main.ChallengeList.Tab1Ch;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Challenger extends AppCompatActivity {

    EditText titleText;
    EditText points;
    EditText textView;
    Button accept;
    Button cancel;

    // nessi code
    // Challenge item = null;

    // Initialize the DatabaseReference
    DatabaseReference databaseChallenges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenger);

        // Nessi Code
//        Intent intent = this.getIntent();
//        if (intent.getExtras() != null) {
//            Bundle bundle = intent.getExtras();
//            item = (Challenge) bundle.getSerializable("Challenge");
//        }

        databaseChallenges = FirebaseDatabase.getInstance().getReference("challenges");

        titleText = (EditText) findViewById(R.id.editTitel);
        textView = (EditText) findViewById(R.id.textView);
        points = (EditText) findViewById(R.id.editPoint);
        accept = (Button) findViewById(R.id.accept);
        cancel = (Button) findViewById(R.id.cancel);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addChallenge();
                startActivity(new Intent(getApplicationContext(), ChallengeListScreen.class));
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ChallengeListScreen.class));
            }
        });

        // Nessi Code
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cancel();
//            }
//        });
//        if (item == null) {
//            accept.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View view) {
//                    newChallenge();
//                }
//            });
//        } else {
//            titleText.setText(item.getTitle());
//            titleText.setFocusable(false);
//            textView.setText(item.getText());
//            textView.setFocusable(false);
//            points.setText(String.valueOf(item.getReward()), TextView.BufferType.EDITABLE);
//            points.setFocusable(false);
//            accept.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View view) {
//                    acceptChallenge();
//                }
//            });
//        }

    }

    /**
     * Adds a new Challenge by clicking the accept-button
     */
    private void addChallenge() {
        String title = titleText.getText().toString().trim();
        String reward = points.getText().toString().trim();
        String description = textView.getText().toString().trim();

        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(reward) && !TextUtils.isEmpty(description)) {

            String id = databaseChallenges.push().getKey();
            Challenge challenge = new Challenge(id, title, reward, description);

            databaseChallenges.child(id).setValue(challenge);

            Toast.makeText(this, "Challenge created", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "You should fill all fields!", Toast.LENGTH_LONG).show();
        }
    }

    // Nessi Code
//    private void acceptChallenge() {
//        finish();
//    }
//
//    public void newChallenge() {
//        String title = titleText.getText().toString();
//        String text = textView.getText().toString();
//        int reward = points.getInputType();
//        Challenge newCh = new Challenge(7, title, text, reward);
//        Tab1Ch.rowItems.add(newCh);
//        finish();
//    }
//
//    public void cancel() {
//        titleText.getText().clear();
//        points.getText().clear();
//        textView.getText().clear();
//        finish();
//    }

}