package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import service.StorageService;

@Controller
public class DeleteController {
	
	@Autowired
	private StorageService storageController;

	@PostMapping("/account/delete")
	public ResponseEntity<?> delete(@RequestParam(required = true, name = "file") String fileName){
			storageController.delete(fileName);
			return new ResponseEntity<>(String.format("Deleted blob %s successfully!", fileName),HttpStatus.OK);
	}
	
	@ExceptionHandler({Exception.class})
	public ResponseEntity<?> handlingError(){
		return new ResponseEntity<>(String.format("Unable to delete blob"),HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
