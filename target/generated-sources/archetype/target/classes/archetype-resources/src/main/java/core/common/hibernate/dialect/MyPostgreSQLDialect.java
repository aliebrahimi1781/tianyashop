#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.common.hibernate.dialect;

import org.hibernate.dialect.PostgreSQLDialect;

public class MyPostgreSQLDialect extends PostgreSQLDialect {

	
	public boolean useInputStreamToInsertBlob() {
		// TODO Auto-generated method stub
		return true;
	}

}
