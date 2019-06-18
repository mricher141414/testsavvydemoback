package com.example.Timesheet.com.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	@ApiOperation("Returns a list of all the items in the queue")
	public List<QueueItem> getAll() {
		log.debug("Entering getAll");
		return queueService.getAll();
	}
	
	@PostMapping(Paths.QueueBasicPath)
	@ApiOperation("Create an entry in the queue based on the timesheet received in body")
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
	
	@GetMapping(Paths.ApiCallToBatFile)
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
