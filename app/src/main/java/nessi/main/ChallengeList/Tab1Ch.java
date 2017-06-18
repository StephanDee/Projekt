package nessi.main.ChallengeList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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

    // Initialize the DatabaseReference
    DatabaseReference databaseChallenges;

    public Tab1Ch() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View rootView = inflater.inflate(R.layout.fragment_tab1ch, container, false);

        databaseChallenges = FirebaseDatabase.getInstance().getReference("challenges");

        listViewChallenges = (ListView) rootView.findViewById(R.id.listCh);
        challengeList = new ArrayList<>();

        listViewChallenges.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Challenge challenge = challengeList.get(i);
                Intent executorScreen = new Intent(getActivity(), ExecutorScreen.class);
                executorScreen.putExtra("title", challenge.getchallengeTitle());
                executorScreen.putExtra("reward", challenge.getchallengeReward());
                executorScreen.putExtra("description", challenge.getchallengeText());
                startActivity(executorScreen);
                // startActivity(new Intent(getActivity(), ExecutorScreen.class));
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rootView;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        databaseChallenges.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                challengeList.clear();
//
//                for (DataSnapshot challengeSnapshot : dataSnapshot.getChildren()) {
//                    Challenge challenge = challengeSnapshot.getValue(Challenge.class);
//
//                    challengeList.add(challenge);
//                }
//
//                ChallengeListe adapter = new ChallengeListe(null, Tab1Ch.this, challengeList);
//                listViewChallenges.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

//    ListView mylistview;
//    int[] id;
//    String[] titles;
//    int[] reward;
//    String[] text;
//    public static List<Challenge> rowItems = new ArrayList<>();
//
//    public Tab1Ch() {
//    }
//
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        View rootView = inflater.inflate(R.layout.fragment_tab1ch, container, false);
//        titles = getResources().getStringArray(R.array.titles);
//        id = getResources().getIntArray(R.array.id);
//        reward = getResources().getIntArray(R.array.reward);
//        text = getResources().getStringArray(R.array.texts);
//        for (int i = 0; i < titles.length; i++) {
//            Challenge item = new Challenge(id[i], titles[i], text[i], reward[i]);
//            rowItems.add(item);
//        }
//        mylistview = (ListView) rootView.findViewById(R.id.listCh);
//        ChallengeAdapter adapter = new ChallengeAdapter(rootView.getContext(), rowItems);
//        mylistview.setAdapter(adapter);
//        mylistview.setOnItemClickListener(this);
//        return rootView;
//    }
//
//    public interface OnFragmentInteractionListener {
//        void onFragmentInteraction(Uri uri);
//    }
//
//    public void onItemClick(AdapterView<?> parent, View view, int position,
//                            long id) {
//        Challenge ch = rowItems.get(position);
//        Intent newWindow = new Intent(getContext(), Challenger.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("Challenge", ch);
//        newWindow.putExtras(bundle);
//        startActivity(newWindow);
//    }

}
