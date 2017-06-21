package nessi.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import nessi.main.ChallengeList.Challenge;
import nessi.main.ChallengeList.ChallengeListe;
import nessi.main.HallOfFameList.Users;

/**
 * This class represents the ChallengeListScreen.
 *
 * @author Stephan D
 */
public class ChallengeListScreen extends AppCompatActivity {

    ImageButton buttonAdd;
    ListView listViewChallenges;
    List<Challenge> challengeList;
    List<Users> userList;

    ProgressDialog progressDialog;

    // Initialize the challengeDatabaseReference
    DatabaseReference databaseChallenges;

    // Initialize userDatabaseReference
    DatabaseReference databaseUsers;

    // Initialize the FirebaseAuth
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challengelist);

        progressDialog = new ProgressDialog(this);

        databaseChallenges = FirebaseDatabase.getInstance().getReference("challenges");
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();

        buttonAdd = (ImageButton) findViewById(R.id.imageButtonAddChallenge);
        listViewChallenges = (ListView) findViewById(R.id.listViewChallenges);
        challengeList = new ArrayList<>();
        userList = new ArrayList<>();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });

        listViewChallenges.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Challenge challenge = challengeList.get(i);
                showUpdateDeleteDialog(challenge.getchallengeId(), challenge.getchallengeTitle(), challenge.getchallengeReward(), challenge.getchallengeText());
                return true;
            }
        });

        listViewChallenges.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Challenge challenge = challengeList.get(i);
                Intent challengeScreen = new Intent(getApplicationContext(), ChallengeScreen.class);
                challengeScreen.putExtra("id", challenge.getchallengeId());
                challengeScreen.putExtra("userid", challenge.getuserId());
                challengeScreen.putExtra("user", challenge.getchallengeCreator());
                challengeScreen.putExtra("title", challenge.getchallengeTitle());
                challengeScreen.putExtra("reward", challenge.getchallengeReward());
                challengeScreen.putExtra("description", challenge.getchallengeText());
                startActivity(challengeScreen);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        progressDialog.setMessage("loading...");
        progressDialog.show();

        databaseChallenges.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                challengeList.clear();

                final FirebaseUser user = firebaseAuth.getCurrentUser();
                String userData = user.getUid().trim();

                for (DataSnapshot challengeSnapshot : dataSnapshot.getChildren()) {
                    Challenge challenge = challengeSnapshot.getValue(Challenge.class);

                    if(challenge.getuserId().equals(userData)) {
                        challengeList.add(challenge);
                    }
                }
                ChallengeListe adapter = new ChallengeListe(ChallengeListScreen.this, challengeList);
                listViewChallenges.setAdapter(adapter);
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

    public void showAddDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.challenge_add_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTitle = (EditText) dialogView.findViewById(R.id.editTitle);
        final EditText editDescription = (EditText) dialogView.findViewById(R.id.editDescription);
        final TextView textViewReward = (TextView) dialogView.findViewById(R.id.textViewReward);
        final SeekBar seekBarReward = (SeekBar) dialogView.findViewById(R.id.seekBarReward);
        final Button buttonAddChallenge = (Button) dialogView.findViewById(R.id.buttonAddChallenge);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        seekBarReward.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textViewReward.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        buttonAddChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = editTitle.getText().toString().trim();
                String description = editDescription.getText().toString().trim();
                Integer reward = seekBarReward.getProgress();

                if (TextUtils.isEmpty(title)) {
                    editTitle.setError("Title required");
                    return;
                }
                if (TextUtils.isEmpty(description)) {
                    editDescription.setError("Description required");
                    return;
                }

                final FirebaseUser user = firebaseAuth.getCurrentUser();
                String userData = user.getUid().trim();
                // Toast.makeText(getApplication(), userData, Toast.LENGTH_LONG).show();

                for (int i = 0; i < userList.size(); i++) {
                    Users userDatabase = userList.get(i);
                    if (userDatabase.getUserId().trim().equals(userData)) {
                        String creator = userDatabase.getUserName();
                        String uid = userDatabase.getUserId();

                        String id = databaseChallenges.push().getKey();
                        Challenge challenge = new Challenge(id, uid, creator, title, reward, description);

                        databaseChallenges.child(id).setValue(challenge);

                        addChallenge();
                        alertDialog.dismiss();
                    }
                }
            }
        });
    }

    /**
     * Adds a toast by creating a new Challenge by clicking the create challenge-button
     */
    private void addChallenge() {
        Toast.makeText(this, "Challenge created", Toast.LENGTH_LONG).show();
    }

    private void showUpdateDeleteDialog(final String challengeId, String challengeTitle, Integer challengeReward, String challengeDescription) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.challenge_update_delete_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextTitle = (EditText) dialogView.findViewById(R.id.editTextTitle);
        final EditText editTextDescription = (EditText) dialogView.findViewById(R.id.editTextDescription);
        final TextView textViewReward = (TextView) dialogView.findViewById(R.id.textViewReward);
        final SeekBar seekBarReward = (SeekBar) dialogView.findViewById(R.id.seekBarReward);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdate);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);

        dialogBuilder.setTitle(challengeTitle);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        seekBarReward.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textViewReward.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = editTextTitle.getText().toString().trim();
                String description = editTextDescription.getText().toString().trim();
                Integer reward = seekBarReward.getProgress();

                if (TextUtils.isEmpty(title)) {
                    editTextTitle.setError("Title required");
                    return;
                }
                if (TextUtils.isEmpty(description)) {
                    editTextDescription.setError("Description required");
                    return;
                }

                final FirebaseUser user = firebaseAuth.getCurrentUser();
                String userData = user.getUid().trim();
                // Toast.makeText(getApplication(), userData, Toast.LENGTH_LONG).show();

                for (int i = 0; i < userList.size(); i++) {
                    Users userDatabase = userList.get(i);
                    if (userDatabase.getUserId().trim().equals(userData)) {
                        String creator = userDatabase.getUserName();
                        String uid = userDatabase.getUserId();

                        updateChallenge(challengeId, uid, creator, title, reward, description);
                        alertDialog.dismiss();
                    }
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteChallenge(challengeId);
                alertDialog.dismiss();
            }
        });
    }

    private boolean updateChallenge(String id, String uid, String creator, String title, Integer reward, String description) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("challenges").child(id);
        Challenge challenge = new Challenge(id, uid, creator, title, reward, description);

        databaseReference.setValue(challenge);

        Toast.makeText(getApplicationContext(), "Challenge Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteChallenge(String id) {

        //getting the specified challenge reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("challenges").child(id);

        //removing challenge
        dR.removeValue();

        // getting the specified executor
        DatabaseReference dRexecutor = FirebaseDatabase.getInstance().getReference("executors").child(id);

        //removing executors
        dRexecutor.removeValue();

        Toast.makeText(getApplicationContext(), "Challenge Deleted", Toast.LENGTH_LONG).show();

        return true;
    }

}