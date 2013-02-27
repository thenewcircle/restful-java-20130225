package chirp.service.representations;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.UriBuilder;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import chirp.model.Post;
import chirp.service.resources.PostResource;

public class PostCollectionRepresentation {

	private final URI self;
	private final Collection<PostRepresentation> posts;

	public PostCollectionRepresentation(Collection<Post> posts) {
		this.self = UriBuilder.fromResource(PostResource.class).build();
		this.posts = new ArrayList<PostRepresentation>();
		for (Post post : posts) {
			this.posts.add(new PostRepresentation(post, true));
		}
	}

	@JsonCreator
	public PostCollectionRepresentation(
			@JsonProperty("self") URI self,
			@JsonProperty("posts") Collection<PostRepresentation> posts) {
		this.self = self;
		this.posts = posts;
	}

	public URI getSelf() {
		return self;
	}

	public Collection<PostRepresentation> getPosts() {
		return posts;
	}

}
