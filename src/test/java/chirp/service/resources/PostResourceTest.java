package chirp.service.resources;

import static com.sun.jersey.api.client.ClientResponse.Status.CREATED;
import static junit.framework.Assert.assertEquals;

import java.net.URI;

import javax.ws.rs.core.MultivaluedMap;

import org.junit.Test;

import chirp.service.representations.PostRepresentation;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class PostResourceTest extends ResourceTest {

	@Test
	public void createPostMustCreatePost() {
		getUserRepository().createUser("testuser", "Test User");
		
		MultivaluedMap<String, String> form = new MultivaluedMapImpl();
		form.add("content", "test content");
		ClientResponse response = resource().path("post").path("testuser").post(ClientResponse.class, form);
		assertEquals(CREATED.getStatusCode(), response.getStatus());

		URI uri = response.getLocation();
		PostRepresentation post = resource().uri(uri).get(PostRepresentation.class);
		assertEquals("test content", post.getContent());
	}
}
