#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.cgform.exception;

@SuppressWarnings("serial")
public class DBException extends Exception {
	
	public DBException(String msg)
	 {
	  super(msg);
	 }
}
