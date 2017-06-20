package nessi.main.ChallengeList;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import nessi.main.R;

/**
 * This class represents the ChallengeListe.
 *
 * @author Stephan D
 */
public class ChallengeListe extends ArrayAdapter<Challenge> {

    private Activity context;
    private List<Challenge> challengeList;

    // Constructor
    public ChallengeListe(Activity context, List<Challenge> challengeList) {
        super(context, R.layout.challengelist_layout, challengeList);

        this.context = context;
        this.challengeList = challengeList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.challengelist_layout, null, true);

        TextView textViewCreator = (TextView) listViewItem.findViewById(R.id.textViewCreator);
        TextView textViewTitle = (TextView) listViewItem.findViewById(R.id.textViewTitle);
        TextView textViewReward = (TextView) listViewItem.findViewById(R.id.textViewReward);
        TextView textViewDescription = (TextView) listViewItem.findViewById(R.id.textViewDescription);

        Challenge challenge = challengeList.get(position);

        textViewCreator.setText(String.format("%s%s", context.getString(R.string.creator), challenge.getchallengeCreator()));
        textViewTitle.setText(challenge.getchallengeTitle());
        textViewReward.setText(String.format("%s%s", context.getString(R.string.rewarditem), challenge.getchallengeReward()));
        textViewDescription.setText(challenge.getchallengeText());

        return listViewItem;
    }
}