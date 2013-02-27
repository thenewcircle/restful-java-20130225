package chirp.service.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import chirp.model.User;
import chirp.model.UserRepository;
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
	public Collection<UserRepresentation> getUsers() {
		Collection<UserRepresentation> rep = new ArrayList<UserRepresentation>();
		for (User user : userRepository.getUsers()) {
			rep.add(new UserRepresentation(user));
		}
		return rep;
	}
	
	@GET
	@Path("{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public UserRepresentation getUser(@PathParam("username") String username) {
		User user = userRepository.getUser(username);
		UserRepresentation rep = new UserRepresentation(user);
		return rep;
	}

	@PUT
	@Path("{username}")
	public Response createUser(@PathParam("username") String username, @FormParam("realname") String realname) {
		userRepository.createUser(username, realname);

		URI uri = UriBuilder.fromPath("").build();
		return Response.created(uri).build();
	}
}
