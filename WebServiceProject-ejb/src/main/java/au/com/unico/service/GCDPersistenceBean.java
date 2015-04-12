package au.com.unico.service;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.unico.model.GCD;

/**
 * Session Bean implementation class ParameterPersistenceBean
 */
@Stateless
@LocalBean
@EJB(name = "java:global/GCDPersistenceBean", beanInterface = GCDPersistenceBeanLocal.class)
public class GCDPersistenceBean implements GCDPersistenceBeanLocal {

	public final String LIST_QUERY = "select g from GCD g ORDER by g.id";
	// pass persistence unit to entityManager.
	@PersistenceContext(unitName = "MqSQLEJBPersistence")
	private EntityManager entityManager;

	@Resource(lookup = "java:/ConnectionFactory")
	private static ConnectionFactory connectionFactory;

	@Resource(lookup = "java:/unico/parameterQueue")
	private static Queue queue;
	
	private static final Logger logger = LoggerFactory
			.getLogger(GCDPersistenceBean.class);	


	/**
	 * Default constructor.
	 */
	public GCDPersistenceBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void saveGCD(GCD gcd) {
		entityManager.persist(gcd);
	}

	@Override
	public List<GCD> getGCDs() {
		List<GCD> gcds = entityManager.createQuery(LIST_QUERY)
				.getResultList();
		return gcds;
	}
}
