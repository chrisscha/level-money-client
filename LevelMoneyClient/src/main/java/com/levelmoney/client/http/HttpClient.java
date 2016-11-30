package com.levelmoney.client.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.levelmoney.client.ServerConfig;
import com.levelmoney.client.api.v2.core.ApiRequest;
import com.levelmoney.client.api.v2.core.ApiResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Wrapper for Apache's HttpClient
 * I use a try-with-resources pattern to avoid worrying about closing the resources.
 * <p>
 * The debug hack is the sort of thing that should be fixed by logging levels.
 * <p>
 * Created by chris on 11/22/16.
 */
public class HttpClient<T extends ApiRequest, U extends ApiResponse> {

    private final Class responseClass;
    private final String apiUrl;

    public HttpClient(Class responseClass, String apiName) {
        this.responseClass = responseClass;
        this.apiUrl = ServerConfig.SERVICE_URL + apiName;
    }

    /*
     *It's a bit of a hack to have to pass in the response class here, but due to type erasure Java can't
     * know what type of object a U really is. You can't just say U.class .... it's erased at runtime.
     * We could hide this fact by using factories or frameworks. If this software was "real" I'd use factories
     * and build HttpClients with their request,response,methodType,etc. all at server initialization
     * since it never changes until you recompile.
     */
    public U post(T request) throws IOException {

        HttpPost post = new HttpPost(apiUrl);
        ObjectMapper mapper = new ObjectMapper();
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            post.setEntity(new StringEntity(mapper.writeValueAsString(request)));

            HttpResponse rawResponse = client.execute(post);

            try (BufferedReader rd = new BufferedReader(new InputStreamReader(rawResponse.getEntity().getContent()))) {
                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                //System.out.println(result);//TODO: fix HACK: Un-comment if you'd like to see the raw response for debugging.
                return (U) mapper.readValue(result.toString(), responseClass);//the U is a forced cast, but it's either going to always work or never work
            }
        }
    }
}
