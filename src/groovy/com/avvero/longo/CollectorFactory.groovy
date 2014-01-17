package com.avvero.longo

import java.util.concurrent.ConcurrentHashMap

/**
 *
 * @author fxdev-belyaev-ay
 */
class CollectorFactory {

    private static def collectors = new ConcurrentHashMap<String, Collector>();

    public static synchronized void addCollector(Collector collector) {
        if (!collectors.containsKey(collector.getName())) {
            collectors.put(collector.getName(), collector)
        }
    }

    public static synchronized Collector getCollector(String name) {
        return collectors.get(name);
    }

    public static synchronized Collector getCollector(MongoConnectionConfig config) {
        Collector collector = collectors.get(config.getKey())
        if (collector == null) {
            collector = new MongoCollector(config)
            collectors.put(collector.getName(), collector)
            collector.start()
        }
        return collector;
    }

    public static synchronized Collector getCollector(Socket socket) {
        Collector collector = collectors.get(socket.getKey())
        if (collector == null) {
            collector = new SocketCollector(socket)
            collectors.put(collector.getName(), collector)
            collector.start()
        }
        return collector;
    }

}
