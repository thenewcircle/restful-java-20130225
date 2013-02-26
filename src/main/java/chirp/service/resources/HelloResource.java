package chirp.service.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/")
public class HelloResource {

	@GET
	@Path("hello/{name}")
	public String getHello(@PathParam("name") String name) {
		return "Hello, " + name + "!";
	}

}
