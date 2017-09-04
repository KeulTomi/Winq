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
import com.example.keult.networking.model.EventData;

import java.util.ArrayList;

/**
 * Created by tomi on 2017.06.10..
 */

public class EventsJoinedAdapter extends ArrayAdapter<EventData> {


    public EventsJoinedAdapter(@NonNull Context context, ArrayList<EventData> eventDatas) {
        super(context, 0, eventDatas);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        final View itemView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.events_list_item, parent, false);
            ScaleHelper.scaleViewAndChildren(listItemView.findViewById(R.id.events_list_item_root), Winq.getScaleX(), Winq.getScaleY());

            itemView = listItemView;


        }

        EventData currentItem = getItem(position);

        if (currentItem.getImage() != "") {
            //Ha van képe az eventnek akkor betöltjük
            Glide.with(getContext())
                    .load(currentItem.getImage())
                    .asBitmap()
                    .into((ImageView) listItemView.findViewById(R.id.events_item_image));
        }

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
        String eventsDateYear = currentItem.getDate().substring(0, 4);
        String eventsDateMonth = currentItem.getDate().substring(5, 7);
        String eventsDateDay = currentItem.getDate().substring(8, 10);

        timeTextView.setText(eventsDateYear + eventsDateMonth + "." + eventsDateDay);

        TextView countryTextView = (TextView) listItemView.findViewById(R.id.events_item_country);
        countryTextView.setText("/HUN/");

        return listItemView;
    }
}
