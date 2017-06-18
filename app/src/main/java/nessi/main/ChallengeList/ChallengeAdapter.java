package nessi.main.ChallengeList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;
import nessi.main.R;

public class ChallengeAdapter /*extends BaseAdapter*/ {

//    Context context;
//    List<Challenge> rowItems;
//
//    ChallengeAdapter(Context context, List<Challenge> rowItems) {
//        this.context = context;
//        this.rowItems = rowItems;
//    }
//
//    @Override
//    public int getCount() {
//        return rowItems.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return rowItems.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return rowItems.indexOf(getItem(position));
//    }
//
//    private class ViewHolder {
//        TextView title;
//        TextView reward;
//    }
//
//    @Override
//    public View getView(int position, View view, ViewGroup parent) {
//        ViewHolder holder = null;
//        LayoutInflater mInflater = (LayoutInflater) context
//                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//        if (view == null) {
//            view = mInflater.inflate(R.layout.chlist_item, null);
//            holder = new ViewHolder();
//            holder.title = (TextView) view.findViewById(R.id.title);
//            holder.reward = (TextView) view.findViewById(R.id.reward);
//            Challenge item = rowItems.get(position);
//            holder.title.setText(item.getTitle());
//            String x = item.getReward()+"";
//            holder.reward.setText(x);
//            view.setTag(holder);
//        } else {
//            holder = (ViewHolder) view.getTag();
//        }
//
//        return view;
//    }

}
