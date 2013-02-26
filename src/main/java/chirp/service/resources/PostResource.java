package chirp.service.resources;

import java.net.URI;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import chirp.model.Post;
import chirp.model.Timestamp;
import chirp.model.User;
import chirp.model.UserRepository;

import com.google.inject.Inject;

@Path("/post/{username}")
public class PostResource {

	private final UserRepository userRepository;

	@Inject
	public PostResource(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@POST
	public Response createPost(@PathParam("username") String username, @FormParam("content") String content) {
		User user = userRepository.getUser(username);
		Post post = user.createPost(content);
		
		URI uri = UriBuilder.fromPath(post.getTimestamp().toString()).build();
		return Response.created(uri).build();
	}

	@GET
	@Path("{timestamp}")
	@Produces(MediaType.APPLICATION_JSON)
	public Post getPost(@PathParam("username") String username, @PathParam("timestamp") String timestamp) {
		User user = userRepository.getUser(username);
		return user.getPost(new Timestamp(timestamp));
	}
}
