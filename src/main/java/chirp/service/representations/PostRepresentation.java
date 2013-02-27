package chirp.service.representations;

import java.net.URI;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import chirp.model.Post;

import com.sun.jersey.server.linking.Ref;

public class PostRepresentation {

	@Ref("/post/{username}/{timestamp}")
	private URI self;

	private final String username;
	private final String timestamp;
	private final String content;

	public PostRepresentation(Post post, boolean summary) {
		this.username = post.getUser().getUsername();
		this.timestamp = post.getTimestamp().toString();
		this.content = summary ? null : post.getContent();
	}

	@JsonCreator
	public PostRepresentation(
			@JsonProperty("content") String content,
			@JsonProperty("self") URI self) {
		this.username = null; // TODO: parse from self link
		this.timestamp = null; // TODO: parse from self link
		this.content = content;
		this.self = self;
	}

	@JsonIgnore
	public String getUsername() {
		return username;
	}

	@JsonIgnore
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
