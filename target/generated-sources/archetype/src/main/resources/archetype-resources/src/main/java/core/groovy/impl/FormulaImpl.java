#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.groovy.impl;

import ${package}.core.groovy.IScript;
import org.springframework.stereotype.Component;

/**
 * 公式脚本
 */
@Component
public class FormulaImpl implements IScript {
	public Double add(Double a,Double b) {
		return a*b;
	}
}