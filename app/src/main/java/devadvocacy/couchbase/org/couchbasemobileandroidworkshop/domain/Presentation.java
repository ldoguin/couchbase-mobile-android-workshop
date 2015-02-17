package devadvocacy.couchbase.org.couchbasemobileandroidworkshop.domain;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Emitter;
import com.couchbase.lite.Mapper;
import com.couchbase.lite.Query;
import com.couchbase.lite.View;
import com.couchbase.lite.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ldoguin on 16/02/15.
 */
public class Presentation {

    public static final String VIEW_NAME = "presentation_view";
    public static final String TYPE = "presentation";
    private Database database;
    private Document sourceDocument;
    private String title;
    private String presentationAbstract;
    private Date createdAt;


    public Presentation(Database database) {
        this.database = database;
        this.createdAt = new Date();
        this.title = "defaultTitle";
        this.presentationAbstract = "defaultPresentationAbstract";
    }

    public static Query findAllByDate(Database database) {
        View view = database.getView(VIEW_NAME);
        if (view.getMap() == null) {
            view.setMap(new Mapper() {
                @Override
                public void map(Map<String, Object> document, Emitter emitter) {
                    if (TYPE.equals(document.get("type"))) {
                        emitter.emit(document.get("created_at"), document);
                    }
                }
            }, "1");
        }
        return view.createQuery();
    }

    public static void createPresentation(Database database, String title, String presentationAbstract) throws CouchbaseLiteException {
        Document doc = database.createDocument();
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("title", title);
        properties.put("channels", "all");
        properties.put("type", TYPE);
        properties.put("presentationAbstract", presentationAbstract);
        properties.put("created_at", new Date());
        doc.putProperties(properties);
    }

    public static Presentation from(Document document) {
        Presentation presentation = new Presentation(document.getDatabase());
        if (document.getProperty("title") != null) {
            presentation.setTitle((String) document.getProperty("title"));
        }
        if (document.getProperty("presentationAbstract") != null) {
            presentation.setPresentationAbstract((String) document.getProperty("presentationAbstract"));
        }
        if (document.getProperty("created_at") != null) {
            long createdAtL = 0;
            Object createdAt = document.getProperty("created_at");
            if (createdAt instanceof Double) {
                createdAtL = ((Double) createdAt).longValue();
            }
            if (createdAt instanceof Long) {
                createdAtL = (Long) createdAt;
            }


            presentation.setCreatedAt(new Date(createdAtL));
        }
        presentation.setSourceDocument(document);
        return presentation;
    }

    public void save() throws CouchbaseLiteException {
        Map<String, Object> properties = new HashMap<String, Object>();
        Document document;
        if (sourceDocument == null) {
            document = database.createDocument();
        } else {
            document = sourceDocument;
            properties.putAll(sourceDocument.getProperties());
        }
        properties.put("type", TYPE);
        properties.put("created_at", createdAt.getTime());
        properties.put("title", title);
        properties.put("presentationAbstract", presentationAbstract);
        try {
            document.putProperties(properties);
        } catch (CouchbaseLiteException e) {
            Log.e("PRESENTATION", "Failed to save");
            throw e;
        }
    }

    @Override
    public String toString() {
        return getTitle();
    }

    public static String getType() {
        return TYPE;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPresentationAbstract() {
        return presentationAbstract;
    }

    public void setPresentationAbstract(String presentationAbstract) {
        this.presentationAbstract = presentationAbstract;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Document getSourceDocument() {
        return sourceDocument;
    }

    public void setSourceDocument(Document sourceDocument) {
        this.sourceDocument = sourceDocument;
    }

}
