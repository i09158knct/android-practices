package jp.i09158knct.android_practice.r2_3;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	private static final String TARGET_URL = "https://gist.github.com/i09158knct/5945751/raw/331e46cffc8c9303393dfafe4abbdd30184b1f86/routes.json";

	private JSONArray mRoutes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {
			mRoutes = getRoutesJSON(TARGET_URL);
			String[] names = getRouteNames(mRoutes);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
			setListAdapter(adapter);
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

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		try {
			JSONObject route = mRoutes.getJSONObject(position);
			String description = route.getString("description");
			Toast.makeText(this, description, Toast.LENGTH_SHORT).show();
		}
		catch (JSONException e) {
			Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}

	private String[] getRouteNames(JSONArray routes) throws JSONException {
		List<String> names = new LinkedList<String>();
		for (int i = 0; i < routes.length(); i++) {
			JSONObject route = routes.getJSONObject(i);
			String name = route.getString("name");
			names.add(name);
		}
		return (String[]) names.toArray(new String[routes.length()]);
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
