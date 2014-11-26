#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.groovy;

import java.util.HashMap;
import java.util.Map;

import ${package}.core.util.ApplicationContextUtil;
import org.springframework.context.ApplicationContext;


/**
 * 公式计算
 * 
 */
public class GroovyParse {
	/**
	 * 公式解析计算
	 */
	public static Object formulaParse(String formula, Map<String, Object> map) {
		ApplicationContext context = ApplicationContextUtil.getContext();
		GroovyScriptEngine groovyScriptEngine = context.getBean(GroovyScriptEngine.class);
		Object value = groovyScriptEngine.executeObject(formula, map);
		return value;
	}

	public static void main(String[] args) {
		String formula = "println 'Hello World!';po = '9s00';return (a * b);";
		Map map = new HashMap();
		map.put("a", 900);
		map.put("b", 10);
		GroovyScriptEngine groovyScriptEngine = new GroovyScriptEngine();
		Object value = groovyScriptEngine.executeObject(formula, map);
		${package}.core.util.LogUtil.info(value);
		${package}.core.util.LogUtil.info(groovyScriptEngine.binding.getVariable("po"));
	}

}
