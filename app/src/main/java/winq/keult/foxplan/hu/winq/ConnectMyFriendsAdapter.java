package winq.keult.foxplan.hu.winq;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.keult.networking.model.FriendsData;

import java.util.ArrayList;

/**
 * Created by tomi on 2017.06.21..
 */

public class ConnectMyFriendsAdapter extends ArrayAdapter<FriendsData> {


    public ConnectMyFriendsAdapter(@NonNull Context context, ArrayList<FriendsData> friendsDatas) {
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

        FriendsData currentItem = getItem(position);

        //TextView userName = (TextView) listItemView.findViewById(R.id.connect_item_fullname_of_current_user);
        //userName.setText(currentItem.getFullName());

        //TextView countryShortName = (TextView) listItemView.findViewById(R.id.connect_item_country);
        //countryShortName.setText(currentItem.getFullName());

        return listItemView;
    }
}
