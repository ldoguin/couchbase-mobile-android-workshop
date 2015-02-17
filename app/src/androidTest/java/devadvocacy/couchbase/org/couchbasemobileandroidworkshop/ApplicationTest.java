package devadvocacy.couchbase.org.couchbasemobileandroidworkshop;

import android.test.ApplicationTestCase;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;

import java.util.HashMap;
import java.util.Map;

import devadvocacy.couchbase.org.couchbasemobileandroidworkshop.domain.Presentation;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    private Database database;

    public ApplicationTest() throws CouchbaseLiteException {
        super(Application.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        createApplication();
        database = getApplication().getDatabase();
    }

    public void testDatabase() throws CouchbaseLiteException {
        // This will create a document using the given id
        // Be careful, despite the fact that we use a getter, the document is not yet persisted
        Document document = database.getDocument("myDocumentId");
        assertNull(database.getExistingLocalDocument(document.getId()));
        Map<String, Object> properties = new HashMap<>();
        properties.put("title", "doc title");
        // The call to putProperties will create a new local revision of the document, thus
        // persisting it.
        document.putProperties(properties);
        assertNotNull(database.getExistingDocument(document.getId()));
    }

    public void testPresentation() throws Exception {

        // Start by creating a presentation document
        Presentation.createPresentation(database,"Doc1 Title","Abstract");
        // Create another presentation through the save method
        Presentation pres2 = new Presentation(database);
        pres2.setTitle("Doc2 Title");
        pres2.setPresentationAbstract("presentationAbstract");
        pres2.save();
        // use a specific database query that returns all the documents
        QueryEnumerator qe = database.createAllDocumentsQuery().run();
        // Make sure we see our previously created document
        Boolean foundDoc1 = false;
        Boolean foundDoc2 = false;
        while (qe.hasNext()) {
            QueryRow qr = qe.next();
            Document doc = qr.getDocument();
            Presentation presentation = Presentation.from(doc);
            if (presentation.getTitle().equals("Doc1 Title")) {
                foundDoc1 = true;
            } else if (presentation.getTitle().equals("Doc2 Title")) {
                foundDoc2 = true;
            }
        }
        assertTrue(foundDoc1);
        assertTrue(foundDoc2);
     }

    @Override
    public void tearDown() throws Exception {
        // start fresh for each test, be careful as it empty the content of your local database
        database.delete();
        super.tearDown();
    }
}