package ke.co.mobimech.mpesab2c2;

import android.support.annotation.NonNull;

public interface MpesaLib<Result> {
    void onResult(@NonNull Result result);

    void onError(String error);
}
