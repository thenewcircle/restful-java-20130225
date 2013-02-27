package chirp.service.resources;

import java.net.URI;
import java.util.Collection;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriBuilder;

import chirp.model.User;
import chirp.model.UserRepository;
import chirp.service.representations.UserCollectionRepresentation;
import chirp.service.representations.UserRepresentation;

import com.google.inject.Inject;

@Path("/user")
public class UserResource {

	private final UserRepository userRepository;

	@Inject
	public UserResource(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers(@Context Request request) {
		Collection<User> users = userRepository.getUsers();

		EntityTag eTag = getEntityTag();
		ResponseBuilder response = request.evaluatePreconditions(eTag);
		if (response != null) {
			return response.build();
		}

		UserCollectionRepresentation rep = new UserCollectionRepresentation(users);
		return Response.ok(rep).tag(eTag).build();
	}
	
	@GET
	@Path("{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public UserRepresentation getUser(@PathParam("username") String username) {
		User user = userRepository.getUser(username);
		UserRepresentation rep = new UserRepresentation(user, false);
		return rep;
	}

	@PUT
	@Path("{username}")
	public Response createUser(
			@PathParam("username") String username,
			@FormParam("realname") String realname,
			@Context Request request) {
		ResponseBuilder response = request.evaluatePreconditions(getEntityTag());
		if (response != null) {
			return response.build();
		}
		
		userRepository.createUser(username, realname);

		URI uri = UriBuilder.fromPath("").build();
		return Response.created(uri).tag(getEntityTag()).build();
	}

	private EntityTag getEntityTag() {
		Collection<User> users = userRepository.getUsers();
		return new EntityTag(String.valueOf(users.hashCode()));
	}
}
