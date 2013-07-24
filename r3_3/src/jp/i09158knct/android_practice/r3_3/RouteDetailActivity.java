package jp.i09158knct.android_practice.r3_3;

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

		String jsonString = getIntent().getStringExtra(Route.EXTRA_KEY_ROUTE);

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
		setText(R.id.route_detail_name, route.getString(Route.PROPERTY_KEY_NAME));
		setText(R.id.route_detail_description, route.getString(Route.PROPERTY_KEY_DESCRIPTION));
		setText(R.id.route_detail_played_count, "" + route.getInt(Route.PROPERTY_KEY_PLAYED_COUNT));
		setText(R.id.route_detail_achieved_count, "" + route.getInt(Route.PROPERTY_KEY_ACHIEVED_COUNT));

		String[] latLng = route.getString(Route.PROPERTY_KEY_START_LOCATION).split(",");
		setText(R.id.route_detail_latitude, latLng[0]);
		setText(R.id.route_detail_longitude, latLng[1]);

		String formatedTimeString = parseAndFormatTimeString(route.getString(Route.PROPERTY_KEY_CREATED_AT));
		setText(R.id.route_detail_created_at, formatedTimeString);
	}

	private String parseAndFormatTimeString(String timeString) throws ParseException {
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.UK);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy”N MŒŽ d“ú (EEE) HHŽž mm•ª ss•b", Locale.UK);
		Date time = parser.parse(timeString);
		return formatter.format(time);
	}

	private void setText(int id, String text) {
		TextView textView = (TextView) findViewById(id);
		textView.setText(text);
	}

}
