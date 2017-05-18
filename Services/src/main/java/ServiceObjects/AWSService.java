package ServiceObjects;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
//import org.apache.commons.io.FileUtils;

import java.io.IOException;

import static framework.Utils.statusCodeValidation;

public class AWSService {

    public static String get(String endpoint) throws IOException {

        Client client = Client.create();
        WebResource webResource = client
                .resource(endpoint);
        ClientResponse response = webResource.type("application/json").get(ClientResponse.class);
        statusCodeValidation(response,200);

        String output = response.getEntity(String.class);
        return output;
    }

    public static String post(String request, String endpoint) throws IOException {
        Client client = Client.create();
        WebResource webResource = client
                .resource(endpoint);
        ClientResponse response = webResource.type("application/json").post(ClientResponse.class,request);
        statusCodeValidation(response,201);
        String output = response.getEntity(String.class);
        return output;
    }

    public static String put(String request, String endpoint) throws IOException {
        Client client = Client.create();
        WebResource webResource = client
                .resource(endpoint);
        ClientResponse response = webResource.type("application/json").put(ClientResponse.class,request);
        statusCodeValidation(response,200);
        String output = response.getEntity(String.class);
        return output;
    }

    public static String delete(String request, String endpoint) throws IOException {
        Client client = Client.create();
        WebResource webResource = client
                .resource(endpoint);
        ClientResponse response = webResource.type("application/json").delete(ClientResponse.class);
        statusCodeValidation(response,200);
        String output = response.getEntity(String.class);
        return output;
    }

}
