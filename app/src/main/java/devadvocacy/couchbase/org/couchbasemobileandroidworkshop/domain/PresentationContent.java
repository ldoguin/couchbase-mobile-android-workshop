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
        addItem(new Presentation("1", "Item 1", "abstract 1"));
        addItem(new Presentation("2", "Item 2", "abstract 2"));
        addItem(new Presentation("3", "Item 3", "abstract 3"));
    }

    private static void addItem(Presentation presentation) {
        ITEMS.add(presentation);
        ITEM_MAP.put(presentation.getId(), presentation);
    }

}
