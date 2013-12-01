package com.avvero.longo

import com.avvero.db.utils.CursorListener
import com.avvero.db.utils.MongoUtils
import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBCursor
import grails.converters.JSON
import org.atmosphere.cpr.*

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * User: avvero
 * Date: 24.11.13
 */
class LogItemHandler extends HttpServlet {

    @Override
    void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String mapping = "/log" + request.getPathInfo()
        Broadcaster b = BroadcasterFactory.getDefault().lookup(DefaultBroadcaster.class, mapping, true)
        Meteor m = Meteor.build(request)

        //-----------------

        //-----------------
//        m.addListener(new AtmosphereResourceEventListenerAdapter() {
//            @Override
//            void onDisconnect(AtmosphereResourceEvent event) {
//                db.requestDone();
//            }
//        })
        m.setBroadcaster(b)
        //-----------------

        //-----------------
    }

    @Override
    void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String mapping = "/log" + request.getPathInfo();
        def jsonMap = JSON.parse(request.getReader().readLine().trim()) as Map
        String message = jsonMap.containsKey("message") ? jsonMap.message.toString() : null
        if (message == null) {

        } else {
            DB db = MongoUtils.getDB("127.0.0.1", 27017, "longo")
            DBCursor cursor = MongoUtils.getTailableCursor(db, "test")
            MongoUtils.tail(db, cursor, new CursorListener() {
                @Override
                public void afterInsert(Object o) {
                    BasicDBObject dbObject = (BasicDBObject) o;
                    def log = dbObject.toString()
                    Broadcaster broadcaster = BroadcasterFactory.getDefault().lookup(DefaultBroadcaster.class, mapping)
                    broadcaster.broadcast(log)
                }
            });

            Broadcaster b = BroadcasterFactory.getDefault().lookup(DefaultBroadcaster.class, mapping)
            b.broadcast(jsonMap)
        }
    }

}
