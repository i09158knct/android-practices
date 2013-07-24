package jp.i09158knct.android_practice.r1_2_2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String TARGET_URL = "https://gist.github.com/i09158knct/5945751/raw/7b8de8f1fe18317c7991e458af347ae7915fcf28/route.json";

	private class GetJSONTask extends AsyncTask<String, Void, JSONObject> {

		private ProgressDialog mDialog;

		@Override
		protected void onPreExecute() {
			mDialog = new ProgressDialog(MainActivity.this);
			mDialog.setTitle("Loading...");
			mDialog.show();
		}

		@Override
		protected JSONObject doInBackground(String... urls) {
			try {
				return getRouteJSON(urls[0]);
			}
			catch (ClientProtocolException e) {
				e.printStackTrace();
				cancel(true);
				return null;
			}
			catch (IOException e) {
				e.printStackTrace();
				cancel(true);
				return null;
			}
			catch (JSONException e) {
				e.printStackTrace();
				cancel(true);
				return null;
			}
		}

		@Override
		protected void onPostExecute(JSONObject route) {
			mDialog.dismiss();
			try {
				setRouteInfo(route);
			}
			catch (JSONException e) {
				Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
			catch (ParseException e) {
				Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
		}

		private JSONObject getRouteJSON(String url) throws ClientProtocolException, IOException, JSONException {
			String jsonString = getJSONString(url);
			return new JSONObject(jsonString);
		}

		private String getJSONString(String url) throws ClientProtocolException, IOException {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(url);

			HttpResponse response = client.execute(request);
			ByteArrayOutputStream outstream = new ByteArrayOutputStream();
			response.getEntity().writeTo(outstream);
			return outstream.toString();
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		new GetJSONTask().execute(TARGET_URL);
	}

	private void setRouteInfo(JSONObject route) throws JSONException, ParseException {
		setText(R.id.main_name, route.getString("name"));
		setText(R.id.main_description, route.getString("description"));
		setText(R.id.main_played_count, "" + route.getInt("played_count"));
		setText(R.id.main_achieved_count, "" + route.getInt("achieved_count"));

		String[] latLng = route.getString("start_location").split(",");
		setText(R.id.main_latitude, latLng[0]);
		setText(R.id.main_longitude, latLng[1]);

		String formatedTimeString = parseAndFormatTimeString(route.getString("created_at"));
		setText(R.id.main_created_at, formatedTimeString);
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
