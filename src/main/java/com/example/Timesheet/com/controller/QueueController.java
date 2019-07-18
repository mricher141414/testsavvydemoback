package com.example.Timesheet.com.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Timesheet.com.GlobalFunctions;
import com.example.Timesheet.com.GlobalMessages;
import com.example.Timesheet.com.Paths;
import com.example.Timesheet.com.dto.TimesheetDto;
import com.example.Timesheet.com.model.QueueItem;
import com.example.Timesheet.com.service.QueueService;
import com.example.Timesheet.com.service.TimesheetService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Api(tags = "QueueController")
public class QueueController implements Serializable {

	private static final long serialVersionUID = 434988359096145233L;
	private static final Logger log = LogManager.getLogger(QueueController.class);

	@Autowired
	private QueueService queueService;
	
	@Autowired
	private TimesheetService timesheetService;
	
	@GetMapping(Paths.QueueBasicPath)
	@ApiOperation(value = "Returns a list of all the items in the queue",
					response = QueueItem.class, responseContainer = "List")
	public List<QueueItem> getAll() {
		log.debug("Entering getAll");
		return queueService.getAll();
	}
	
	@GetMapping(Paths.QueueExistsByTimesheet)
	@ApiOperation(value = "Returns a boolean to check if a queueItem with the sent timesheet id already exists",
					response = boolean.class, 
					notes = "404 if the timesheet identifier cannot be found")
	public ResponseEntity<String> getByTimesheet(@ApiParam(value = "Identifier of the timesheet to search for in the queue", required = true) @RequestParam(value = "id") int id) {
		log.debug("Entering getByTimesheet with id parameter of " + id);
		
		if(timesheetService.timesheetExists(id) == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetIdNotFound, Paths.QueueExistsByTimesheet);
		}
		
		return GlobalFunctions.createOkResponseFromObject(queueService.existByTimesheet(id));
	}
	
	@PostMapping(Paths.QueueBasicPath)
	@ApiOperation(value = "Create an entry in the queue based on the timesheet received in body", 
					response = QueueItem.class)
	public ResponseEntity<String> create(@ApiParam(value = "Timesheet to add to the queue.", required = true)@RequestBody TimesheetDto timesheetDto) {
		log.debug("Entering create");
		
		if(timesheetService.timesheetExists(timesheetDto.getId()) == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetIdNotFound, Paths.QueueBasicPath);
		}
		
		QueueItem queueItem = new QueueItem();
		
		queueItem.setTimesheetId(timesheetDto.getId());
		queueService.save(queueItem);
		
		return GlobalFunctions.createOkResponseFromObject(timesheetDto);
	}
	
	@DeleteMapping(Paths.QueueByTimesheet)
	@ApiOperation(value = "Deletes an entry in the queue based on the timesheet id sent as a parameter",
					response = QueueItem.class)
	public ResponseEntity<String> delete(@ApiParam(value = "Id of the timesheet to remove from the queue", required = true)@RequestParam(value = "id") int id) {
		log.debug("Entering delete");
		
		if(timesheetService.timesheetExists(id) == false) {
			return GlobalFunctions.createNotFoundResponse(GlobalMessages.TimesheetIdNotFound, Paths.QueueBasicPath);
		}
		
		List<QueueItem> queueItemList = queueService.findByTimesheetId(id);
		QueueItem queueItem = new QueueItem();
		
		if(queueItemList.size() > 0) {
			
			for (QueueItem item: queueItemList) {
				
				queueItem = item;
				queueService.delete(queueItem);
				return GlobalFunctions.createOkResponseFromObject(queueItem);
			}
			
		}
		return GlobalFunctions.createOkResponseFromObject(queueItem);
	}
	
	
	@GetMapping(Paths.ApiCallToBatFile)
	@ApiIgnore
	public boolean callBat() {
		log.debug("Entering callBat");	
		
		try {
			Runtime.getRuntime().exec("cmd /c start \"\" Calculation.bat");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
