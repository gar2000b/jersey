package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to
	 * the client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
		return "Got it!";
	}

	@GET
	@Path("/users/{username}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getUser(@PathParam("username") String userName) {
		return "Username passed in is: " + userName;
	}

	@PUT
	@Path("/dataIn")
	@Consumes(MediaType.TEXT_PLAIN)
	public void putIntoDataStore(InputStream data) {

		String lineIn = "";
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(data));
			while ((lineIn = br.readLine()) != null) {
				sb.append(lineIn);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(sb.toString());
	}

}
