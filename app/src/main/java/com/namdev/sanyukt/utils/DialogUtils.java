package com.namdev.sanyukt.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.namdev.sanyukt.R;
import com.namdev.sanyukt.adapters.ListBaseAdapter;

/**
 * Created by harish on 12/24/2016.
 */
public class DialogUtils {

    public void forgotPassDialog(final Activity mActivity, Boolean cancelOutsideClick) {

        final Dialog doneDialog = new Dialog(mActivity);
        doneDialog.setCanceledOnTouchOutside(!cancelOutsideClick);
        doneDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        doneDialog.setContentView(R.layout.forgot_password_dialog);
        final EditText editText = (EditText) doneDialog.findViewById(R.id.id_forgot_password_email_txt);
        TextView sendButton = (TextView) doneDialog.findViewById(R.id.id_forgot_password_dialog_action_btn);
        ImageView closeBtn = (ImageView) doneDialog.findViewById(R.id.id_forgot_password_dialog_close_ic);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doneDialog.dismiss();
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FormUtils formUtils = new FormUtils(mActivity);
                if (formUtils.isEmailAddress(editText.getText().toString(), true)) {
                    new CommonUtils().showToast(mActivity, "We have Send you email for reset your password thankyou");
                    doneDialog.dismiss();
                }

            }
        });
        doneDialog.show();
    }

    public void showRelationDialog(Activity hostActivity, final TextView relationTxtVw, String[] array, String headerString) {

        final Dialog dialog = new Dialog(hostActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.select_list_dialog);
        TextView header = (TextView) dialog.findViewById(R.id.id_dialog_box_hdr);

        header.setText(headerString);

        final String[] mArray = array;

        final BaseAdapter mAdapter = new ListBaseAdapter(hostActivity
                , mArray);
        final ImageView listCrossBtn = (ImageView) dialog.findViewById(R.id.id_select_relation_dialog_close_ic);
        ListView listView = (ListView) dialog.findViewById(R.id.id_city_listView);
        listView.setAdapter(mAdapter);
        listCrossBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                relationTxtVw.setText(mArray[position]);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}

