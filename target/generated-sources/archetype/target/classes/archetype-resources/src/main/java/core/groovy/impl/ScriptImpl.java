#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.groovy.impl;

import ${package}.core.groovy.IScript;
import org.springframework.stereotype.Component;

/**
 * 系统脚本
 */
@Component
public class ScriptImpl implements IScript {
	public String getCurrentUserId() {
		return "1";
	}
}