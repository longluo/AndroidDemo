package com.longluo.demo.qrcode.zxing.client.android.clipboard;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;

public final class ClipboardInterface {

    private static final String TAG = ClipboardInterface.class.getSimpleName();

    private ClipboardInterface() {
    }

    public static CharSequence getText(Context context) {
        ClipboardManager clipboard = getManager(context);
        ClipData clip = clipboard.getPrimaryClip();
        return hasText(context) ? clip.getItemAt(0).coerceToText(context) : null;
    }

    public static void setText(CharSequence text, Context context) {
        if (text != null) {
            try {
                getManager(context).setPrimaryClip(ClipData.newPlainText(null, text));
            } catch (NullPointerException | IllegalStateException e) {
                // Have seen this in the wild, bizarrely
                Log.w(TAG, "Clipboard bug", e);
            }
        }
    }

    public static boolean hasText(Context context) {
        ClipboardManager clipboard = getManager(context);
        ClipData clip = clipboard.getPrimaryClip();
        return clip != null && clip.getItemCount() > 0;
    }

    private static ClipboardManager getManager(Context context) {
        return (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    }

}
