import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.IOException;

public class BackendRestMain {

    /**
     * @param args
     */
    public static void main(String[] args) throws IOException {
        int port = 8091;
        if (args.length > 0) {
            try {
                port = new Integer(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port '" + args[0] + "'. Port must be a number.");
                System.exit(1);
            }
        }
        System.setProperty("profiles.active", "development");
        Server server = new Server();
        //System.setProperty("org.eclipse.jetty.util.URI.charset","ISO-8859-1");
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setMaxThreads(100);
        server.setThreadPool(threadPool);
        Connector connector = new SelectChannelConnector();
        connector.setPort(port);
        server.setConnectors(new Connector[]{connector});
        WebAppContext context = new WebAppContext();
        context.setContextPath("/");
        context.setResourceBase("src/main/webapp");
        context.setDefaultsDescriptor("src/main/webapp/mywebdefaults.xml");
        //static
        context.setMaxFormContentSize(-1);
        ResourceHandler staticResourceHandler = new ResourceHandler();
        staticResourceHandler.setResourceBase("/home/www/upload");
        staticResourceHandler.setDirectoriesListed(true);
        ContextHandler staticContextHandler = new ContextHandler();
        staticContextHandler.setContextPath("/upload");
        staticContextHandler.setHandler(staticResourceHandler);
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{staticContextHandler, context});
        server.setHandler(handlers);
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
