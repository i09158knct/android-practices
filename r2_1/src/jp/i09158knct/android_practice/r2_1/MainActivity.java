package jp.i09158knct.android_practice.r2_1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String TARGET_URL = "https://gist.github.com/i09158knct/5945751/raw/331e46cffc8c9303393dfafe4abbdd30184b1f86/routes.json";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {
			JSONArray routes = getRoutesJSON(TARGET_URL);
			setRouteInfos(routes);
		}
		catch (ClientProtocolException e) {
			Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		catch (IOException e) {
			Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		catch (JSONException e) {
			Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}

	private void setRouteInfos(JSONArray routes) throws JSONException {
		setText(R.id.main_first_route, routes.getJSONObject(0).toString());
		setText(R.id.main_last_route, routes.getJSONObject(routes.length() - 1).toString());
	}

	private void setText(int id, String text) {
		TextView textView = (TextView) findViewById(id);
		textView.setText(text);
	}

	private JSONArray getRoutesJSON(String url) throws ClientProtocolException, IOException, JSONException {
		String jsonString = getJSONString(url);
		return new JSONArray(jsonString);
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
