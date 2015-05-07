package cn.walle.framework.core.support.hibernate;

import java.sql.Types;

import org.hibernate.type.StandardBasicTypes;

public class Oracle10gDialect extends org.hibernate.dialect.Oracle10gDialect {

	public Oracle10gDialect() {
		super();
		registerHibernateType(Types.CHAR, StandardBasicTypes.STRING.getName());
	}
	
}
