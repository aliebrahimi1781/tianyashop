#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.common.hibernate.dialect;


public class DerbyDialect extends Dialect
{
  public boolean supportsLimit()
  {
    return false;
  }

  public boolean supportsLimitOffset()
  {
    return false;
  }

  public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder)
  {
    throw new UnsupportedOperationException("paged queries not supported");
  }
}