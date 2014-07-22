package com.classification.activities;

import com.classification.R;
import com.classification.application.GlobalApp;
import com.commons.activity.AppsAbstractActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppsAbstractActivity {

	private Button btnNew;
	private Button btnLoad;
	private Button btnRecord;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initControls();
		initAction();
		initValues();
	}

	@Override
	protected void initControls() {

		btnNew = (Button) findViewById(R.id.btnNew);
		btnLoad = (Button) findViewById(R.id.btnLoad);
		btnRecord = (Button) findViewById(R.id.btnRecord);

	}

	@Override
	protected void initAction() {

		btnNew.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				((GlobalApp) getApplicationContext()).resetValues();

				Intent intNew = new Intent(v.getContext(),
						NewItemActivity.class);
				startActivity(intNew);
				finish();

			}

		});

		btnLoad.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intLoad = new Intent(v.getContext(),
						LoadItemActivity.class);
				startActivity(intLoad);
				finish();

			}
		});

		btnRecord.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

	}

	@Override
	protected void initValues() {
	}

}
