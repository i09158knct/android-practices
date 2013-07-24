package jp.i09158knct.android_practice.r3_2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class RouteDetailActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_detail);

		String jsonString = getIntent().getStringExtra(MainActivity.EXTRA_KEY_ROUTE);

		try {
			JSONObject route = new JSONObject(jsonString);
			setRouteInfo(route);
		}
		catch (JSONException e) {
			Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		catch (ParseException e) {
			Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}

	private void setRouteInfo(JSONObject route) throws JSONException, ParseException {
		setText(R.id.route_detail_name, route.getString("name"));
		setText(R.id.route_detail_description, route.getString("description"));
		setText(R.id.route_detail_played_count, "" + route.getInt("played_count"));
		setText(R.id.route_detail_achieved_count, "" + route.getInt("achieved_count"));

		String[] latLng = route.getString("start_location").split(",");
		setText(R.id.route_detail_latitude, latLng[0]);
		setText(R.id.route_detail_longitude, latLng[1]);

		String formatedTimeString = parseAndFormatTimeString(route.getString("created_at"));
		setText(R.id.route_detail_created_at, formatedTimeString);
	}

	private String parseAndFormatTimeString(String timeString) throws ParseException {
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.UK);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy�N M�� d�� (EEE) HH�� mm�� ss�b", Locale.UK);
		Date time = parser.parse(timeString);
		return formatter.format(time);
	}

	private void setText(int id, String text) {
		TextView textView = (TextView) findViewById(id);
		textView.setText(text);
	}

}
