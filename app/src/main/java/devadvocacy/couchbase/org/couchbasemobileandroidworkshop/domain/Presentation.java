package devadvocacy.couchbase.org.couchbasemobileandroidworkshop.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ldoguin on 16/02/15.
 */
public class Presentation {

    public static final String type = "presentation";
    private String id;
    private String title;
    private String presentationAbstract;
    private Date createdAt;

    public Presentation(String id, String title, String presentationAbstract) {
        this.id = id;
        this.title = title;
        this.presentationAbstract = presentationAbstract;
        this.createdAt = new Date();
    }

    @Override
    public String toString() {
        return getTitle();
    }

    public static String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
