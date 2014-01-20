package com.avvero.bson;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.log4j.spi.ThrowableInformation;
import org.log4mongo.LoggingEventBsonifierImpl;

import java.util.Arrays;
import java.util.List;

/**
 * @author fxdev-belyaev-ay
 */
public class LoggingEventBsonifierImplExt extends LoggingEventBsonifierImpl {

    private static final String KEY_THROWABLES = "throwables";
    private static final String KEY_THROWABLE_RPS = "throwable_rps";

    /**
     * Adds the ThrowableInformation object to an existing BSON object.
     *
     * @param bson
     *            The BSON object to add the throwable info to <i>(must not be null)</i>.
     * @param throwableInfo
     *            The ThrowableInformation object to add to the BSON object <i>(may be null)</i>.
     */
    @SuppressWarnings(value = "unchecked")
    protected void addThrowableInformation(DBObject bson, final ThrowableInformation throwableInfo) {
        if (throwableInfo != null) {
            Throwable currentThrowable = throwableInfo.getThrowable();
            List throwables = new BasicDBList();

            while (currentThrowable != null) {
                DBObject throwableBson = bsonifyThrowable(currentThrowable);

                if (throwableBson != null) {
                    throwables.add(throwableBson);
                }

                currentThrowable = currentThrowable.getCause();
            }

            if (throwables.size() > 0) {
                bson.put(KEY_THROWABLES, throwables);
            }

            String[] rp = throwableInfo.getThrowableStrRep();
            if (rp.length > 0) {
                List rps = new BasicDBList();
                for (String entry : rp) {
                    rps.add(entry);
                }
                bson.put(KEY_THROWABLE_RPS, rps);
            }
        }
    }

}
