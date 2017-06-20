package nessi.main;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

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

    List<Users> ExecutorList;

    // Initialize the DatabaseReference
    DatabaseReference databaseChallenges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challengescreen);

        savedInstanceState = getIntent().getExtras();

        String getUser = savedInstanceState.getString("user");
        String getTitle = savedInstanceState.getString("title");
        Integer getReward = savedInstanceState.getInt("reward");
        String getDescription = savedInstanceState.getString("description");

        textViewChallenger = (TextView) findViewById(R.id.textViewChallengeChallenger);
        textViewTitle = (TextView) findViewById(R.id.textViewChallengeTitle);
        textViewReward = (TextView) findViewById(R.id.textViewChallengeReward);
        textViewDescription = (TextView) findViewById(R.id.textViewChallengeDescription);
        videoViewChallenge = (VideoView) findViewById(R.id.videoViewChallenge);
        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        buttonStop = (Button) findViewById(R.id.buttonStop);

        textViewChallenger.setText(getUser);
        textViewTitle.setText(getTitle);
        textViewReward.setText(getReward.toString());
        textViewDescription.setText(getDescription);

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
    }
}
