package ke.co.mobimech.mpesa;

import android.support.annotation.NonNull;

public interface MpesaLib<Result> {
    void onResult(@NonNull Result result);

    void onError(String error);
}
