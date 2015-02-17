package devadvocacy.couchbase.org.couchbasemobileandroidworkshop;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.replicator.Replication;
import com.couchbase.lite.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import devadvocacy.couchbase.org.couchbasemobileandroidworkshop.domain.Presentation;

/**
 * Created by ldoguin on 16/02/15.
 */
public class Application extends android.app.Application {

    public static final String TAG = "AndroidWorkshop";
    private static final String DATABASE_NAME = "conference";
    /* if you use the android emulator, the host IP should be 10.0.2.2 */
    private static final String SYNC_URL_HTTP = "http://10.0.2.2:4984/db";
    private Replication pull;
    private Replication push;

    private Manager manager;
    private Database database;

    public Application() {
        super();
    }

    private void initDatabase() {
        try {
            Manager.enableLogging(TAG, Log.VERBOSE);
            Manager.enableLogging(Log.TAG, Log.VERBOSE);
            Manager.enableLogging(Log.TAG_SYNC_ASYNC_TASK, Log.VERBOSE);
            Manager.enableLogging(Log.TAG_SYNC, Log.VERBOSE);
            Manager.enableLogging(Log.TAG_QUERY, Log.VERBOSE);
            Manager.enableLogging(Log.TAG_VIEW, Log.VERBOSE);
            Manager.enableLogging(Log.TAG_DATABASE, Log.VERBOSE);
            manager = new Manager(new AndroidContext(getApplicationContext()), Manager.DEFAULT_OPTIONS);
        } catch (IOException e) {
            Log.e(TAG, "Cannot create Manager object", e);
            return;
        }
        try {
            /* Create Database will return the existing database or create it if it does not
             exist. */
            database = manager.getDatabase(DATABASE_NAME);
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Cannot get Database", e);
        }
    }

    private void setupSync() throws MalformedURLException {
        URL url = new URL(SYNC_URL_HTTP);

        pull = database.createPullReplication(url);
        push = database.createPushReplication(url);

        pull.setContinuous(true);
        push.setContinuous(true);

        pull.start();
        push.start();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Application.TAG, "Application State: onCreate()");
        initDatabase();
        try {
            setupSync();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public Database getDatabase() {
        return this.database;
    }

}
