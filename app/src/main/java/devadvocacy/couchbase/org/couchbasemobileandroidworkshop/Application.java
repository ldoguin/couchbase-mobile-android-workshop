package devadvocacy.couchbase.org.couchbasemobileandroidworkshop;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.util.Log;

import java.io.IOException;

/**
 * Created by ldoguin on 16/02/15.
 */
public class Application extends android.app.Application {

    public static final String TAG = "AndroidWorkshop";
    private static final String DATABASE_NAME = "conference";

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
            /**
             * Create Database will return the existing database or create it if it does not exist.
             */
            database = manager.getDatabase(DATABASE_NAME);
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Cannot get Database", e);
            return;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Application.TAG, "Application State: onCreate()");
        initDatabase();
    }

    public Database getDatabase() {
        return this.database;
    }

}
