package uom.android.dev;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uom.android.dev.LastFmJson.Image;
import uom.android.dev.LastFmJson.Track;

/**
 * Created by vasil on 22-Nov-17.
 */

public class SearchResultsAdapter extends ArrayAdapter<Track> {

    private Context context;
    private List<Track> values;

    public SearchResultsAdapter(Context context, List<Track> values){
        super(context, R.layout.list_item_pagination, values);

        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item_pagination, parent, false);
        }

        TextView textView = (TextView) row.findViewById(R.id.list_item_pagination_text);
        //ImageView imageView = (ImageView) row.findViewById(R.id.list_item_pagination_image);
        Track item = values.get(position);
        String message = item.getName();
        ArrayList<Image> images = new ArrayList<>();

        for(Image image_results : item.getImage()){
            Image image = new Image(
                    image_results.getText(),
                    image_results.getSize(),
                    image_results.getAdditionalProperties());
            images.add(image);
        }
        textView.setText(item.getName() + " - " + item.getArtist());
        //imageView.setBackground(images.get(0));

        return row;
    }
}
