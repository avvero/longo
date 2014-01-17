package com.avvero.longo

class MongoConnectionConfig {

    String host
    int port
    String databaseName
    String collectionName

    static constraints = {
    }

    def getKey() {
        return "mongo:" + this.host+":" + this.port + ":" + this.databaseName + ":" + this.collectionName
    }

}
