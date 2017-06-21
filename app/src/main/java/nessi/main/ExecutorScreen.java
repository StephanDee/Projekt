package nessi.main;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import nessi.main.ExecutorList.ExecutorListe;
import nessi.main.ExecutorList.Executors;
import nessi.main.HallOfFameList.Users;

/**
 * This class represents the ExecutorScreen.
 *
 * @author Stephan D
 */
public class ExecutorScreen extends AppCompatActivity {

    ImageButton imageButtonAddExecutor;
    Button buttonPlay, buttonStop;
    TextView textViewChallenger, textViewTitle, textViewReward, textViewDescription, textViewExecutor, textViewPoints;
    ListView listViewExecutors;

    VideoView videoViewChallenge;

    ProgressDialog progressDialog;

    // Initialize the Executor DatabaseReference
    DatabaseReference databaseExecutors;

    // Initialize the User DatabaseReference
    DatabaseReference databaseUsers;

    // Initialize FirebaseAuth
    FirebaseAuth firebaseAuth;

    List<Users> userList;
    List<Executors> executorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executorscreen);

        progressDialog = new ProgressDialog(this);

        savedInstanceState = getIntent().getExtras();

        String getId = savedInstanceState.getString("id");
        String getUser = savedInstanceState.getString("user");
        String getTitle = savedInstanceState.getString("title");
        Integer getReward = savedInstanceState.getInt("reward");
        String getDescription = savedInstanceState.getString("description");

        databaseExecutors = FirebaseDatabase.getInstance().getReference("executors").child(getId);
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        firebaseAuth = FirebaseAuth.getInstance();

        // from Executor
        textViewExecutor = (TextView) findViewById(R.id.textViewExecutor);
        textViewPoints = (TextView) findViewById(R.id.textViewPoints);

        // from Challenge
        textViewTitle = (TextView) findViewById(R.id.textViewChallengeTitle);
        textViewChallenger = (TextView) findViewById(R.id.textViewChallengeChallenger);
        textViewReward = (TextView) findViewById(R.id.textViewChallengeReward);
        textViewDescription = (TextView) findViewById(R.id.textViewChallengeDescription);

        // Video
        videoViewChallenge = (VideoView) findViewById(R.id.videoViewChallenge);
        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        buttonStop = (Button) findViewById(R.id.buttonStop);

        executorList = new ArrayList<>();
        userList = new ArrayList<>();

        imageButtonAddExecutor = (ImageButton) findViewById(R.id.imageButtonAddExecutor);
        listViewExecutors = (ListView) findViewById(R.id.listViewExecutors);

        // Challenger data
        textViewChallenger.setText(getUser);
        textViewTitle.setText(getTitle);
        textViewReward.setText(getReward.toString());
        textViewDescription.setText(getDescription);

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.spicychallenge);
                videoViewChallenge.setVideoURI(uri);
                videoViewChallenge.start();
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoViewChallenge.pause();
            }
        });

        imageButtonAddExecutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExecutor();
            }
        });
    }

    protected void onStart() {
        super.onStart();

        progressDialog.setMessage("loading...");
        progressDialog.show();

        databaseExecutors.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                executorList.clear();

                for (DataSnapshot executorSnapshot : dataSnapshot.getChildren()) {
                    Executors executor = executorSnapshot.getValue(Executors.class);

                    executorList.add(executor);
                }
                ExecutorListe executorListAdapter = new ExecutorListe(ExecutorScreen.this, executorList);
                listViewExecutors.setAdapter(executorListAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                userList.clear();

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Users user = userSnapshot.getValue(Users.class);

                    userList.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addExecutor() {

        final FirebaseUser user = firebaseAuth.getCurrentUser();
        String userData = user.getUid().trim();

        for (int i = 0; i < userList.size(); i++) {
            Users userDatabase = userList.get(i);
            if (userDatabase.getUserId().trim().equals(userData)) {
                String uid = userDatabase.getUserId();
                String name = userDatabase.getUserName();
                Integer upoints = userDatabase.getUserPoints();

                // Test
//        String uid = "TestUniqueUserID";
//        String name = "DeveloperTest";
//        Integer upoints = 1337;

                String id = databaseExecutors.push().getKey();
                Executors executor = new Executors(id, uid, name, upoints);
                databaseExecutors.child(id).setValue(executor);
                Toast.makeText(this, "Executor added", Toast.LENGTH_LONG).show();
            }
        }
    }
}
