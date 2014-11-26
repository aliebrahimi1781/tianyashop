#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.extend.hqlsearch.parse.impl;

import ${package}.core.common.hibernate.qbc.CriteriaQuery;
import ${package}.core.extend.hqlsearch.HqlGenerateUtil;
import ${package}.core.extend.hqlsearch.parse.IHqlParse;

public class ShortParseImpl implements IHqlParse {

	
	public void addCriteria(CriteriaQuery cq, String name, Object value) {
		if (HqlGenerateUtil.isNotEmpty(value))
			cq.eq(name, value);
	}

	
	public void addCriteria(CriteriaQuery cq, String name, Object value,
			String beginValue, String endValue) {
		if (HqlGenerateUtil.isNotEmpty(beginValue)) {
			cq.ge(name, Short.parseShort(beginValue));
		}
		if (HqlGenerateUtil.isNotEmpty(endValue)) {
			cq.le(name, Short.parseShort(endValue));
		}
		if (HqlGenerateUtil.isNotEmpty(value)) {
			cq.eq(name, value);
		}
	}

}
