package au.com.unico.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class RestServiceTest {
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
			pushRestCall();
	        //listRestCall();
	}
	
	private static void pushRestCall(){
		/*		  HttpClient client = new DefaultHttpClient();
		  HttpPost post = new HttpPost("http://localhost:8088/WebServiceProject/queueInterface/push");
		  ArrayList<NameValuePair> postParameters;
		  
		  postParameters = new ArrayList<NameValuePair>();
		    postParameters.add(new BasicNameValuePair("param1", "1"));
		    postParameters.add(new BasicNameValuePair("param2", "2"));
		  post.setEntity(new UrlEncodedFormEntity(postParameters));

		  HttpResponse response = client.execute(post);
		  BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		  String line = "";
		  while ((line = rd.readLine()) != null) {
		   System.out.println(line);
		  }*/
		  
/*		  HttpHost host = new HttpHost("localhost", 8088, "http");

	        HttpClient client1 = new DefaultHttpClient();
*//*	        AuthHttpComponentsClientHttpRequestFactory requestFactory =
	                new AuthHttpComponentsClientHttpRequestFactory(
	                        client, host, "user", "password");
*/	        RestTemplate restTemplate1 = new RestTemplate();

	        /*
	         * restTemplate1.getMessageConverters() .add(new
	         * FormHttpMessageConverter());
	         * restTemplate1.getMessageConverters().add( new
	         * StringHttpMessageConverter());
	         */
	       /* restTemplate1.getMessageConverters().add(
	                new MappingJacksonHttpMessageConverter());*/

	        HttpHeaders requestHeaders = new HttpHeaders();
	        requestHeaders.setContentType(MediaType.APPLICATION_XML);

	        /*
	         * MultiValueMap<String, String> map = new LinkedMultiValueMap<String,
	         * String>(); map.add("Payload", payload);
	         */
	        
	        Map<String, String> parammap = new HashMap<String, String>();
	        parammap.put("param1", "45" );
	        parammap.put("param2", "60" );

	        MultiValueMap<String, Object> headers = new LinkedMultiValueMap<String, Object>();
	        headers.add("Accept", "application/xml");
	        headers.add("Content-Type", "application/xml");
	        /*HttpEntity<String> requestEntity = new HttpEntity<String>(payload,
	                requestHeaders);*/
	        HttpEntity<String> requestEntity = new HttpEntity<String>(requestHeaders);

	        String responseStr = restTemplate1
	                .postForObject(
	                        "http://localhost:8088/WebServiceProject/queueInterface/push?param1={param1}&param2={param2}",
	                        requestEntity, String.class, parammap);
	        System.out.println(" Response received from the Import Service "
	                + responseStr);
	}
	
	private static void listRestCall(){
        MultiValueMap<String, Object> listheaders = new LinkedMultiValueMap<String, Object>();
        listheaders.add("Accept", "application/json");
        listheaders.add("Content-Type", "application/json");
        
        HttpHeaders listRequestHeaders = new HttpHeaders();
        listRequestHeaders.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate1 = new RestTemplate();
        
        
        List<LinkedHashMap<Integer, Date>> response = restTemplate1
                .getForObject(
                        "http://localhost:8088/WebServiceProject/queueInterface/list",
                        List.class,listRequestHeaders);
        System.out.println(" Response received from the Import Service "
                + response.size());
        
        for (LinkedHashMap<Integer, Date> lin : response){
        	System.out.println(lin);
        }
	}
	
}
