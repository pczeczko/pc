package com.example.f1controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

public class ConnectionDialog extends DialogFragment {

	private String message;
	
	public ConnectionDialog(String message) {
		if (!message.isEmpty()) this.message = message;
		else this.message = "OK";
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(message).setPositiveButton("OK", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {}
		});
		return builder.create();
	}

}
