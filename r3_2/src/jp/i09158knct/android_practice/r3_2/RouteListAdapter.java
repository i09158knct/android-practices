package jp.i09158knct.android_practice.r3_2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RouteListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private String[] mNames;

	public RouteListAdapter(Activity activity, String[] names) {
		mInflater = activity.getLayoutInflater();
		mNames = names;
	}

	@Override
	public int getCount() {
		return mNames.length;
	}

	@Override
	public Object getItem(int position) {
		return mNames[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item_route, parent, false);
		}

		String name = mNames[position];
		setText(convertView, R.id.list_item_route_name, name);

		return convertView;
	}

	private void setText(View container, int id, String text) {
		TextView textView = (TextView) container.findViewById(id);
		textView.setText(text);
	}
}
