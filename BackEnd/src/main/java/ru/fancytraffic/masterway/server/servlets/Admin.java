package ru.fancytraffic.masterway.server.servlets;

import ru.fancytraffic.masterway.server.adm.CommandExecutor;
import ru.fancytraffic.masterway.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Roman Ljulchenko
 *         rnljul@gmail.com
 *         -----
 *         Created  on 02.03.2017.
 */
public class Admin extends HttpServlet{


    private final CommandExecutor commandExecutor;

    public Admin(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> statusPageVars = new HashMap<>();
        statusPageVars.put("status", "started");
        response.getWriter().println(PageGenerator.instance().getPage(statusPageVars));
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

}

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        String reqStopKey = request.getParameter("stopKey");

        response.setContentType("text/html;charset=utf-8");

        response.setStatus(HttpServletResponse.SC_OK);

        Map<String, Object> statusPageVars = new HashMap<>();

        if (reqStopKey != null && reqStopKey.equals(commandExecutor.getStopKey())) {
            // Останавливать надо в одельном потоке, чтобы не конфликтовать с потоком сервлета
            new Thread(commandExecutor::prepareStop).start();
            // Если сразу стопнуть сервер, то станица со статусом может не успеть сгенерироваться
            statusPageVars.put("status", "stopped");
            response.getWriter().println(PageGenerator.instance().getPage(statusPageVars));
            // Страница готова, можно останавливать
            commandExecutor.execStop();

        } else {
            statusPageVars.put("status", "started");
            response.getWriter().println(PageGenerator.instance().getPage(statusPageVars));
        }
    }

}
