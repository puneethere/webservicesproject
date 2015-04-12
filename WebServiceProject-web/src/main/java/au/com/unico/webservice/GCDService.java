package au.com.unico.webservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jws.WebService;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.unico.model.GCD;
import au.com.unico.model.Pair;
import au.com.unico.service.GCDPersistenceBeanLocal;
import au.com.unico.util.WebServiceUtils;

@WebService
public class GCDService {

	private ConnectionFactory connectionFactory;
	private Queue queue;
	private GCDPersistenceBeanLocal gcdPersistence;
	
	private static final Logger logger = LoggerFactory
			.getLogger(GCDService.class);	

	public GCDService() {
		try {
			InitialContext ic = new InitialContext();
			this.connectionFactory = (ConnectionFactory)ic.lookup("java:/ConnectionFactory");
			this.queue = (Queue) ic.lookup("java:/unico/parameterQueue");
			this.gcdPersistence = (GCDPersistenceBeanLocal) ic.lookup("java:global/GCDPersistenceBean");
		} catch (NamingException e) {
			logger.error("Exception in lookup ", e);
		}	
	}
	public int gcd(){
		Connection connection = null;
		Session session = null;
		int gcd = 0;
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	        MessageConsumer subscriber = session.createConsumer(queue);

	        Message message = subscriber.receive();
	        ObjectMessage objMsg = (ObjectMessage) message;
	        Pair pair = (Pair) objMsg.getObject();
	        gcd = WebServiceUtils.findGCD(pair.getParam1(), pair.getParam2());
	        GCD gcdModel = new GCD();
	        gcdModel.setGcd(gcd);
	        gcdModel.setEnteredDate(new Date());
	        gcdPersistence.saveGCD(gcdModel);
		} catch (JMSException e) {
			logger.error("Exception in calculating GCD ", e);
		}finally{
			if (session != null){
				try {
					session.close();
				} catch (JMSException e) {
					logger.error("Exception in Closing the JMS Session ", e);
				}
			}
			if (connection != null){
				try {
					connection.close();
				} catch (JMSException e) {
					logger.error("Exception in Closing the JMS Connection ", e);
				}
			}
		}
		return gcd;

	}
	
	public ArrayList<Integer> gcdList(){
		List<GCD> gcds = gcdPersistence.getGCDs();
		ArrayList<Integer> gcdInts = new ArrayList<Integer>();
		for (GCD gcd :gcds){
			gcdInts.add(gcd.getGcd());
		}
		return gcdInts;
	}
	
	public int gcdSum(){
		List<GCD> gcds = gcdPersistence.getGCDs();
		Integer gcdSum = 0;
		for (GCD gcd :gcds){
			gcdSum = gcdSum + gcd.getGcd();
		}
		return gcdSum;
	}
}
