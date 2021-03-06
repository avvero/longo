package com.avvero.longo

import com.avvero.bson.LoggingEventBsonifierImplExt
import com.mongodb.DBObject
import org.apache.commons.logging.LogFactory
import org.apache.log4j.spi.LoggingEvent
import org.atmosphere.cpr.Broadcaster
import org.atmosphere.cpr.BroadcasterFactory
import org.atmosphere.cpr.DefaultBroadcaster
import org.log4mongo.LoggingEventBsonifier
import org.log4mongo.LoggingEventBsonifierImpl

/**
 *
 * @author fxdev-belyaev-ay
 */
class SocketCollector extends Collector {

    private static final log = LogFactory.getLog(this)

    Socket socket
    ServerSocket serverSocket

    LoggingEventBsonifier bsonifier = new LoggingEventBsonifierImplExt()

    SocketCollector(Socket socket) {
        this.socket = socket
    }

    @Override
    String getName() {
        return socket.getKey()
    }

    @Override
    void _start() {
        log.info("Start SocketCollector...")
        log.info("Listening on port " + socket.port);
        serverSocket = new ServerSocket(socket.port);
        new Thread(new Runnable() {
            @Override
            void run() {
                while (run) {
                    log.info("Waiting to accept a new client.");
                    java.net.Socket serverSocket = serverSocket.accept();
                    log.info("Connected to client at " + getName());
                    log.info("Starting new socket node.");
                    ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(serverSocket.getInputStream()));
                    try {
                        LoggingEvent event;
                        if (ois != null) {
                            while(true) {
                                // read an event from the wire
                                event = (LoggingEvent) ois.readObject();
                                alertListener(event)
                            }
                        }
                    } catch (SocketException e) {
                        log.error(e)
                    } finally {
//                        if (ois != null) {
//                            try {
//                                ois.close();
//                            } catch(Exception e) {
//                                log.info("Could not close connection.", e);
//                            }
//                        }
                    }
                }
            }
        }).start()
    }

    @Override
    void _stop() {
        log.info("Stop SocketCollector...")
        serverSocket.close()
    }

    @Override
    protected Listener getNewListener(Broadcaster broadcaster, String mapping) {
        return new Listener() {
            void handle(Object o) {
                LoggingEvent event = (LoggingEvent) o;
                String lev = event.getLevel();
                if (lev == "ERROR") {
                    def event1 = event;
                }
                DBObject bson = bsonifier.bsonify(event);
                def log = bson.toString()
                Broadcaster b = BroadcasterFactory.getDefault().lookup(DefaultBroadcaster.class, mapping)

                broadcaster.broadcast(log)
            }
        }
    }
}
