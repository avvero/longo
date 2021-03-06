package com.avvero.longo

import org.atmosphere.cpr.Broadcaster
import org.atmosphere.cpr.BroadcasterFactory
import org.atmosphere.cpr.DefaultBroadcaster
import org.atmosphere.cpr.Meteor

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
        Broadcaster broadcaster = BroadcasterFactory.getDefault().lookup(DefaultBroadcaster.class, mapping, true)
        Meteor m = Meteor.build(request)

        //XXX подумать лучше
        String[] pars = request.getPathInfo().split("/")
        Collector collector = CollectorFactory.getCollector(pars[1])
        collector.addListener(broadcaster, mapping)
        m.setBroadcaster(broadcaster)
    }

    @Override
    void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

}
