package chirp.service.representations;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import chirp.model.Post;
import chirp.service.resources.PostResource;

public class PostRepresentation {

	private final String timestamp;
	private final String content;
	private final URI self;

	public PostRepresentation(Post post, boolean summary) {
		String timestamp = post.getTimestamp().toString();
		this.timestamp = summary ? null : timestamp;
		this.content = summary ? null : post.getContent();
		this.self = UriBuilder
				.fromResource(PostResource.class)
				.path(post.getUser().getUsername())
				.path(timestamp)
				.build();
	}

	@JsonCreator
	public PostRepresentation(
			@JsonProperty("timestamp") String timestamp,
			@JsonProperty("content") String content,
			@JsonProperty("self") URI self) {
		this.timestamp = timestamp;
		this.content = content;
		this.self = self;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getContent() {
		return content;
	}

	public URI getSelf() {
		return self;
	}
}
