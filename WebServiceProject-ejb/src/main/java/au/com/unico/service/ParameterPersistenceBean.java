package au.com.unico.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.unico.model.Pair;
import au.com.unico.model.Parameter;

/**
 * Session Bean implementation class ParameterPersistenceBean
 */
@Stateless
@LocalBean
@EJB(name = "java:global/ParameterPersistenceBean", beanInterface = ParameterPersistenceBeanLocal.class)
public class ParameterPersistenceBean implements ParameterPersistenceBeanLocal {

	public final String LIST_QUERY = "select p from Parameter p ORDER by p.id";
	// pass persistence unit to entityManager.
	@PersistenceContext(unitName = "MqSQLEJBPersistence")
	private EntityManager entityManager;

	@Resource(lookup = "java:/ConnectionFactory")
	private static ConnectionFactory connectionFactory;

	@Resource(lookup = "java:/unico/parameterQueue")
	private static Queue queue;

	private static final Logger logger = LoggerFactory
			.getLogger(ParameterPersistenceBean.class);

	/**
	 * Default constructor.
	 */
	public ParameterPersistenceBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void saveParameter(Parameter param) {
		entityManager.persist(param);
	}

	@Override
	public List<Parameter> getParams() {
		List<Parameter> params = entityManager.createQuery(LIST_QUERY)
				.getResultList();
		return params;
	}

	public void addToQueueAndSave(Pair pair) {
		Connection connection = null;
		Session session = null;
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(queue);
			ObjectMessage message = session.createObjectMessage();
			message.setObject(pair);
			producer.send(message);
			Date dt = new Date();
			Parameter param1 = new Parameter();
			param1.setValue(new Long(pair.getParam1()));
			param1.setEnteredDate(dt);
			this.saveParameter(param1);
			Parameter param2 = new Parameter();
			param2.setValue(new Long(pair.getParam2()));
			param2.setEnteredDate(dt);
			this.saveParameter(param2);

		} catch (Exception e) {
			logger.error(
					"Exception in adding the parameter to the queue and to the database ",
					e);
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (JMSException e) {
					logger.error("Exception in closing the JMS Session ", e);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					logger.error("Exception in closing the JMS Connection ", e);
				}
			}

		}

	}

}
