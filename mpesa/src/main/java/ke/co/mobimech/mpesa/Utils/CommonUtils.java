package ke.co.mobimech.mpesa.Utils;

import android.util.Base64;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CommonUtils {
    public static final int CONNECTION_TIMEOUT = 60000;

    public static final int FETCH_TIMEOUT = 60000;

    public static final int INPUT_TIMEOUT = 60000;


    public static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber.equals("")) {
            return "";
        }
        if (phoneNumber.length() < 11 & phoneNumber.startsWith("0")) {
            String p = phoneNumber.replaceFirst("^0", "254");
            return p;
        }
        if (phoneNumber.length() == 13 && phoneNumber.startsWith("+")) {
            return phoneNumber.replaceFirst("^+", "");
        }
        return phoneNumber;
    }


    public static String getTimestamp() {
        return new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
    }

    public static String getPassword(String businessShortCode, String passKey, String timeStamp) {
        String password = businessShortCode + passKey + timeStamp;
        return Base64.encodeToString(password.getBytes(), Base64.NO_WRAP);
    }
}
