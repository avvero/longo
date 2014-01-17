package com.avvero.longo

import com.avvero.db.utils.MongoUtils
import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBCursor
import org.apache.commons.logging.LogFactory
import org.atmosphere.cpr.Broadcaster

/**
 *
 * @author fxdev-belyaev-ay
 */
class MongoCollector extends Collector {

    private static final log = LogFactory.getLog(this)

    DB db
    DBCursor cursor

    MongoConnectionConfig config

    MongoCollector(MongoConnectionConfig config) {
        this.config = config
    }

    @Override
    String getName() {
        return config.getKey()
    }

    @Override
    void _start() {
        log.info("Start MongoCollector...")
        db = MongoUtils.getDB("127.0.0.1", 27017, "longo")
        cursor = MongoUtils.getTailableCursor(db, "test")
        new Thread(new Runnable() {
            @Override
            void run() {
                try {
                    while (cursor.hasNext()) {
                        BasicDBObject doc = (BasicDBObject) cursor.next()
                        alertListener(doc)
                    }
                } finally {
                    stop()
                }
            }
        }).start()
    }

    @Override
    void _stop() {
        log.info("Stop MongoCollector...")
        try {
            if (cursor != null) cursor.close();
        } catch (final Throwable t) {
        }
        db.requestDone();
    }

    @Override
    protected Listener getNewListener(Broadcaster broadcaster) {
        return new Listener() {
            void handle(Object o) {
                BasicDBObject dbObject = (BasicDBObject) o;
                def log = dbObject.toString()
                broadcaster.broadcast(log)
            }
        }
    }

}
