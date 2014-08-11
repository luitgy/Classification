package com.classification.activities;

import com.classification.R;
import com.classification.application.GlobalApp;
import com.commons.activity.AppsAbstractActivity;
import com.commons.util.AppsGuiUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppsAbstractActivity {

	private Button btnNew;
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
		btnRecord = (Button) findViewById(R.id.btnRecord);

		AppsGuiUtils.addButtonEffectClick(
				getResources().getColor(R.color.effect_click_button), btnNew);
		AppsGuiUtils
				.addButtonEffectClick(
						getResources().getColor(R.color.effect_click_button),
						btnRecord);

	}

	@Override
	protected void initAction() {

		btnNew.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				((GlobalApp) getApplicationContext()).resetValues();

				Intent intSelectGame = new Intent(v.getContext(),
						SelectGameActivity.class);
				startActivity(intSelectGame);
				finish();

			}

		});

		// btnLoad.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// Intent intLoad = new Intent(v.getContext(),
		// LoadItemActivity.class);
		// startActivity(intLoad);
		// finish();
		//
		// }
		// });

		btnRecord.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				Intent intRecord = new Intent(view.getContext(),
						RecordActivity.class);
				startActivity(intRecord);
				finish();

			}
		});

	}

	@Override
	protected void initValues() {
	}

}
