package cn.walle.framework.core.support.hibernate;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * IdentifierGenerator for Hibernate.
 * Use java.util.UUID
 * @author cj
 *
 */
public class UUIDGenerator implements IdentifierGenerator {

	/**
	 * Generate a new identifier using java.util.UUID
	 */
	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {
		return UUID.randomUUID().toString();
	}

}
