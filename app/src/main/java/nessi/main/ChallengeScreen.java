package nessi.main;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

import nessi.main.ChallengeList.Challenge;
import nessi.main.ExecutorList.ExecutorListe;
import nessi.main.ExecutorList.Executors;
import nessi.main.HallOfFameList.Users;

/**
 * This class represents the ChallengeScreen.
 *
 * @author Stephan D
 */
public class ChallengeScreen extends AppCompatActivity {

    Button buttonAccept, buttonPlay, buttonStop;
    TextView textViewChallenger, textViewTitle, textViewReward, textViewDescription;
    ListView listViewExecutors;

    VideoView videoViewChallenge;

    ProgressDialog progressDialog;

    // Initialize the DatabaseReference
    DatabaseReference databaseExecutors;

    List<Executors> executorList;

    String getId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challengescreen);

        progressDialog = new ProgressDialog(this);

        savedInstanceState = getIntent().getExtras();

        getId = savedInstanceState.getString("id");
        String getUser = savedInstanceState.getString("user");
        String getTitle = savedInstanceState.getString("title");
        Integer getReward = savedInstanceState.getInt("reward");
        String getDescription = savedInstanceState.getString("description");

        databaseExecutors = FirebaseDatabase.getInstance().getReference("executors").child(getId);

        textViewChallenger = (TextView) findViewById(R.id.textViewChallengeChallenger);
        textViewTitle = (TextView) findViewById(R.id.textViewChallengeTitle);
        textViewReward = (TextView) findViewById(R.id.textViewChallengeReward);
        textViewDescription = (TextView) findViewById(R.id.textViewChallengeDescription);
        videoViewChallenge = (VideoView) findViewById(R.id.videoViewChallenge);
        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        buttonStop = (Button) findViewById(R.id.buttonStop);

        executorList = new ArrayList<>();
        listViewExecutors = (ListView) findViewById(R.id.listViewExecutors);

        textViewChallenger.setText(getUser);
        textViewTitle.setText(getTitle);
        textViewReward.setText(getReward.toString());
        textViewDescription.setText(getDescription);

        listViewExecutors.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Executors executor = executorList.get(i);
                showDeleteDialog(executor.getExecutorId(), executor.getUserId(), executor.getExecutorName(), executor.getExecutorPoints());
                return true;
            }
        });

        buttonPlay.setOnClickListener(new View.OnClickListener() {
//            boolean alreadyClicked = false;

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
                ExecutorListe executorListAdapter = new ExecutorListe(ChallengeScreen.this, executorList);
                listViewExecutors.setAdapter(executorListAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void showDeleteDialog(final String executorId,final String userId, String name, Integer points) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.executor_delete_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button buttonDeleteExecutor = (Button) dialogView.findViewById(R.id.buttonDeleteExecutor);

        dialogBuilder.setTitle(name);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonDeleteExecutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDialogExecutor(executorId);
                alertDialog.dismiss();
            }
        });
    }

    private boolean deleteDialogExecutor(String id) {

        // getting the specified executor
        DatabaseReference dRexecutor = FirebaseDatabase.getInstance().getReference("executors").child(getId).child(id);

        //removing executors
        dRexecutor.removeValue();

        Toast.makeText(getApplicationContext(), "Executor Deleted", Toast.LENGTH_LONG).show();

        return true;
    }
}

