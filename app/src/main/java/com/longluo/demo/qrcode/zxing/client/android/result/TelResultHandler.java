package com.longluo.demo.qrcode.zxing.client.android.result;

import android.app.Activity;
import android.telephony.PhoneNumberUtils;

import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.TelParsedResult;
import com.longluo.demo.R;

/**
 * Offers relevant actions for telephone numbers.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class TelResultHandler extends ResultHandler {
    private static final int[] buttons = {
            R.string.button_dial,
            R.string.button_add_contact
    };

    public TelResultHandler(Activity activity, ParsedResult result) {
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
        TelParsedResult telResult = (TelParsedResult) getResult();
        switch (index) {
            case 0:
                dialPhoneFromUri(telResult.getTelURI());
                // When dialer comes up, it allows underlying display activity to continue or something,
                // but app can't get camera in this state. Avoid issues by just quitting, only in the
                // case of a phone number
                getActivity().finish();
                break;
            case 1:
                String[] numbers = new String[1];
                numbers[0] = telResult.getNumber();
                addPhoneOnlyContact(numbers, null);
                break;
    }
    }

    // Overriden so we can take advantage of Android's phone number hyphenation routines.
    @Override
    public CharSequence getDisplayContents() {
        String contents = getResult().getDisplayResult();
        contents = contents.replace("\r", "");
        return PhoneNumberUtils.formatNumber(contents);
    }

    @Override
    public int getDisplayTitle() {
        return R.string.result_tel;
    }
}
