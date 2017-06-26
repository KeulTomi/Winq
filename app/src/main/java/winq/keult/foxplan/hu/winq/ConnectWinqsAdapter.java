package winq.keult.foxplan.hu.winq;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.keult.networking.model.DateData;

import java.util.ArrayList;

/**
 * Created by tomi on 2017.06.21..
 */

public class ConnectWinqsAdapter extends ArrayAdapter<DateData> {


    public ConnectWinqsAdapter(@NonNull Context context, ArrayList<DateData> dateDatas) {
        super(context, 0, dateDatas);
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

        DateData currentItem = getItem(position);

        if (currentItem.getImage() != "") {
            //Ha van képe a felhasználónak akkor betöltjük
            Glide.with(getContext())
                    .load(currentItem.getImage())
                    .asBitmap()
                    .into((ImageView) listItemView.findViewById(R.id.connect_item_image));
        }

        TextView userName = (TextView) listItemView.findViewById(R.id.connect_item_fullname_of_current_user);
        userName.setText(currentItem.getFullName());

        TextView countryShortName = (TextView) listItemView.findViewById(R.id.connect_item_country);
        countryShortName.setText("HUN");

        return listItemView;
    }
}
