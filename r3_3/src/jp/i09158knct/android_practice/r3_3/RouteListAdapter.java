package jp.i09158knct.android_practice.r3_3;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RouteListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private JSONArray mRoutes;

	public RouteListAdapter(Activity activity, JSONArray routes) {
		mInflater = activity.getLayoutInflater();
		mRoutes = routes;
	}

	@Override
	public int getCount() {
		return mRoutes.length();
	}

	@Override
	public Object getItem(int position) {
		try {
			return mRoutes.getJSONObject(position);
		}
		catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
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

		JSONObject route;
		try {
			route = mRoutes.getJSONObject(position);
			String name = route.getString(Route.PROPERTY_KEY_NAME);
			String description = route.getString(Route.PROPERTY_KEY_DESCRIPTION);
			String location = route.getString(Route.PROPERTY_KEY_START_LOCATION);

			setText(convertView, R.id.list_item_route_name, name);
			setText(convertView, R.id.list_item_route_description, description);
			setText(convertView, R.id.list_item_route_start_location, location);

			return convertView;
		}
		catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void setText(View container, int id, String text) {
		TextView textView = (TextView) container.findViewById(id);
		textView.setText(text);
	}
}
