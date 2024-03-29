package com.caxerx.memoryvisualizer4j.implementation.visualizer;

import com.caxerx.memoryvisualizer4j.api.StickyBroadcaster;
import com.google.gson.Gson;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.websocket.WsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;

public class VisualizerWebServer implements StickyBroadcaster {

    private final ArrayList<String> stickyMessage = new ArrayList<>();
    private final HashSet<WsContext> activeSession = new HashSet<>();

    public VisualizerWebServer() {
        Javalin.log = new NonOpLogger();
        Javalin app = null;
        int port = 20000;
        while (app == null && port < 30000) {
            try {
                app = Javalin.create(config -> {
                    config.addStaticFiles("/", "/dist", Location.CLASSPATH);
                    config.addSinglePageRoot("/", "/dist/index.html");
                }).start(port);
            } catch (Exception e) {
                port++;
            }
        }

        Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
        logger.info("Visualizer Service started at: http://localhost:" + port);
        logger.info("Visualizer Service started. Application will not stop until manually terminate.");

        app.ws("/websocket", ws -> {
            ws.onConnect(ctx -> {
                activeSession.add(ctx);
                stickyMessage.forEach(ctx::send);
            });

            ws.onClose(activeSession::remove);
        });
    }

    @Override
    public void broadcastSticky(ObjectMapMessage msg) {
        String json = new Gson().toJson(msg);
        stickyMessage.add(json);
        activeSession.forEach(session -> session.send(json));
    }

}
