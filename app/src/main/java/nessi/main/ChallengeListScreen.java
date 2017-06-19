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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import nessi.main.ChallengeList.Challenge;
import nessi.main.ChallengeList.ChallengeListe;

/**
 * This class represents the ChallengeListScreen.
 *
 * @author Stephan D
 */
public class ChallengeListScreen extends AppCompatActivity {

    ImageButton buttonAdd;
    ListView listViewChallenges;
    List<Challenge> challengeList;

    ProgressDialog progressDialog;

    // Initialize the DatabaseReference
    DatabaseReference databaseChallenges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challengelist);

        progressDialog = new ProgressDialog(this);

        databaseChallenges = FirebaseDatabase.getInstance().getReference("challenges");

        buttonAdd = (ImageButton) findViewById(R.id.imageButtonAddChallenge);
        listViewChallenges = (ListView) findViewById(R.id.listViewChallenges);
        challengeList = new ArrayList<>();

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

                for (DataSnapshot challengeSnapshot : dataSnapshot.getChildren()) {
                    Challenge challenge = challengeSnapshot.getValue(Challenge.class);

                    challengeList.add(challenge);
                }
                ChallengeListe adapter = new ChallengeListe(ChallengeListScreen.this, challengeList);
                listViewChallenges.setAdapter(adapter);
                progressDialog.dismiss();
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
        final EditText editReward = (EditText) dialogView.findViewById(R.id.editReward);
        final Button buttonAddChallenge = (Button) dialogView.findViewById(R.id.buttonAddChallenge);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonAddChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = editTitle.getText().toString().trim();
                String description = editDescription.getText().toString().trim();
                String reward = editReward.getText().toString().trim();

                if (TextUtils.isEmpty(title)) {
                    editTitle.setError("Title required");
                    return;
                }
                if (TextUtils.isEmpty(description)) {
                    editDescription.setError("Description required");
                    return;
                }
                if (TextUtils.isEmpty(reward)) {
                    editReward.setError("Reward required");
                    return;
                }

                String id = databaseChallenges.push().getKey();
                Challenge challenge = new Challenge(id, title, reward, description);

                databaseChallenges.child(id).setValue(challenge);

                addChallenge();
                alertDialog.dismiss();
            }
        });
    }

    /**
     * Adds a toast by creating a new Challenge by clicking the create challenge-button
     */
    private void addChallenge() {
        Toast.makeText(this, "Challenge created", Toast.LENGTH_LONG).show();
    }

    private void showUpdateDeleteDialog(final String challengeId, String challengeTitle, String challengeReward, String challengeDescription) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.challenge_update_delete_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextTitle = (EditText) dialogView.findViewById(R.id.editTextTitle);
        final EditText editTextReward = (EditText) dialogView.findViewById(R.id.editTextReward);
        final EditText editTextDescription = (EditText) dialogView.findViewById(R.id.editTextDescription);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdate);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);

        dialogBuilder.setTitle(challengeTitle);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTextTitle.getText().toString().trim();
                String reward = editTextReward.getText().toString().trim();
                String description = editTextDescription.getText().toString().trim();

                if (TextUtils.isEmpty(title)) {
                    editTextTitle.setError("Title required");
                    return;
                }
                if (TextUtils.isEmpty(reward)) {
                    editTextReward.setError("Reward required");
                    return;
                }
                if (TextUtils.isEmpty(description)) {
                    editTextDescription.setError("Description required");
                    return;
                }

                updateChallenge(challengeId, title, reward, description);
                alertDialog.dismiss();
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

    private void updateChallenge(String id, String title, String reward, String description) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("challenges").child(id);
        Challenge challenge = new Challenge(id, title, reward, description);

        databaseReference.setValue(challenge);

        Toast.makeText(this, "Challenge Updated Successfully", Toast.LENGTH_LONG).show();
    }

    private boolean deleteChallenge(String id) {

        //getting the specified challenge reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("challenges").child(id);

        //removing challenge
        dR.removeValue();

        Toast.makeText(getApplicationContext(), "Challenge Deleted", Toast.LENGTH_LONG).show();

        return true;
    }

}