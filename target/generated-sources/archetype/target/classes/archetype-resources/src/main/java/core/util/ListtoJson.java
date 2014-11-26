#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.util;

import java.util.List;

import ${package}.web.system.pojo.base.TSFunction;


public class ListtoJson {

	public static String getJsonData(@SuppressWarnings("rawtypes") List list) {

		StringBuffer buffer = new StringBuffer();

		buffer.append("[");

		iterGet(list, "0", buffer);

		buffer.append("]");

		// 将,${symbol_escape}n]替换成${symbol_escape}n]

		String tmp = buffer.toString();

		tmp = tmp.replaceAll(",${symbol_escape}n]", "${symbol_escape}n]");

		return tmp;

	}

	static int count = 0;

	/**
	 * 
	 * 递归生成json格式的数据{id:1,text:'',children:[]}
	 * 
	 * @param args
	 */

	static void iterGet(List<TSFunction> list, String pid, StringBuffer buffer) {
		for (TSFunction node : list) {

			// 查找所有父节点为pid的所有对象，然后拼接为json格式的数据
			if(node.getTSFunction()!=null)
			{			
			if (pid.equals(oConvertUtils.getString(node.getTSFunction().getId())))
			{
				count++;
				buffer.append("{${symbol_escape}'id${symbol_escape}':" + node.getId() + ",${symbol_escape}'text${symbol_escape}':${symbol_escape}'"
						+ node.getFunctionName() + "${symbol_escape}',${symbol_escape}'children${symbol_escape}':[");
				// 递归
				iterGet(list, node.getId(), buffer);
				buffer.append("]},${symbol_escape}n");
				count--;

			}
			}
		}

	}

}
