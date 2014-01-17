package com.avvero.longo

import org.apache.commons.logging.LogFactory

/**
 *
 * @author fxdev-belyaev-ay
 */
abstract class Collector {

    private static final log = LogFactory.getLog(this)

    private boolean run = false

    def listeners = []

    public abstract String getName()

    public void start() {
        _start()
        run = true
    };
    protected abstract void _start()

    public void stop() {
        _stop()
        run = false
    }
    protected abstract void _stop()

    synchronized void addListener(Listener listener) {
        log.info("Add new listener to Collector " + getName())
        this.listeners.add(listener)
    }

    synchronized void alertListener(doc) {
        listeners.each {it->
            it.handle(doc)
        }
    }

}
