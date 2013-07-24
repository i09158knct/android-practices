package jp.i09158knct.android_practice.r3_1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RouteListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private int mLayoutId;
	private String[] mNames;

	public RouteListAdapter(Activity activity, int id, String[] names) {
		mInflater = activity.getLayoutInflater();
		mLayoutId = id;
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
			convertView = mInflater.inflate(mLayoutId, parent, false);
		}

		String name = mNames[position];
		((TextView) convertView).setText(name);
		return convertView;
	}

}
