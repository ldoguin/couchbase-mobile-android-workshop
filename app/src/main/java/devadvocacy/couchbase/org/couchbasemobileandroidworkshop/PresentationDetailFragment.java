package devadvocacy.couchbase.org.couchbasemobileandroidworkshop;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.couchbase.lite.Database;
import com.couchbase.lite.Document;

import devadvocacy.couchbase.org.couchbasemobileandroidworkshop.domain.Presentation;

/**
 * A fragment representing a single Presentation detail screen.
 * This fragment is either contained in a {@link PresentationListActivity}
 * in two-pane mode (on tablets) or a {@link PresentationDetailActivity}
 * on handsets.
 */
public class PresentationDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Presentation mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PresentationDetailFragment() {
    }

    private Database getDatabase() {
        Application application = (Application) getActivity().getApplication();
        return application.getDatabase();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.

            Document doc = getDatabase().getDocument(getArguments().getString(ARG_ITEM_ID));
            mItem = Presentation.from(doc);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_presentation_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.presentation_detail)).setText(mItem.getTitle());
        }

        return rootView;
    }
}
