#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.cgform.exception;

@SuppressWarnings("serial")
public class BusinessException extends Exception {
	
	public BusinessException(String msg)
	 {
	  super(msg);
	 }
}
