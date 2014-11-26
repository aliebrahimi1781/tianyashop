#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.extend.swftools;

/**
 * PDF文档转换接口
 * @author 张代浩
 *
 */
public interface PDFConverter {
	public void convert2PDF(String inputFile,String pdfFile,String extend);
	public void convert2PDF(String inputFile,String extend);

}
