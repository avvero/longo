package com.avvero.longo

class Socket {

    String host
    int port

    static constraints = {
    }

    def getKey() {
        return "socket:" + this.host+":" + this.port
    }

}
