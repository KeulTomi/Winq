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
import com.example.keult.networking.model.ProfileData;

import java.util.ArrayList;
import java.util.Calendar;

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
            ScaleHelper.scaleViewAndChildren(listItemView.findViewById(R.id.connect_list_item_root), Winq.getScaleX(), Winq.getScaleY());

            itemView = listItemView;


        }

        ProfileData currentItem = getItem(position);

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
        countryShortName.setText(currentItem.getUserCountryShort());

        TextView ageOfUser = (TextView) listItemView.findViewById(R.id.connect_item_age_of_current_user);
        int userBornYear = Integer.parseInt(currentItem.getUserborn().substring(0, 4));
        Calendar c = Calendar.getInstance();
        int currentYear = c.get(Calendar.YEAR);

        ageOfUser.setText(String.valueOf(currentYear - userBornYear));

        return listItemView;
    }
}
