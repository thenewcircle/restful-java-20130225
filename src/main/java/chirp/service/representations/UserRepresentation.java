package chirp.service.representations;

import java.net.URI;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import chirp.model.User;

import com.sun.jersey.server.linking.Link;
import com.sun.jersey.server.linking.Links;
import com.sun.jersey.server.linking.Ref;

@Links({
	@Link(value = @Ref("/user/{username}"), rel = "self"),
	@Link(value = @Ref("/post/{username}"), rel = "related")
})
public class UserRepresentation {

	@Ref("/user/{username}")
	private URI self;

	private final String username;
	private final String realname;

	public UserRepresentation(User user, boolean summary) {
		this.username = user.getUsername();
		this.realname = summary ? null : user.getRealname();
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

	@JsonIgnore
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
