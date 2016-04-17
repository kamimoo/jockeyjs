package com.example.jockeytestapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingsDialogFragment extends DialogFragment {

	private Callback callback;
	private static final String CONVERTER_INDEX_KEY = "converter_index";
	private static final String WEBVIEW_FEATURE_INDEX_KEY = "webview_feature_index";

	interface Callback {
		void onChangeSettings(int converterIndex, int webViewFeatureIndex);
	}

	public static SettingsDialogFragment newInstance(int converterIndex, int webViewFeatureIndex) {
		SettingsDialogFragment dialogFragment = new SettingsDialogFragment();
		Bundle args = new Bundle();
		args.putInt(CONVERTER_INDEX_KEY, converterIndex);
		args.putInt(WEBVIEW_FEATURE_INDEX_KEY, webViewFeatureIndex);
		dialogFragment.setArguments(args);
		return dialogFragment;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (!(activity instanceof Callback)) {
			throw new IllegalStateException();
		}
		callback = (Callback) activity;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		int converterIndex = getArguments().getInt(CONVERTER_INDEX_KEY);
		int webViewFeatureIndex = getArguments().getInt(WEBVIEW_FEATURE_INDEX_KEY);

		LayoutInflater inflater = getActivity().getLayoutInflater();
		View rootView = inflater.inflate(R.layout.dialog_settings, null);
		final Spinner converterSpinner = (Spinner) rootView.findViewById(R.id.spinner_converter);
		final ArrayAdapter<String> adapter =
			new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
				getResources().getStringArray(R.array.converters));
		converterSpinner.setAdapter(adapter);
		converterSpinner.setSelection(converterIndex);

		final Spinner webViewSpinner = (Spinner) rootView.findViewById(R.id.spinner_webview);
		final ArrayAdapter<String> webViewAdapter =
			new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
				getResources().getStringArray(R.array.webviews));
		webViewSpinner.setAdapter(webViewAdapter);
		webViewSpinner.setSelection(webViewFeatureIndex, false);

		AlertDialog.Builder builder =
			new AlertDialog.Builder(getActivity()).setTitle(R.string.action_settings)
				.setView(rootView)
				.setPositiveButton("Change", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						callback.onChangeSettings(converterSpinner.getSelectedItemPosition(),
							webViewSpinner.getSelectedItemPosition());
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						SettingsDialogFragment.this.getDialog().cancel();
					}
				});

		return builder.create();
	}
}
