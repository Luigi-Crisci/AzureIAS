package myapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
	
	@Autowired(required = true)
	private StorageConnectorBean connectorBean;
	

}
