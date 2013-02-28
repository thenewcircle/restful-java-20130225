package chirp.service.resources;

import java.net.URI;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import chirp.model.UserRepository;

import com.google.inject.Inject;

@Path("/bulk-deletion")
public class BulkDeletionResource {

	private final UserRepository userRepository;

	@Inject
	public BulkDeletionResource(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@POST
	public Response createBulkDeletion() {
		int id = userRepository.createBulkDeletion();
		URI uri = UriBuilder.fromPath(String.valueOf(id)).build();
		return Response.created(uri).build();
	}

	@PUT
	@Path("{id}/{username}")
	public Response addUserToBulkDeletion(@PathParam("id") int id, @PathParam("username") String username) {
		userRepository.addToBulkDeletion(id, username);
		return Response.status(Status.ACCEPTED).build();
	}

	@POST
	@Path("{id}")
	public Response commitBulkDeletion(@PathParam("id") int id) {
		if (userRepository.commitBulkDeletion(id)) {
			return Response.noContent().build();
		}
		return Response.status(Status.CONFLICT).build();
	}

	@DELETE
	@Path("{id}")
	public void cancelBulkDeletion(@PathParam("id") int id) {
		userRepository.cancelBulkDeletion(id);
	}

}
