package com.avvero.longo

class FlowController {

    static long id;

    def synchronized getId() {
        return ++id;
    }

    def index() {
        render (view: "index", model: [collector: params.collector, id: getId()])
    }

    /**
     * Управление источниками
     * @return
     */

    def sources() {
        render (view: "sources", model: [
                mongoConnectionConfigs: MongoConnectionConfig.findAll(),
                sockets: Socket.findAll()
        ])
    }

    def addMongoConnectionConfig() {
        new MongoConnectionConfig(params).save()
        forward action: "sources"
    }

    def delMongoConnectionConfig() {
        MongoConnectionConfig.findById(params.id).delete(flush: true)
        forward action: "sources"
    }

    def addSocket() {
        new Socket(params).save()
        forward action: "sources"
    }

    def delSocket() {
        def socket = Socket.findById(params.id)
        CollectorFactory.deleteCollector(socket);
        socket.delete(flush: true)
        forward action: "sources"
    }

    /**
     * Подключения
     * @return
     */

    def startMongoCollector() {
        def connection = MongoConnectionConfig.findById(params.id)
        Collector collector = CollectorFactory.getCollector(connection)
        collector.start()
        redirect(controller: "flow", action: "index", params: [collector: collector.getName()])
    }

     def startSocketCollector() {
        def socket = Socket.findById(params.id)
        Collector collector = CollectorFactory.getCollector(socket)
        if (!collector.isRun()) {
            collector.start()
        }
        redirect(controller: "flow", action: "index", params: [collector: collector.getName()])
    }

     def stopSocketCollector() {
        def socket = Socket.findById(params.id)
        Collector collector = CollectorFactory.getCollector(socket)
        collector.stop()
        forward action: "sources"
    }


}
