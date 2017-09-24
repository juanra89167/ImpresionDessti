package com.mave.ws.rest.serviceweb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.mave.ws.rest.voweb.Actor;




@Path("/actor")
public class Serviceweb {
	static final String api_version = "1.01A rev.18729";
	static Map<String, Actor> actors = new HashMap<String, Actor>();
	
	static { 
		System.out.println("Initializing Internal DataStore...");
		actors.put("123", new Actor("123", "Hugh Jackson", "Hugh Michael Jackman", "October 12, 1968", "hughjackman@mail.com"));
		actors.put("124", new Actor("124", "Jennifer Lawrence", "Jennifer Shrader Lawrence", "August 15, 1990", "jennifer@mail.com"));
		actors.put("345", new Actor("345", "Jennifer Lopez", "Jennifer Lynn Lopez", "July 24, 1969", "jlo@verizon.com"));
		actors.put("333", new Actor("333", "Jennifer Anniston", "Jennifer Joanna Aniston", "February 11, 1969", "jennifer.anniston@eonline.com"));
		actors.put("444", new Actor("444", "Julia Roberts", "Julia Fiona Roberts ", "October 28, 1967", "julia.roberts@att.com"));
		actors.put("777", new Actor("777", "Chris Evans", "Christopher Robert Evans", "June 13, 1981", "chris.evans@comcast.com"));
		actors.put("654", new Actor("654", "Robert Downey Jr.", "Robert John Downey Jr", "April 4, 1965", "robertdowney@verizon.com"));
		actors.put("255", new Actor("255", "Johnny Depp", "John Christopher Depp II", "June 9, 1963", "johndepp@hollywood.com"));		
		actors.put("989", new Actor("989", "Scarlet Johansson", "Scarlett Ingrid Johansson", "November 22, 1984", "scarjo@mail.com"));
	}
		
	@Path("/version") 
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnVersion() {
		return "<p>Version: " + api_version + "</p>";
	}

	// This is the default @PATH
	@GET
	@Produces({ MediaType.APPLICATION_JSON})
	public ArrayList<Actor> getAllActors() {
		System.out.println("Getting all actors...");
		ArrayList<Actor> actorList = new ArrayList<Actor>(actors.values());
		return actorList;
	}
	
	@Path("{id}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Actor getActorById(@PathParam("id") String id) {
		System.out.println("Getting actor by ID: " + id);

		Actor actor = actors.get(id);
	  if (actor != null) {
		
	  } else {
	
	  }
	  return actor;
	}
	
	@Path("{id}")
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Actor updateActor(Actor actor) {
	  actors.put(""+actor.getEstatus(), actor);
	  
	  System.out.println("updateActor with ID: " + actor.getPoliza());
	  if (actor != null) {
		//logger.info("Inside updateActor, returned: " + actor.toString());
	  } else {
		//logger.info("Inside updateActor, ID: " + actor.getId() + ", NOT FOUND!");
	  }
	  return actor; 
	}
	
	@Path("/search/{query}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ArrayList<Actor> searchActorByName(@PathParam("query") String query) {
  	  System.out.println("Searching actor by Name: " + query);
	  
  	  ArrayList<Actor> actorList = new ArrayList<Actor>();	  
	  for (Actor c: actors.values()) {
	    if (c.getSucursal().toUpperCase().contains(query.toUpperCase()))
	    	actorList.add(c);
	  }
	  return actorList;
	}
	
	@Path("/add")
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Actor addActor(Actor actor) {
	//  System.out.println("Adding actor with ID: " + actor.getBirthName());
	  
	  if (actor != null) {
		System.out.println("Inside addActor, returned: " + actor.toString());
		actors.put(""+actor.getPoliza(), actor);
		System.out.println("# of actors: " + actors.size());
		System.out.println("Actors are now: " + actors);
	  } else {
		System.out.println("Inside addActor, Unable to add actors...");
	  } 
	  return actor;
	}
	
	@Path("{id}")
	@DELETE
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Actor deleteActorById(@PathParam("id") String id) {
	  System.out.println("Deleting actor with ID: " + id);
		
	  Actor actor = actors.remove(id);
	  if (actor != null) {
	//	logger.info("Inside deleteActorById, returned: " + actor.toString());
	  } else {
		//logger.info("Inside deleteActorById, ID: " + id + ", NOT FOUND!");
	  }
	  return actor;
	}
}
