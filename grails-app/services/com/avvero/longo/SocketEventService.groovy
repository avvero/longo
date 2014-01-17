package com.avvero.longo

import grails.transaction.Transactional
import org.apache.log4j.LogManager
import org.apache.log4j.net.SocketNode

@Transactional
class SocketEventService {

    ServerSocket serverSocket

    def start() {
        log.info("Start SocketEventService...")
        new Thread(new Runnable() {
            @Override
            void run() {
                def port = 4447
                log.info("Listening on port " + port);
                serverSocket = new ServerSocket(port);
                log.info("Waiting to accept a new client.");
                Socket socket = serverSocket.accept();
                log.info("Connected to client at " + socket.getInetAddress());
                log.info("Starting new socket node.");
                new Thread(new SocketNode(socket, LogManager.getLoggerRepository())).start();
            }
        }).start()
    }

    def stop() {
        log.info("Stop SocketEventService...")
        serverSocket.close()
    }

}
