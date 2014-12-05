package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import control.CredentialJpaController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Credential;

public class HTTPserver {

    static int port = 9999;
    static String ip = "127.0.0.1";
    static String publicFolder = "src/htmlFiles/";
    private static final boolean DEVELOPMENT_MODE = true;
    private static EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("SEMESTERprojetPU");
    private static CredentialJpaController credentialJpaController = new CredentialJpaController(emf);

    public void run() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(ip, port), 0);
    //REST Routes

        //HTTP Server Routes
        // server.createContext("/login", new HandlerCheckPassword());
        server.createContext("/login", new HandlerCheckCredentials());
        server.createContext("/create", new HandlerCreateCredentials());
        server.createContext("/find", new HandlerFindCredentials());
        server.start();

        System.out.println("Server has started on port: " + port);
    }

    public static void main(String[] args) throws Exception {
        if (args.length >= 3) {
            port = Integer.parseInt(args[0]);
            ip = args[1];
            publicFolder = args[2];
        }
        new HTTPserver().run();
    }

    private static class HandlerCheckCredentials implements HttpHandler {

        @Override
        public void handle(HttpExchange he) throws IOException {
            String response = "";
            int status = 200;
            String method = he.getRequestMethod().toUpperCase();
            switch (method) {
                case "GET":
                    try {
                        String path = he.getRequestURI().getPath();
                        int lastIndex = path.lastIndexOf("/");
                        if (lastIndex > 0) {  //something exists after /
                            String params = path.substring(lastIndex + 1);
                            String username = params.split(",")[0];
                            String password = params.split(",")[1];
                            response = credentialJpaController.checkCredential(username, password);
                            // String jsonresponse= gson.toJson(response);
                        } else { // person                           
                            response = "Invalid request";
                        }
                    } catch (Exception nfe) {
                        response = "Error in server:" + nfe;
                        status = 404;
                    }
                    break;
            }
            he.getResponseHeaders().add("Content-Type", "application/json");
            he.sendResponseHeaders(status, 0);
            try (OutputStream os = he.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
    
    private static class HandlerFindCredentials implements HttpHandler {

        @Override
        public void handle(HttpExchange he) throws IOException {
            String response = "";
            int status = 200;
            String method = he.getRequestMethod().toUpperCase();
            switch (method) {
                case "GET":
                    try {
                        String path = he.getRequestURI().getPath();
                        int lastIndex = path.lastIndexOf("/");
                        if (lastIndex > 0) {  //something exists after /
                            String username = path.substring(lastIndex + 1);
                            response = credentialJpaController.checkACredential(username);
                            // String jsonresponse= gson.toJson(response);
                        } else { // person                           
                            response = "Invalid request";
                        }
                    } catch (Exception nfe) {
                        response = "Error in server:" + nfe;
                        status = 404;
                    }
                    break;
            }
            he.getResponseHeaders().add("Content-Type", "application/json");
            he.sendResponseHeaders(status, 0);
            try (OutputStream os = he.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
    
    

    private static class HandlerCreateCredentials implements HttpHandler {

        Gson gson = new Gson();

        @Override
        public void handle(HttpExchange he) throws IOException {
            Credential cred = null;
            String response = "";
            int status = 200;
            String method = he.getRequestMethod().toUpperCase();
            switch (method) {
                case "POST":
                    try {
                        InputStreamReader isr = new InputStreamReader(he.getRequestBody());
                        BufferedReader br = new BufferedReader(isr);
                        String jsonQuery = br.readLine();
                        System.out.println("hello: " + jsonQuery);

                        cred = credentialJpaController.addCredential(jsonQuery);
                        System.out.println(jsonQuery);

                        response = gson.toJson(cred);
                        System.out.println(response);
                    } catch (IllegalArgumentException iae) {
                        status = 400;
                        response = iae.getMessage();
                    } catch (IOException e) {
                        status = 500;
                        response = "Internal Server Problem";
                    } catch (Exception ex) {
                        Logger.getLogger(HTTPserver.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;

            }
            he.getResponseHeaders().add("Content-Type", "application/json");
            he.sendResponseHeaders(status, 0);
            try (OutputStream os = he.getResponseBody()) {
                os.write(response.getBytes());

            }

        }
    }
}
