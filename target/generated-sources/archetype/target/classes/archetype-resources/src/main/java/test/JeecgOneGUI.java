#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package test;

import ${package}.codegenerate.window.CodeWindow;


/**
 * 【单表模型】代码生成器入口
 * @author 张代浩
 *
 */
public class JeecgOneGUI {

	public static void main(String[] args) {
		CodeWindow  codeWindow = new CodeWindow();
		codeWindow.pack();
	}
}
