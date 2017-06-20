package winq.keult.foxplan.hu.winq;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Fotó feltöltésekor vagy készítésekor választéklista a fotó jellegéhez
 */

public class UploadSelectorDialog extends DialogFragment {

    UploadSelectorListener mUploadSelectorListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mUploadSelectorListener = (UploadSelectorListener) activity;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.pick_photo_type)
                .setItems(R.array.photo_types_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {

                            case 0:
                                mUploadSelectorListener.onProfileUploadSelected(UploadSelectorDialog.this);
                                break;
                            case 1:
                                mUploadSelectorListener.onImageUploadSelected(UploadSelectorDialog.this);
                                break;
                            case 2:
                                mUploadSelectorListener.onStoryUploadSelected(UploadSelectorDialog.this);
                                break;
                        }
                    }
                });
        return builder.create();
    }

    // Ezt az interfészt a Profile activity implementálja
    public interface UploadSelectorListener {
        public void onProfileUploadSelected(DialogFragment dialogFragment);

        public void onImageUploadSelected(DialogFragment dialogFragment);

        public void onStoryUploadSelected(DialogFragment dialogFragment);
    }
}
