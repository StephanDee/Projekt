package nessi.main.HallOfFameList;

import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import nessi.main.R;

public class Tab2Fame extends Fragment {

    String[] member_names;
    TypedArray profile_pics;
    TypedArray statues;
    List<User> list;
    ListView view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_tab1ch, container, false);
        list = new ArrayList<>();
        member_names = getResources().getStringArray(R.array.Member_names);
        profile_pics = getResources().obtainTypedArray(R.array.profile_pics);
        statues = getResources().obtainTypedArray(R.array.statues);
        for (int i = 0; i < member_names.length; i++) {
            User item = new User(member_names[i], profile_pics.getResourceId(i, -1),
                    statues.getResourceId(i, -1));
            list.add(item);
        }
        view = (ListView) rootView.findViewById(R.id.listCh);
        ProfileAdapter adapter = new ProfileAdapter(rootView.getContext(), list);
        view.setAdapter(adapter);
        return rootView;
    }



    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
