package film.com.viwafo.example.Util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * Created by minhl on 06/07/2017.
 */

public class UtilPermissions {
    public static boolean hasPermissions(Context context, String... allPermissionNeeded) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if ((context != null) && (allPermissionNeeded != null))
                for (String permission : allPermissionNeeded)
                    if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                        return false;
        }
        return true;

    }
}