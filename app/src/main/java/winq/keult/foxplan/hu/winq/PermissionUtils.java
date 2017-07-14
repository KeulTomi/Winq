package winq.keult.foxplan.hu.winq;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

/**
 * Engedélyek kezelését segítő metódusok osztálya
 */
public abstract class PermissionUtils {

    /**
     * Engedély kérése a helyzethez való hozzáféréshez
     * Ha korábban a felhasználó letiltotta a hozzáférést akkor a rendszer javaslatot tesz
     * arra, hogy részletesebb magyarázattal kérjünk engedélyt a hozzáféréshez.
     */
    public static void requestPermission(FragmentActivity activity, int requestId,
                                         String permission) {
        boolean explanationNecessary = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);

        // Ha a felhasználó korábban megtiltotta a hozzáférést a helyzethez akkor részletesebb magyarázat szükséges
        if (explanationNecessary) {
            // Részletes magyarázat szükséges, dialógus ablakának megjelenítése.
            ExplanationDialog.newInstance(requestId)
                    .show(activity.getSupportFragmentManager(), "dialog");
        } else {
            // Nem szükséges részletes magyarázat, standard rendszer dialógusablak megjelenítése
            // (csak API23 fölött nyílik meg engedélykérő ablak, API23 alatt telepítéskor dönt a felhasználó
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestId);

        }
    }

    /**
     * Egy dialógusablak, ami elmagyarázza a felhasználónak, hogy az app miért akar hozzáférni a helyzet adatokhoz
     * A felhasználó válaszát OnRequestPermissionsResultCallback függvény fogadja a MainActivity-ben
     */
    public static class ExplanationDialog extends DialogFragment {

        // Ezzel a kulccsal hívódik meg a dialógus ablak bezárása után a OnRequestPermissionsResultCallback f
        // üggvény a MainActivity-ben.
        private static final String ARGUMENT_PERMISSION_REQUEST_CODE = "requestCode";

        /**
         * Dialógus ablak megjelenítése a részletes
         */
        public static ExplanationDialog newInstance(int requestCode) {
            Bundle arguments = new Bundle();
            arguments.putInt(ARGUMENT_PERMISSION_REQUEST_CODE, requestCode);
            ExplanationDialog dialog = new ExplanationDialog();
            dialog.setArguments(arguments);
            return dialog;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Bundle arguments = getArguments();
            final int requestCode = arguments.getInt(ARGUMENT_PERMISSION_REQUEST_CODE);

            return new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.permission_rationale_location)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Ok-ra kattintás után hozzáférés kérése a rendszertől
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                    requestCode);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, null)
                    .create();
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            super.onDismiss(dialog);
        }
    }
}