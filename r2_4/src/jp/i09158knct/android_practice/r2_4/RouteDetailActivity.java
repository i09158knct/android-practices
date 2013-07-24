package jp.i09158knct.android_practice.r2_4;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class RouteDetailActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_detail);

		String description = getIntent().getStringExtra(MainActivity.EXTRA_KEY_DESCRIPTION);
		TextView textView = (TextView) findViewById(R.id.route_detail_description);
		textView.setText(description);
	}
}
