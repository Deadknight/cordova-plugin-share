package com.dk;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;

import android.support.v4.content.FileProvider;
import java.io.File;
import android.content.Context;


public class Share extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("share")) {
            String text = args.getString(0);
            String title = args.getString(1);
            String mimetype = args.getString(2);
            int action = args.getInt(3);
            this.share(text, title, mimetype, action, callbackContext);
            return true;
        }
        return false;
    }

    private void share(String text, String title, String mimetype, int action, CallbackContext callbackContext) {
      try {
        Intent sendIntent = new Intent();
        if (mimetype.equals("text/plain")) {
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        } else {
            sendIntent.setAction(Intent.ACTION_SEND);
            Context context = this.cordova.getActivity().getApplicationContext();
            Uri fileUri = FileProvider.getUriForFile(context, "com.pinkbike.trailforks.provider", new File(Uri.parse(text).getPath()));
            sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            sendIntent.putExtra(Intent.EXTRA_STREAM, fileUri);

            context.grantUriPermission("com.garmin.android.apps.connectmobile", fileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        sendIntent.setType(mimetype);
        cordova.getActivity().startActivity(Intent.createChooser(sendIntent, title));
        callbackContext.success();
        } catch(Error e) {
            callbackContext.error(e.getMessage());
        }
    }
}
