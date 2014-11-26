#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * @author  张代浩
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface JeecgEntityTitle {
	  String name();
}
