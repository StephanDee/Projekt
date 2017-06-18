package nessi.main;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import nessi.main.HallOfFameList.Users;

/**
 * This class represents the ChallengeScreen.
 *
 * @author Stephan D
 */
public class ExecutorScreen extends AppCompatActivity {

    Button buttonPlay, buttonStop;
    TextView textViewChallenger, textViewTitle, textViewReward, textViewDescription;
    ListView listViewExecutors;

    VideoView videoViewChallenge;

    List<Users> ExecutorList;

    // Initialize the DatabaseReference
    DatabaseReference databaseChallenges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executorscreen);

        savedInstanceState = getIntent().getExtras();

        String getTitle = savedInstanceState.getString("title");
        String getReward = savedInstanceState.getString("reward");
        String getDescription = savedInstanceState.getString("description");

        textViewTitle = (TextView) findViewById(R.id.textViewChallengeTitle);
        textViewReward = (TextView) findViewById(R.id.textViewChallengeReward);
        textViewDescription = (TextView) findViewById(R.id.textViewChallengeDescription);
        videoViewChallenge = (VideoView) findViewById(R.id.videoViewChallenge);
        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        buttonStop = (Button) findViewById(R.id.buttonStop);

        textViewTitle.setText(getTitle);
        textViewReward.setText(getReward);
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
    }

    protected void onStart() {
        super.onStart();
    }
}
