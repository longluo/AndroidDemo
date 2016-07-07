package com.longluo.demo.qrcode.zxing.client.android.result;


import android.app.Activity;
import android.telephony.PhoneNumberUtils;

import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.SMSParsedResult;
import com.longluo.demo.R;

/**
 * Handles SMS addresses, offering a choice of composing a new SMS or MMS message.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class SMSResultHandler extends ResultHandler {
    private static final int[] buttons = {
            R.string.button_sms,
            R.string.button_mms
    };

    public SMSResultHandler(Activity activity, ParsedResult result) {
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
        SMSParsedResult smsResult = (SMSParsedResult) getResult();
        switch (index) {
            case 0:
                // Don't know of a way yet to express a SENDTO intent with multiple recipients
                sendSMS(smsResult.getNumbers()[0], smsResult.getBody());
                break;
            case 1:
                sendMMS(smsResult.getNumbers()[0], smsResult.getSubject(), smsResult.getBody());
                break;
        }
    }

    @Override
    public CharSequence getDisplayContents() {
        SMSParsedResult smsResult = (SMSParsedResult) getResult();
        StringBuilder contents = new StringBuilder(50);
        String[] rawNumbers = smsResult.getNumbers();
        String[] formattedNumbers = new String[rawNumbers.length];
        for (int i = 0; i < rawNumbers.length; i++) {
            formattedNumbers[i] = PhoneNumberUtils.formatNumber(rawNumbers[i]);
        }
        ParsedResult.maybeAppend(formattedNumbers, contents);
        ParsedResult.maybeAppend(smsResult.getSubject(), contents);
        ParsedResult.maybeAppend(smsResult.getBody(), contents);
        return contents.toString();
    }

    @Override
    public int getDisplayTitle() {
        return R.string.result_sms;
    }
}
