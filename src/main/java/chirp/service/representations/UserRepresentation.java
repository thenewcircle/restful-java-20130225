package chirp.service.representations;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import chirp.model.User;
import chirp.service.resources.UserResource;

public class UserRepresentation {

	private final String username;
	private final String realname;
	private final URI self;

	public UserRepresentation(User user, boolean summary) {
		String username = user.getUsername();
		this.username = summary ? null : username;
		this.realname = summary ? null : user.getRealname();
		self = UriBuilder.fromResource(UserResource.class).path(username).build();
	}

	@JsonCreator
	public UserRepresentation(
			@JsonProperty("username") String username,
			@JsonProperty("realname") String realname,
			@JsonProperty("self") URI self) {
		this.username = username;
		this.realname = realname;
		this.self = self;
	}

	public String getUsername() {
		return username;
	}

	public String getRealname() {
		return realname;
	}

	public URI getSelf() {
		return self;
	}
}
