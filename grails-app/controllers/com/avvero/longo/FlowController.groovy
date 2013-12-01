package com.avvero.longo

class FlowController {

    static long id;

    def synchronized getId() {
        return ++id;
    }

    def index() {
        render (view: "index", model: [id: getId()])
    }
}
