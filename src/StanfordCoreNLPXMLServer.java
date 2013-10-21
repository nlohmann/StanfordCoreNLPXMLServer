/*
    Stanford CoreNLP XML Server
    Copyright (C) 2013 Niels Lohmann

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import edu.stanford.nlp.io.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.util.*;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.Query;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerServer;
import org.simpleframework.transport.Server;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

public class StanfordCoreNLPXMLServer implements Container {
    static StanfordCoreNLP pipeline;
    static int port = 8080;

    // an interface to the Stanford Core NLP
    public String parse(String s) throws java.io.IOException {
        Annotation annotation = new Annotation(s);
        pipeline.annotate(annotation);
        StringWriter sb = new StringWriter();
        pipeline.xmlPrint(annotation, sb);
        return sb.toString();
    }

    public void handle(Request request, Response response) {
        try {
            long time = System.currentTimeMillis();
   
            response.setValue("Content-Type", "text/xml");
            response.setValue("Server", "Stanford CoreNLP XML Server/1.0 (Simple 5.1.6)");
            response.setDate("Date", time);
            response.setDate("Last-Modified", time);
   
            // pass "text" POST query to Stanford Core NLP parser
            String text = request.getQuery().get("text");  
            PrintStream body = response.getPrintStream();
            body.println(parse(text));
            body.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    } 

    public static void main(String args[]) throws Exception {
        // use port if given
        try {
            port = Integer.parseInt(args[0]);
        } catch(Exception e) {
        }

        // initialize the Stanford Core NLP
        pipeline = new StanfordCoreNLP();

        // start the server
        Container container = new StanfordCoreNLPXMLServer();
        Server server = new ContainerServer(container);
        Connection connection = new SocketConnection(server);
        SocketAddress address = new InetSocketAddress(port);
        connection.connect(address);

        System.out.println("Initialized server at port " + port + ".");
    }
}
