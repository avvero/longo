package com.avvero.longo

import com.avvero.db.utils.MongoUtils
import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBCursor
import grails.transaction.Transactional

@Transactional
class MongoLogService {

    DB db
    DBCursor cursor

    def listeners = []

    def start() {
        log.info("Start MongoLogService...")
        new Thread(new Runnable() {
            @Override
            void run() {
                db = MongoUtils.getDB("127.0.0.1", 27017, "longo")
                cursor = MongoUtils.getTailableCursor(db, "test")
                try {
                    while (cursor.hasNext()) {
                        BasicDBObject doc = (BasicDBObject)cursor.next()
                        alertListener(doc)
                    }
                } finally {
                    stop()
                }
            }
        }).start()
    }

    synchronized void addListener(Listener listener) {
        log.info("Add new listener to MongoLogService...")
        this.listeners.add(listener)
    }

    synchronized void alertListener(doc) {
        listeners.each {it->
            it.handle(doc)
        }
    }

    def stop() {
        log.info("Stop MongoLogService...")
        try {
            if (cursor != null) cursor.close();
        } catch (final Throwable t) { }
        db.requestDone();
    }
}
