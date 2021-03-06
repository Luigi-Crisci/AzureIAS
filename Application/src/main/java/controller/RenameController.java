package controller;

import javax.naming.InvalidNameException;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import exeption.AlreadyExistingException;
import exeption.BlobNotFoundExeption;
import service.StorageService;

@Controller
public class RenameController {
	
	@Autowired
	private StorageService storageService;
	
	@PostMapping("/account/rename")
	public ResponseEntity<?> rename(@RequestParam(name = "oldFilename", required = true) String oldFilename, 
			@RequestParam(name = "newFilename", required = true)String newFilename, 
			@RequestParam(name = "overwrite", required = false, defaultValue = "false") String overwrite){
		
			Boolean ow=new Boolean(false);
			try{
				ow=Boolean.parseBoolean(overwrite);
			}catch (Exception e) {
				ow=false;
			}
			
			String newKey = null;
			try {
						newKey = storageService.rename(oldFilename, newFilename, ow);
				} catch (InvalidNameException e) {
					return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
				} catch (IllegalArgumentException e) {
					return new ResponseEntity<>("Error while deleting blob, plese try later",HttpStatus.INTERNAL_SERVER_ERROR);
				} catch (BlobNotFoundExeption e) {
					return new ResponseEntity<>("Original blob not found",HttpStatus.BAD_REQUEST);
				} catch (AlreadyExistingException e) {
					return new ResponseEntity<>("There are already a file with the specified name, plese change it "
							+ "or check the overwrite label",HttpStatus.BAD_REQUEST);
				}
			
			
			return new ResponseEntity<>("{ \"key\": \""+newKey+"\" }",HttpStatus.OK);
		
	}

}
