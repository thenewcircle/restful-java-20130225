package chirp.service.representations;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.UriBuilder;

import chirp.model.User;
import chirp.service.resources.UserResource;

public class UserCollectionRepresentation {

	private final URI self = UriBuilder.fromResource(UserResource.class).build();
	private final Collection<UserRepresentation> users;

	public UserCollectionRepresentation(Collection<User> users) {
		this.users = new ArrayList<UserRepresentation>();
		for (User user : users) {
			this.users.add(new UserRepresentation(user));
		}
	}

	public URI getSelf() {
		return self;
	}

	public Collection<UserRepresentation> getUsers() {
		return users;
	}

}
