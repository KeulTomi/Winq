package winq.keult.foxplan.hu.winq;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.keult.networking.model.EventData;

import java.util.ArrayList;

/**
 * Created by tomi on 2017.06.13..
 */

public class EventsSearchAdapter extends ArrayAdapter<EventData> {


    public EventsSearchAdapter(@NonNull Context context, ArrayList<EventData> eventDatas) {
        super(context, 0, eventDatas);
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
        //Ha hosszabb a title mint 19 betű akkor utána már csak ...-ot irunk
        if (currentItem.getTitle().length() > 11) {
            String cuttedText = currentItem.getTitle().substring(0, 12);
            nameTextView.setText(cuttedText + "...");
        } else {
            nameTextView.setText(currentItem.getTitle());
        }

        TextView placeTextView = (TextView) listItemView.findViewById(R.id.events_item_place);
        //Ha hosszabb a location mint 19 betű akkor utána már csak ...-ot irunk
        if (currentItem.getLocation().length() > 19) {
            String cuttedText = currentItem.getLocation().substring(0, 12);
            placeTextView.setText(cuttedText + "...");
        } else {
            placeTextView.setText(currentItem.getLocation());
        }

        TextView timeTextView = (TextView) listItemView.findViewById(R.id.events_eventstime);
        //Ha hosszabb a date mint 19 betű akkor utána már csak ...-ot irunk
        if (currentItem.getDate().length() > 19) {
            String cuttedText = currentItem.getDate().substring(0, 19);
            timeTextView.setText(cuttedText + "...");
        } else {
            timeTextView.setText(currentItem.getDate());
        }

        return listItemView;
    }
}
