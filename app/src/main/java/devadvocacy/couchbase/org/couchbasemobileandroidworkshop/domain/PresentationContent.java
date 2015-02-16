package devadvocacy.couchbase.org.couchbasemobileandroidworkshop.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ldoguin on 16/02/15.
 */
public class PresentationContent {
    /**
     * An array of sample (dummy) items.
     */
    public static List<Presentation> ITEMS = new ArrayList<Presentation>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, Presentation> ITEM_MAP = new HashMap<String, Presentation>();

    static {
        // Add 3 sample items.
        addItem(new Presentation(null));
        addItem(new Presentation(null));
        addItem(new Presentation(null));
    }

    private static void addItem(Presentation presentation) {
        ITEMS.add(presentation);
        ITEM_MAP.put(presentation.getId(), presentation);
    }

}
