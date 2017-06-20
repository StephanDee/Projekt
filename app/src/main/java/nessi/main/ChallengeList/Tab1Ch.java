package nessi.main.ChallengeList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import nessi.main.ChallengeScreen;
import nessi.main.ExecutorScreen;
import nessi.main.R;

public class Tab1Ch extends Fragment /*implements AdapterView.OnItemClickListener*/ {

    ListView listViewChallenges;
    List<Challenge> challengeList;

    ProgressDialog progressDialog;

    // Initialize the DatabaseReference
    DatabaseReference databaseChallenges;

    public Tab1Ch() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View rootView = inflater.inflate(R.layout.fragment_tab1ch, container, false);

        progressDialog = new ProgressDialog(getActivity());

        databaseChallenges = FirebaseDatabase.getInstance().getReference("challenges");

        listViewChallenges = (ListView) rootView.findViewById(R.id.listCh);
        challengeList = new ArrayList<>();

        progressDialog.setMessage("loading...");
        progressDialog.show();

        listViewChallenges.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Challenge challenge = challengeList.get(i);
                Intent executorScreen = new Intent(getActivity(), ExecutorScreen.class);
                executorScreen.putExtra("user", challenge.getchallengeCreator());
                executorScreen.putExtra("title", challenge.getchallengeTitle());
                executorScreen.putExtra("reward", challenge.getchallengeReward());
                executorScreen.putExtra("description", challenge.getchallengeText());
                startActivity(executorScreen);
            }
        });

        databaseChallenges.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                challengeList.clear();

                for (DataSnapshot challengeSnapshot : dataSnapshot.getChildren()) {
                    Challenge challenge = challengeSnapshot.getValue(Challenge.class);

                    challengeList.add(challenge);
                }

                ChallengeListe adapter = new ChallengeListe(getActivity(), challengeList);
                listViewChallenges.setAdapter(adapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rootView;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
