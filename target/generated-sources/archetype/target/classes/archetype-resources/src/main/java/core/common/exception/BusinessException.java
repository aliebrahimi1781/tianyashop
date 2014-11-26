#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.common.exception;

public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BusinessException(String message){
		super(message);
	}
	
	public BusinessException(Throwable cause)
	{
		super(cause);
	}
	
	public BusinessException(String message,Throwable cause)
	{
		super(message,cause);
	}
}
