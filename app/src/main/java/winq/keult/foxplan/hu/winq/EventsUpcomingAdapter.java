package winq.keult.foxplan.hu.winq;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.keult.networking.model.EventData;

/**
 * Created by tomi on 2017.06.13..
 */

public class EventsUpcomingAdapter extends ArrayAdapter<EventData> {


    public EventsUpcomingAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        final View itemView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.events_list_item, parent, false);

            itemView = listItemView;


        }

        EventData currentItem = getItem(position);

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.events_item_name);
        nameTextView.setText(currentItem.getTitle());

        TextView placeTextView = (TextView) listItemView.findViewById(R.id.events_item_place);
        placeTextView.setText(currentItem.getLocation());

        TextView timeTextView = (TextView) listItemView.findViewById(R.id.events_eventstime);
        timeTextView.setText(currentItem.getDate());

        TextView countryTextView = (TextView) listItemView.findViewById(R.id.events_item_country);
        countryTextView.setText("/HUN/");

        return listItemView;
    }
}
