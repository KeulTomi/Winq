package winq.keult.foxplan.hu.winq;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.keult.networking.model.ProfileData;

import java.util.ArrayList;

/**
 * Created by tomi on 2017.06.21..
 */

public class ConnectSearchAdapter extends ArrayAdapter<ProfileData> {


    public ConnectSearchAdapter(@NonNull Context context, ArrayList<ProfileData> friendsDatas) {
        super(context, 0, friendsDatas);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        final View itemView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.connect_list_item, parent, false);

            itemView = listItemView;


        }

        ProfileData currentItem = getItem(position);

        /*if (currentItem.getImage() != "") {
            //Ha van képe a felhasználónak akkor betöltjük
            Glide.with(getContext())
                    .load(currentItem.getImage())
                    .asBitmap()
                    .into((ImageView) listItemView.findViewById(R.id.connect_item_image));
        }*/

        //TextView userName = (TextView) listItemView.findViewById(R.id.connect_item_fullname_of_current_user);
        //userName.setText(currentItem.getFullName());

//        TextView countryShortName = (TextView) listItemView.findViewById(R.id.connect_item_country);
        //      countryShortName.setText(currentItem.getFullName());

        return listItemView;
    }
}
