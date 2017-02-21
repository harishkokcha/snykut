package com.namdev.sanyukt.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.namdev.sanyukt.R;

/**
 * Created by harish on 12/24/2016.
 */
public class DialogUtils {

    public void forgotPassDialog(final Activity mActivity, Boolean cancelOutsideClick) {

        final Dialog doneDialog = new Dialog(mActivity);
        doneDialog.setCanceledOnTouchOutside(!cancelOutsideClick);
        doneDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        doneDialog.setContentView(R.layout.forgot_password_dialog);
        final EditText editText=(EditText)doneDialog.findViewById(R.id.id_forgot_password_email_txt);
        TextView sendButton=(TextView) doneDialog.findViewById(R.id.id_forgot_password_dialog_action_btn);
        ImageView closeBtn = (ImageView)doneDialog.findViewById(R.id.id_forgot_password_dialog_close_ic);
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
                if(formUtils.isEmailAddress(editText.getText().toString(),true)){
                    new CommonUtils().showToast(mActivity,"We have Send you email for reset your password thankyou");
                    doneDialog.dismiss();
                }

            }
        });
        doneDialog.show();
    }
}

