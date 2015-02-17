package devadvocacy.couchbase.org.couchbasemobileandroidworkshop.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.couchbase.lite.Document;
import com.couchbase.lite.LiveQuery;

import devadvocacy.couchbase.org.couchbasemobileandroidworkshop.LiveQueryAdapter;

/**
 * Created by ldoguin on 17/02/15.
 */
public class PresentationAdapter extends LiveQueryAdapter {

    public PresentationAdapter(LiveQuery query, Context context) {
        super(query, context);
    }

    @Override
    // Display a Message as an Item in the list View
    public View getView(int position, View convertView, ViewGroup parent) {
        // Load the View if not done so already from the view_presentation.xml
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(android.R.layout.simple_list_item_activated_2 , null);
        }

        // Get the document currently to be displayed
        final Document document = (Document) getItem(position);

        // make sure this is valid
        if (document == null || document.getCurrentRevision() == null) {
            return convertView;
        }

        // Turn the document into a message model we can operate on
        final Presentation presentation = Presentation.from(document);

        TextView titleView = (TextView) convertView.findViewById(android.R.id.text1);
        titleView.setText(presentation.getTitle());

        TextView titleView2 = (TextView) convertView.findViewById(android.R.id.text2);
        titleView2.setText(presentation.getPresentationAbstract());

        return convertView;

    }
}
