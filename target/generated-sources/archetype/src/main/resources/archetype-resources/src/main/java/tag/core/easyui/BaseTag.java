#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.tag.core.easyui;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import ${package}.core.util.oConvertUtils;


/**
 * 
 * @author  张代浩
 *
 */
public class BaseTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String type = "default";// 加载类型

	public void setType(String type) {
		this.type = type;
	}

	
	public int doStartTag() throws JspException {
		return EVAL_PAGE;
	}

	
	public int doEndTag() throws JspException {
		try {
			JspWriter out = this.pageContext.getOut();
			StringBuffer sb = new StringBuffer();

			String types[] = type.split(",");
			if (oConvertUtils.isIn("jquery-webos", types)) {
                sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/sliding/js/jquery-1.7.1.min.js${symbol_escape}"></script>");
			} else if (oConvertUtils.isIn("jquery", types)) {
				sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/jquery/jquery-1.8.3.js${symbol_escape}"></script>");
			}
			if (oConvertUtils.isIn("ckeditor", types)) {
				sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/ckeditor/ckeditor.js${symbol_escape}"></script>");
				sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/tools/ckeditorTool.js${symbol_escape}"></script>");
			}
			if (oConvertUtils.isIn("ckfinder", types)) {
				sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/ckfinder/ckfinder.js${symbol_escape}"></script>");
				sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/tools/ckfinderTool.js${symbol_escape}"></script>");
			}
			if (oConvertUtils.isIn("easyui", types)) {
				sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/tools/dataformat.js${symbol_escape}"></script>");
				sb.append("<link id=${symbol_escape}"easyuiTheme${symbol_escape}" rel=${symbol_escape}"stylesheet${symbol_escape}" href=${symbol_escape}"plug-in/easyui/themes/default/easyui.css${symbol_escape}" type=${symbol_escape}"text/css${symbol_escape}"></link>");
				sb.append("<link rel=${symbol_escape}"stylesheet${symbol_escape}" href=${symbol_escape}"plug-in/easyui/themes/icon.css${symbol_escape}" type=${symbol_escape}"text/css${symbol_escape}"></link>");
				sb.append("<link rel=${symbol_escape}"stylesheet${symbol_escape}" type=${symbol_escape}"text/css${symbol_escape}" href=${symbol_escape}"plug-in/accordion/css/accordion.css${symbol_escape}">");
				sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/easyui/jquery.easyui.min.1.3.2.js${symbol_escape}"></script>");
				sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/easyui/locale/easyui-lang-zh_CN.js${symbol_escape}"></script>");
				sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/tools/syUtil.js${symbol_escape}"></script>");
				sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/easyui/extends/datagrid-scrollview.js${symbol_escape}"></script>");
			}
			if (oConvertUtils.isIn("DatePicker", types)) {
				sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/My97DatePicker/WdatePicker.js${symbol_escape}"></script>");
			}
			if (oConvertUtils.isIn("jqueryui", types)) {
				sb.append("<link rel=${symbol_escape}"stylesheet${symbol_escape}" href=${symbol_escape}"plug-in/jquery-ui/css/ui-lightness/jquery-ui-1.9.2.custom.min.css${symbol_escape}" type=${symbol_escape}"text/css${symbol_escape}"></link>");
				sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/jquery-ui/js/jquery-ui-1.9.2.custom.min.js${symbol_escape}"></script>");
			}
			if (oConvertUtils.isIn("jqueryui-sortable", types)) {
				sb.append("<link rel=${symbol_escape}"stylesheet${symbol_escape}" href=${symbol_escape}"plug-in/jquery-ui/css/ui-lightness/jquery-ui-1.9.2.custom.min.css${symbol_escape}" type=${symbol_escape}"text/css${symbol_escape}"></link>");
				sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/jquery-ui/js/ui/jquery.ui.core.js${symbol_escape}"></script>");
				sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/jquery-ui/js/ui/jquery.ui.widget.js${symbol_escape}"></script>");
				sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/jquery-ui/js/ui/jquery.ui.mouse.js${symbol_escape}"></script>");
				sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/jquery-ui/js/ui/jquery.ui.sortable.js${symbol_escape}"></script>");
			}
			if (oConvertUtils.isIn("prohibit", types)) {
				sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/tools/prohibitutil.js${symbol_escape}"></script>");		}
			if (oConvertUtils.isIn("designer", types)) {
				sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/designer/easyui/jquery-1.7.2.min.js${symbol_escape}"></script>");
				sb.append("<link id=${symbol_escape}"easyuiTheme${symbol_escape}" rel=${symbol_escape}"stylesheet${symbol_escape}" href=${symbol_escape}"plug-in/designer/easyui/easyui.css${symbol_escape}" type=${symbol_escape}"text/css${symbol_escape}"></link>");
				sb.append("<link rel=${symbol_escape}"stylesheet${symbol_escape}" href=${symbol_escape}"plug-in/designer/easyui/icon.css${symbol_escape}" type=${symbol_escape}"text/css${symbol_escape}"></link>");
				sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/designer/easyui/jquery.easyui.min.1.3.0.js${symbol_escape}"></script>");
				sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/designer/easyui/locale/easyui-lang-zh_CN.js${symbol_escape}"></script>");
				sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/tools/syUtil.js${symbol_escape}"></script>");
				
				sb.append("<script type=${symbol_escape}'text/javascript${symbol_escape}' src=${symbol_escape}'plug-in/jquery/jquery-autocomplete/lib/jquery.bgiframe.min.js${symbol_escape}'></script>");
				sb.append("<script type=${symbol_escape}'text/javascript${symbol_escape}' src=${symbol_escape}'plug-in/jquery/jquery-autocomplete/lib/jquery.ajaxQueue.js${symbol_escape}'></script>");
				sb.append("<script type=${symbol_escape}'text/javascript${symbol_escape}' src=${symbol_escape}'plug-in/jquery/jquery-autocomplete/jquery.autocomplete.min.js${symbol_escape}'></script>");
				sb.append("<link href=${symbol_escape}"plug-in/designer/designer.css${symbol_escape}" type=${symbol_escape}"text/css${symbol_escape}" rel=${symbol_escape}"stylesheet${symbol_escape}" />");
				sb.append("<script src=${symbol_escape}"plug-in/designer/draw2d/wz_jsgraphics.js${symbol_escape}"></script>");
				sb.append("<script src=${symbol_escape}'plug-in/designer/draw2d/mootools.js${symbol_escape}'></script>");
				sb.append("<script src=${symbol_escape}'plug-in/designer/draw2d/moocanvas.js${symbol_escape}'></script>");
				sb.append("<script src=${symbol_escape}'plug-in/designer/draw2d/draw2d.js${symbol_escape}'></script>");
				sb.append("<script src=${symbol_escape}"plug-in/designer/MyCanvas.js${symbol_escape}"></script>");
				sb.append("<script src=${symbol_escape}"plug-in/designer/ResizeImage.js${symbol_escape}"></script>");
				sb.append("<script src=${symbol_escape}"plug-in/designer/event/Start.js${symbol_escape}"></script>");
				sb.append("<script src=${symbol_escape}"plug-in/designer/event/End.js${symbol_escape}"></script>");
				sb.append("<script src=${symbol_escape}"plug-in/designer/connection/MyInputPort.js${symbol_escape}"></script>");
				sb.append("<script src=${symbol_escape}"plug-in/designer/connection/MyOutputPort.js${symbol_escape}"></script>");
				sb.append("<script src=${symbol_escape}"plug-in/designer/connection/DecoratedConnection.js${symbol_escape}"></script>");
				sb.append("<script src=${symbol_escape}"plug-in/designer/task/Task.js${symbol_escape}"></script>");
				sb.append("<script src=${symbol_escape}"plug-in/designer/task/UserTask.js${symbol_escape}"></script>");
				sb.append("<script src=${symbol_escape}"plug-in/designer/task/ManualTask.js${symbol_escape}"></script>");
				sb.append("<script src=${symbol_escape}"plug-in/designer/task/ServiceTask.js${symbol_escape}"></script>");
				sb.append("<script src=${symbol_escape}"plug-in/designer/gateway/ExclusiveGateway.js${symbol_escape}"></script>");
				sb.append("<script src=${symbol_escape}"plug-in/designer/gateway/ParallelGateway.js${symbol_escape}"></script>");
				sb.append("<script src=${symbol_escape}"plug-in/designer/boundaryevent/TimerBoundary.js${symbol_escape}"></script>");
				sb.append("<script src=${symbol_escape}"plug-in/designer/boundaryevent/ErrorBoundary.js${symbol_escape}"></script>");
				sb.append("<script src=${symbol_escape}"plug-in/designer/subprocess/CallActivity.js${symbol_escape}"></script>");
				sb.append("<script src=${symbol_escape}"plug-in/designer/task/ScriptTask.js${symbol_escape}"></script>");
				sb.append("<script src=${symbol_escape}"plug-in/designer/task/MailTask.js${symbol_escape}"></script>");
				sb.append("<script src=${symbol_escape}"plug-in/designer/task/ReceiveTask.js${symbol_escape}"></script>");
				sb.append("<script src=${symbol_escape}"plug-in/designer/task/BusinessRuleTask.js${symbol_escape}"></script>");
				sb.append("<script src=${symbol_escape}"plug-in/designer/designer.js${symbol_escape}"></script>");
				sb.append("<script src=${symbol_escape}"plug-in/designer/mydesigner.js${symbol_escape}"></script>");

			}
			if (oConvertUtils.isIn("tools", types)) {
				sb.append("<link rel=${symbol_escape}"stylesheet${symbol_escape}" href=${symbol_escape}"plug-in/tools/css/common.css${symbol_escape}" type=${symbol_escape}"text/css${symbol_escape}"></link>");
				sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/lhgDialog/lhgdialog.min.js${symbol_escape}"></script>");
				sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/tools/curdtools.js${symbol_escape}"></script>");
				sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/tools/easyuiextend.js${symbol_escape}"></script>");
				sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/jquery-plugs/hftable/jquery-hftable.js${symbol_escape}"></script>");
			}
			if (oConvertUtils.isIn("toptip", types)) {
				sb.append("<link rel=${symbol_escape}"stylesheet${symbol_escape}" href=${symbol_escape}"plug-in/toptip/css/css.css${symbol_escape}" type=${symbol_escape}"text/css${symbol_escape}"></link>");
				sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/toptip/manhua_msgTips.js${symbol_escape}"></script>");
			}
			if (oConvertUtils.isIn("autocomplete", types)) {
				sb.append("<link rel=${symbol_escape}"stylesheet${symbol_escape}" href=${symbol_escape}"plug-in/jquery/jquery-autocomplete/jquery.autocomplete.css${symbol_escape}" type=${symbol_escape}"text/css${symbol_escape}"></link>");
				sb.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/jquery/jquery-autocomplete/jquery.autocomplete.min.js${symbol_escape}"></script>");
			}
			out.print(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

}
