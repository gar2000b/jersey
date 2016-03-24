package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.model.Track;

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
	
	// curl: curl -X POST http://localhost:8080/simple-service-webapp/webapi/myresource/abc
    @POST // post with param (not a large payload)
    @Path("/{param}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response postMsg(@PathParam("param") String msg) {
        String output = "POST:Jersey say : " + msg;
        System.out.println(output);
        return Response.status(200).entity(output).build();
    }
    
	// curl: curl --data 'some data' http://localhost:8080/simple-service-webapp/webapi/myresource/post
    @POST // post with a large payload
    @Path("/post")
    // @Consumes(MediaType.TEXT_PLAIN)
    public Response postStrMsg(String msg) {
        String output = "POST:Jersey say : " + msg;
        System.out.println(output);
        return Response.status(200).entity(output).build();
    }
    
    // get example returning JSON
    // curl: curl -i http://localhost:8080/simple-service-webapp/webapi/myresource/getTrack
	@GET
	@Path("/getTrack")
	@Produces(MediaType.APPLICATION_JSON)
	public Track getTrackInJSON() {

		Track track = new Track();
		track.setTitle("Enter Sandman");
		track.setSinger("Metallica");

		return track;

	}
    
    // now create a post that consumes a JSON payload
	// cd ../instructions
	// curl: curl -X POST -d @track.txt http://localhost:8080/simple-service-webapp/webapi/myresource/postTrack --header "Content-Type:application/json"
	@POST
	@Path("/postTrack")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTrackInJSON(Track track) {

		String result = "Track saved : " + track;
		System.out.println(result);
		return Response.status(201).entity(result).build();
		
	}

}
