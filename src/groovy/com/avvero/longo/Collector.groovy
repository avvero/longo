package com.avvero.longo

import org.apache.commons.logging.LogFactory
import org.atmosphere.cpr.Broadcaster

/**
 *
 * @author fxdev-belyaev-ay
 */
abstract class Collector {

    private static final log = LogFactory.getLog(this)

    protected boolean run = false

    public boolean isRun() {
        return run;
    }

    def listeners = []

    public abstract String getName()

    public void start() {
        run = true;
        _start()
    };
    protected abstract void _start()

    public void stop() {
        if (run) {
            run = false
            _stop()
        }
    }
    protected abstract void _stop()

    synchronized void addListener(Listener listener) {
        log.info("Add new listener to Collector " + getName())
        this.listeners.add(listener)
    }

    synchronized void addListener(Broadcaster broadcaster, String mapping) {
        log.info("Add new listener to Collector " + getName())
        this.listeners.add(getNewListener(broadcaster, mapping))
    }

    protected abstract Listener getNewListener(Broadcaster broadcaster, String mapping)

    synchronized void alertListener(doc) {
        listeners.each {it->
            it.handle(doc)
        }
    }

}
