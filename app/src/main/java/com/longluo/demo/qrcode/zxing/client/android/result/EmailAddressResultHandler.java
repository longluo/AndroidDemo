package com.longluo.demo.qrcode.zxing.client.android.result;

import android.app.Activity;

import com.google.zxing.client.result.EmailAddressParsedResult;
import com.google.zxing.client.result.ParsedResult;
import com.longluo.demo.R;

/**
 * Handles email addresses.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class EmailAddressResultHandler extends ResultHandler {
    private static final int[] buttons = {
            R.string.button_email,
            R.string.button_add_contact
    };

    public EmailAddressResultHandler(Activity activity, ParsedResult result) {
        super(activity, result);
    }

    @Override
    public int getButtonCount() {
        return buttons.length;
    }

    @Override
    public int getButtonText(int index) {
        return buttons[index];
    }

    @Override
    public void handleButtonPress(int index) {
        EmailAddressParsedResult emailResult = (EmailAddressParsedResult) getResult();
        switch (index) {
            case 0:
                sendEmailFromUri(emailResult.getMailtoURI(),
                        emailResult.getEmailAddress(),
                        emailResult.getSubject(),
                        emailResult.getBody());
                break;
            case 1:
                String[] addresses = new String[1];
                addresses[0] = emailResult.getEmailAddress();
                addEmailOnlyContact(addresses, null);
                break;
        }
    }

    @Override
    public int getDisplayTitle() {
        return R.string.result_email_address;
    }
}
