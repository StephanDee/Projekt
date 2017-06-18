package nessi.main.HallOfFameList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import nessi.main.R;

public class ProfileAdapter extends BaseAdapter {

	Context context;
	List<User> rowItems;

	ProfileAdapter(Context context, List<User> rowItems) {
		this.context = context;
		this.rowItems = rowItems;
	}

	@Override
	public int getCount() {
		return rowItems.size();
	}

	@Override
	public Object getItem(int position) {
		return rowItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return rowItems.indexOf(getItem(position));
	}

	/* private view holder class */
	private class ViewHolder {
		ImageView profile_pic;
		TextView member_name;
		ImageView status;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item, null);
			ViewHolder holder = new ViewHolder();
			holder.member_name = (TextView) convertView
					.findViewById(R.id.member_name);
			holder.profile_pic = (ImageView) convertView
					.findViewById(R.id.profile_pic);
			holder.status = (ImageView) convertView.findViewById(R.id.rankIcon);
			User row_pos = rowItems.get(position);
			holder.profile_pic.setImageResource(row_pos.getId());
			holder.member_name.setText(row_pos.getName());
			holder.status.setImageResource(row_pos.getRank());

			convertView.setTag(holder);
		}
		return convertView;
	}

}
