package nessi.main.ExecutorList;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import nessi.main.R;

/**
 * This class represents the ExecutorListe.
 *
 * @author Stephan D
 */
public class ExecutorListe extends ArrayAdapter<Executors> {

    private Activity context;
    private List<Executors> executorsList;

    // Constructor
    public ExecutorListe(Activity context, List<Executors> executorsList){
        super(context, R.layout.executorlist_layout, executorsList);
        this.context = context;
        this.executorsList = executorsList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.executorlist_layout, null, true);

        TextView textViewExecutor = (TextView) listViewItem.findViewById(R.id.textViewExecutor);
        TextView textViewPoints = (TextView) listViewItem.findViewById(R.id.textViewPoints);

        Executors executors = executorsList.get(position);

        textViewExecutor.setText(executors.getExecutorName());
        textViewPoints.setText(String.format("%s%s", context.getString(R.string.pointsitem), executors.getExecutorPoints()));

        return listViewItem;
    }
}