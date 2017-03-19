package ru.fancytraffic.masterway.server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.fancytraffic.masterway.server.adm.CommandExecutor;
import ru.fancytraffic.masterway.server.servlets.Admin;

/**
 * @author Roman Ljulchenko
 *         rnljul@gmail.com
 *         -----
 *         Created  on 02.03.2017.
 */
public class Main {

    private static final int HTTP_PORT = 8096;
    private static final String ADM_URI = "/adm/*";
    private static final String STOP_KEY = "stop";

    public static void main(String[] args) throws Exception {

        Server server = new Server(HTTP_PORT);
        CommandExecutor commandExecutor = new CommandExecutor(server, STOP_KEY);

        Admin admin = new Admin(commandExecutor);
        ServletContextHandler admContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        admContextHandler.addServlet(new ServletHolder(admin), ADM_URI);

        Handler[] handlers = {admContextHandler};
        HandlerCollection handlerCollection = new HandlerCollection();
        handlerCollection.setHandlers(handlers);

        server.setHandler(handlerCollection);
        server.start();
        server.dumpStdErr();
        server.join();
    }

}