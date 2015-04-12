package au.com.unico.controller;

import java.util.ArrayList;
import java.util.List;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import au.com.unico.model.Pair;
import au.com.unico.model.Parameter;
import au.com.unico.model.ParametersResponse;
import au.com.unico.service.ParameterPersistenceBeanLocal;

@RestController
@RequestMapping("/queueInterface")
public class QueueInterfaceService {
	
	private static final Logger logger = LoggerFactory
			.getLogger(QueueInterfaceService.class);	

	
	private ConnectionFactory connectionFactory;
	private Destination queue;
	
	@Autowired
	private ParameterPersistenceBeanLocal paramPersistenceService;
	
	QueueInterfaceService(){
		try {
			InitialContext ic = new InitialContext();
			this.connectionFactory = (ConnectionFactory)ic.lookup("java:/ConnectionFactory");
			this.queue = (Queue) ic.lookup("java:/unico/parameterQueue");
		} catch (NamingException e) {
			logger.error("Exception in look up ",e);
		}	
	}
	
	@RequestMapping(value = "/push", method = RequestMethod.POST)
	public String push(@RequestParam int param1, @RequestParam int param2) {

		if (connectionFactory == null) {
			return "Failed";
		}

		if (queue == null) {
			return "Failed";
		}
		
		Pair pair = new Pair();
		
		pair.setParam1(param1);
		pair.setParam2(param2);
		
		paramPersistenceService.addToQueueAndSave(pair);

		return "Success";

	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@Produces("application/json")
	public List<ParametersResponse> list(){
		List<Parameter> params = paramPersistenceService.getParams();
		List<ParametersResponse> responses = new ArrayList<ParametersResponse>();
		
		ParametersResponse response = null;
		for (Parameter param: params){
			response = new ParametersResponse();
			response.setParameterValue(param.getValue().intValue());
			response.setParameterDate(param.getEnteredDate());
			responses.add(response);
		}
		return responses;
	}

}
