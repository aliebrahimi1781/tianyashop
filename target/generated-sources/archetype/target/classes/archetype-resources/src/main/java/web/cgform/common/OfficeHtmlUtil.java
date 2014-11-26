#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.cgform.common;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import ${package}.core.util.LogUtil;
import org.w3c.dom.Document;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;


public class OfficeHtmlUtil {

	int WORD_HTML = 8;
	int WORD_TXT = 7;
	int EXCEL_HTML = 44;

	//清除HTML标签匹配内容
	private final static String regEx_style = "<[${symbol_escape}${symbol_escape}s]*?(style|xml|meta|font|xml|del|ins|o:p|head|!)[^>]*?>[${symbol_escape}${symbol_escape}s${symbol_escape}${symbol_escape}S]*?<[${symbol_escape}${symbol_escape}s]*?${symbol_escape}${symbol_escape}/[${symbol_escape}${symbol_escape}s]*?(style|xml|meta|font|xml|del|ins|o:p|head|>)[${symbol_escape}${symbol_escape}s]*?>";
	
	//清除多余属性匹配内容
	private final static String regEx_attr1 = "[${symbol_escape}${symbol_escape}s] ?xmlns?(:v|:o|:w|)=${symbol_escape}"([^${symbol_escape}"]+)${symbol_escape}"";
	private final static String regEx_attr2 = "style=${symbol_escape}'([^${symbol_escape}']+)${symbol_escape}'";
	private final static String regEx_attr3 = "[${symbol_escape}${symbol_escape}s]?(class|lang)=([^?(${symbol_escape}${symbol_escape}s|>)]+)";
	private final static String regEx_attr4 = "<span[^>]+>";
	private final static String regEx_attr5 = "<${symbol_escape}${symbol_escape}/span>";
	
	
	//过滤布局硬宽度
	private final static String regEx_attr7 = "width=.[0-9]*";
	//过滤文字顶格
	private final static String regEx_attr8 = "valign=top";
	
	//生成输入框匹配内容
	private final static String regEx_attr6 = "${symbol_escape}${symbol_escape}${symbol_pound}{([a-zA-Z_0-9]+)${symbol_escape}${symbol_escape}}";
	
	//替换标签 针对于<input />类型标签
	private final static String regEx_replace = "[^>]+>";

	/**
	 * WORD转HTML
	 * 
	 * @param docfile
	 *            WORD文件全路径
	 * @param htmlfile
	 *            转换后HTML存放路径
	 */
	public void wordToHtml(String docfile, String htmlfile) {
		ActiveXComponent app = new ActiveXComponent("Word.Application"); // 启动word
		try {
			app.setProperty("Visible", new Variant(false));
			Dispatch docs = app.getProperty("Documents").toDispatch();
			Dispatch doc = Dispatch.invoke(docs, "Open", Dispatch.Method, new Object[] { docfile, new Variant(false), new Variant(true) }, new int[1]).toDispatch();
			Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] { htmlfile, new Variant(WORD_HTML) }, new int[1]);
			Variant f = new Variant(false);
			Dispatch.call(doc, "Close", f);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			app.invoke("Quit", new Variant[] {});
		}
	}

	/**
	 * WORD转HTML
	 * 
	 * @param docfile
	 *            WORD文件全路径
	 * @param htmlfile
	 *            转换后HTML存放路径
	 * @throws Throwable
	 * add by duanql	2013-07-17
	 */

	public void WordConverterHtml(String docfile, String htmlfile){
		try {
		InputStream input = new FileInputStream(docfile);
		HWPFDocument wordDocument = new HWPFDocument(input);
		WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
		wordToHtmlConverter.processDocument(wordDocument);
		Document htmlDocument = wordToHtmlConverter.getDocument();
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		DOMSource domSource = new DOMSource(htmlDocument);
		StreamResult streamResult = new StreamResult(outStream);

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer serializer = tf.newTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.setOutputProperty(OutputKeys.METHOD, "html");
		serializer.transform(domSource, streamResult);
		outStream.close();

		String content = new String(outStream.toByteArray(), "UTF-8");
		stringToFile(content,htmlfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	/**
	 * EXCEL转HTML
	 * 
	 * @param xlsfile
	 *            EXCEL文件全路径
	 * @param htmlfile
	 *            转换后HTML存放路径
	 */
	public void excelToHtml(String xlsfile, String htmlfile) {
		ActiveXComponent app = new ActiveXComponent("Excel.Application"); // 启动excel
		try {
			app.setProperty("Visible", new Variant(false));
			Dispatch excels = app.getProperty("Workbooks").toDispatch();
			Dispatch excel = Dispatch.invoke(excels, "Open", Dispatch.Method, new Object[] { xlsfile, new Variant(false), new Variant(true) }, new int[1]).toDispatch();
			Dispatch.invoke(excel, "SaveAs", Dispatch.Method, new Object[] { htmlfile, new Variant(EXCEL_HTML) }, new int[1]);
			Variant f = new Variant(false);
			Dispatch.call(excel, "Close", f);
			${package}.core.util.LogUtil.info("wordtohtml转换成功");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			app.invoke("Quit", new Variant[] {});
		}
	}

	/**
	 * 读文件到字符串
	 * 
	 * @param filename 要读的文件名称
	 *           
	 */
	public  String   getInfo(String tmpFile)   throws   IOException   {  
        StringBuffer   sbFile = null;  
        try{

        FileInputStream fin = new FileInputStream(tmpFile);
        InputStreamReader in = null;
        
        char[]   buffer   =   new   char[4096];  
        int   len;  
        sbFile   =   new   StringBuffer();
        in = new InputStreamReader(fin,"gb2312");
        while   (   (len   =   in.read(buffer))   !=   -1)   {  
            String   s   =   new   String(buffer,   0,   len);  
            sbFile.append(s);  
        }
        }catch (IOException e4) {
        	LogUtil.error(e4.toString());
        }
        
        return   sbFile.toString();  
 }
	/**
	 * 字符串写入文件
	 * 
	 * @param str 要写入的字符串
	 * @param filename 要写入的文件名称
	 *           
	 */
	
	public void stringToFile(String str,String filename){
		   try
		   {
			FileOutputStream fout = new FileOutputStream(filename);
			
		    OutputStreamWriter out = null;
		    out = new OutputStreamWriter(fout,"gb2312");
 	        out.write(str);
		    out.close();
		   }catch (IOException e4) {
				LogUtil.error(e4.toString());
		   }
		  }

	/**
	 * 标签替换  针对于<input />类型标签
	 * 
	 * @param sourceStr html源字符串
	 * @param oldStr 要替换的标签字符串
	 * @param newStr 替换后的标签内容
	 *           
	 */
	
	public String regExReplace(String sourceStr,String oldStr,String newStr){
			java.util.regex.Pattern pattern;   
			java.util.regex.Matcher matcher;
			String ls_comStr = oldStr+regEx_replace;
			String ls_returnStr="";
		   try
		   {
				pattern = Pattern.compile(ls_comStr,Pattern.CASE_INSENSITIVE);   
				matcher = pattern.matcher(sourceStr);   
				ls_returnStr = matcher.replaceAll(newStr);
				
		   }catch (Exception e) {
				LogUtil.error(e.toString());
		   }
		   		
		   		return ls_returnStr;		   
		  }
	
	
	/**
	 * 处理HTML
	 * 
	 * @param htmlStr
	 *            要处理的HTML
	 */
	public String doHtml(String htmlStr) {
		java.util.regex.Pattern pattern;   
	    java.util.regex.Matcher matcher;   
		
		try{
			pattern = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);   
			matcher = pattern.matcher(htmlStr);   
			htmlStr = matcher.replaceAll("");    

			pattern = Pattern.compile(regEx_attr1,Pattern.CASE_INSENSITIVE);   
			matcher = pattern.matcher(htmlStr);   
			htmlStr = matcher.replaceAll("");    
			
			pattern = Pattern.compile(regEx_attr2,Pattern.CASE_INSENSITIVE);   
			matcher = pattern.matcher(htmlStr);   
			htmlStr = matcher.replaceAll("");    

			pattern = Pattern.compile(regEx_attr3,Pattern.CASE_INSENSITIVE);   
			matcher = pattern.matcher(htmlStr);   
			htmlStr = matcher.replaceAll(" ");    

			pattern = Pattern.compile(regEx_attr4,Pattern.CASE_INSENSITIVE);   
			matcher = pattern.matcher(htmlStr);   
			htmlStr = matcher.replaceAll("");    

			pattern = Pattern.compile(regEx_attr5,Pattern.CASE_INSENSITIVE);   
			matcher = pattern.matcher(htmlStr);   
			htmlStr = matcher.replaceAll(""); 
//			pattern = Pattern.compile(regEx_attr7,Pattern.CASE_INSENSITIVE);   
//			matcher = pattern.matcher(htmlStr);   
//			htmlStr = matcher.replaceAll(""); 
			
			pattern = Pattern.compile(regEx_attr8,Pattern.CASE_INSENSITIVE);   
			matcher = pattern.matcher(htmlStr);   
			htmlStr = matcher.replaceAll(""); 
			
			//include
	    	StringBuilder ls_include = new StringBuilder("");
	    	ls_include.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/jquery/jquery-1.8.3.js${symbol_escape}"></script>");
	    	ls_include.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/tools/dataformat.js${symbol_escape}"></script>");
	    	ls_include.append("<link id=${symbol_escape}"easyuiTheme${symbol_escape}" rel=${symbol_escape}"stylesheet${symbol_escape}" href=${symbol_escape}"plug-in/easyui/themes/default/easyui.css${symbol_escape}" type=${symbol_escape}"text/css${symbol_escape}"></link>");
	    	ls_include.append("<link rel=${symbol_escape}"stylesheet${symbol_escape}" href=${symbol_escape}"plug-in/easyui/themes/icon.css${symbol_escape}" type=${symbol_escape}"text/css${symbol_escape}"></link>");
	    	ls_include.append("<link rel=${symbol_escape}"stylesheet${symbol_escape}" type=${symbol_escape}"text/css${symbol_escape}" href=${symbol_escape}"plug-in/accordion/css/accordion.css${symbol_escape}"></link>");
	    	ls_include.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/easyui/jquery.easyui.min.1.3.2.js${symbol_escape}"></script>");
	    	ls_include.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/easyui/locale/easyui-lang-zh_CN.js${symbol_escape}"></script>");
	    	ls_include.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/tools/syUtil.js${symbol_escape}"></script>");
	    	ls_include.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/My97DatePicker/WdatePicker.js${symbol_escape}"></script>");
	    	ls_include.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/lhgDialog/lhgdialog.min.js${symbol_escape}"></script>");
	    	ls_include.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/tools/curdtools.js${symbol_escape}"></script>");
	    	ls_include.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/tools/easyuiextend.js${symbol_escape}"></script>");
	    	ls_include.append("<link rel=${symbol_escape}"stylesheet${symbol_escape}" href=${symbol_escape}"plug-in/Validform/css/style.css${symbol_escape}" type=${symbol_escape}"text/css${symbol_escape}"/>");
	    	ls_include.append("<link rel=${symbol_escape}"stylesheet${symbol_escape}" href=${symbol_escape}"plug-in/Validform/css/tablefrom.css${symbol_escape}" type=${symbol_escape}"text/css${symbol_escape}"/>");
	    	ls_include.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/Validform/js/Validform_v5.3.1_min.js${symbol_escape}"></script>");
	    	ls_include.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/Validform/js/Validform_Datatype.js${symbol_escape}"></script>");
	    	ls_include.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/Validform/js/datatype.js${symbol_escape}"></script>");
	    	ls_include.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/Validform/plugin/passwordStrength/passwordStrength-min.js${symbol_escape}"></script>");
	    	ls_include.append("<script type=${symbol_escape}"text/javascript${symbol_escape}">${symbol_dollar}(function(){${symbol_dollar}(${symbol_escape}"${symbol_pound}formobj${symbol_escape}").Validform({tiptype:4,btnSubmit:${symbol_escape}"${symbol_pound}btn_sub${symbol_escape}",btnReset:${symbol_escape}"${symbol_pound}btn_reset${symbol_escape}",ajaxPost:true,usePlugin:{passwordstrength:{minLen:6,maxLen:18,trigger:function(obj,error){if(error){obj.parent().next().find(${symbol_escape}".Validform_checktip${symbol_escape}").show();obj.find(${symbol_escape}".passwordStrength${symbol_escape}").hide();}else{${symbol_dollar}(${symbol_escape}".passwordStrength${symbol_escape}").show();obj.parent().next().find(${symbol_escape}".Validform_checktip${symbol_escape}").hide();}}}},callback:function(data){var win = frameElement.api.opener;if(data.success==true){frameElement.api.close();win.tip(data.msg);}else{if(data.responseText==''||data.responseText==undefined)${symbol_dollar}(${symbol_escape}"${symbol_pound}formobj${symbol_escape}").html(data.msg);else ${symbol_dollar}(${symbol_escape}"${symbol_pound}formobj${symbol_escape}").html(data.responseText); return false;}win.reloadTable();}});});</script>");
	    	ls_include.append("<style>");
	    	ls_include.append("body{font-size:12px;}");
	    	ls_include.append("table{border: 1px solid ${symbol_pound}000000;padding:0; margin:0 auto;border-collapse: collapse;width:100%;align:right;}");
	    	ls_include.append("td {border: 1px solid ${symbol_pound}000000;background: ${symbol_pound}fff;font-size:12px;padding: 3px 3px 3px 8px;color: ${symbol_pound}000000;word-break: keep-all;}");
	    	ls_include.append("</style>${symbol_escape}r${symbol_escape}n<body");
			pattern = Pattern.compile("<body",Pattern.CASE_INSENSITIVE);   
			matcher = pattern.matcher(htmlStr);   
			htmlStr = matcher.replaceAll(ls_include.toString().replace("${symbol_dollar}", "${symbol_escape}${symbol_escape}${symbol_dollar}"));
			
/*			pattern = Pattern.compile("<p[^>]+>",Pattern.CASE_INSENSITIVE);   
			matcher = pattern.matcher(htmlStr);   
			htmlStr = matcher.replaceAll("");    
			
			pattern = Pattern.compile("<${symbol_escape}${symbol_escape}/p>",Pattern.CASE_INSENSITIVE);   
			matcher = pattern.matcher(htmlStr);   
			htmlStr = matcher.replaceAll("");    
*/			
			
			//添加<form>语句 
			String ls_form = "<form action=${symbol_escape}"cgFormBuildController.do?saveOrUpdate${symbol_escape}" id=${symbol_escape}"formobj${symbol_escape}" name=${symbol_escape}"formobj${symbol_escape}" method=${symbol_escape}"post${symbol_escape}">${symbol_escape}r${symbol_escape}n" +
							 "<input type=${symbol_escape}"hidden${symbol_escape}" name=${symbol_escape}"tableName${symbol_escape}" value=${symbol_escape}"${symbol_escape}${symbol_escape}${symbol_dollar}{tableName?if_exists?html}${symbol_escape}"${symbol_escape}${symbol_escape}/>${symbol_escape}r${symbol_escape}n" +
							 "<input type=${symbol_escape}"hidden${symbol_escape}" name=${symbol_escape}"id${symbol_escape}" value=${symbol_escape}"${symbol_escape}${symbol_escape}${symbol_dollar}{id?if_exists?html}${symbol_escape}"${symbol_escape}${symbol_escape}/>${symbol_escape}r${symbol_escape}n" +
							 "<input type=${symbol_escape}"hidden${symbol_escape}" id=${symbol_escape}"btn_sub${symbol_escape}" class=${symbol_escape}"btn_sub${symbol_escape}"${symbol_escape}${symbol_escape}/>${symbol_escape}r${symbol_escape}n${symbol_pound}{jform_hidden_field}<table";
			pattern = Pattern.compile("<table",Pattern.CASE_INSENSITIVE);   
			matcher = pattern.matcher(htmlStr);   
			htmlStr = matcher.replaceAll(ls_form);    
			
			//添加</form>语句
			pattern = Pattern.compile("</table>",Pattern.CASE_INSENSITIVE);   
			matcher = pattern.matcher(htmlStr);   
			htmlStr = matcher.replaceAll("</table>${symbol_escape}r${symbol_escape}n</form>");    
			
			/*
			//添加input语句
			pattern = Pattern.compile(regEx_attr6,Pattern.CASE_INSENSITIVE);   
			matcher = pattern.matcher(htmlStr);   

			StringBuffer sb = new StringBuffer(); 
			String thStr = "";
			String inputStr = "";

			boolean result = matcher.find(); 
			while(result) {
				thStr = matcher.group(1);
//				inputStr = "<input type=${symbol_escape}"text${symbol_escape}" name=${symbol_escape}""+thStr+"${symbol_escape}" value=${symbol_escape}"${symbol_escape}${symbol_escape}${symbol_dollar}{"+thStr+"?if_exists?html}${symbol_escape}"${symbol_escape}${symbol_escape}/>";
				inputStr = "";
				if(fieldMap.get(thStr)!=null){
					inputStr = FormHtmlUtil.getFormHTML(fieldMap.get(thStr));
					inputStr +="<span class=${symbol_escape}"Validform_checktip${symbol_escape}">&nbsp;</span>";
				}
				matcher.appendReplacement(sb, inputStr); 
				result = matcher.find(); 
			} 
			matcher.appendTail(sb); 
			htmlStr = sb.toString(); 
			*/
			
		}catch(Exception e) {   
        	LogUtil.error(e.getMessage());
		}
		return htmlStr;
	}


	/**
	 * 处理HTML poi转换生成的html
	 * 
	 * @param htmlStr   要处理的HTML
	 * 
	 * add by Duanql 2013-07-22 
	 */
	public String doPoiHtml(String htmlStr) {
		java.util.regex.Pattern pattern;   
	    java.util.regex.Matcher matcher;   
	    
	    //清除HTML标签匹配内容
	    String regEx_poi1 = "<meta[^>]+>";
		
		String regEx_poi2 = "<[${symbol_escape}${symbol_escape}s]*?(style)[^>]*?>[${symbol_escape}${symbol_escape}s${symbol_escape}${symbol_escape}S]*?<[${symbol_escape}${symbol_escape}s]*?${symbol_escape}${symbol_escape}/[${symbol_escape}${symbol_escape}s]*?(style)[${symbol_escape}${symbol_escape}s]*?>";

	    String regEx_poi3 = "[${symbol_escape}${symbol_escape}s]?(class|lang)=([^?(${symbol_escape}${symbol_escape}s|>)]+)";
	    
		try{
			
			
			pattern = Pattern.compile(regEx_poi1,Pattern.CASE_INSENSITIVE);   
			matcher = pattern.matcher(htmlStr);   
			htmlStr = matcher.replaceAll(""); 

			pattern = Pattern.compile(regEx_poi2,Pattern.CASE_INSENSITIVE);   
			matcher = pattern.matcher(htmlStr);   
			htmlStr = matcher.replaceAll(""); 

			pattern = Pattern.compile(regEx_poi3,Pattern.CASE_INSENSITIVE);   
			matcher = pattern.matcher(htmlStr);   
			htmlStr = matcher.replaceAll(""); 

			pattern = Pattern.compile("b2${symbol_escape}"",Pattern.CASE_INSENSITIVE);   
			matcher = pattern.matcher(htmlStr);   
			htmlStr = matcher.replaceAll(""); 
			pattern = Pattern.compile("<tbody>|</tbody>",Pattern.CASE_INSENSITIVE);   
			matcher = pattern.matcher(htmlStr);   
			htmlStr = matcher.replaceAll(""); 
			
			//include
	    	StringBuilder ls_include = new StringBuilder("");
	    	ls_include.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/jquery/jquery-1.8.3.js${symbol_escape}"></script>");
	    	ls_include.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/tools/dataformat.js${symbol_escape}"></script>");
	    	ls_include.append("<link id=${symbol_escape}"easyuiTheme${symbol_escape}" rel=${symbol_escape}"stylesheet${symbol_escape}" href=${symbol_escape}"plug-in/easyui/themes/default/easyui.css${symbol_escape}" type=${symbol_escape}"text/css${symbol_escape}"></link>");
	    	ls_include.append("<link rel=${symbol_escape}"stylesheet${symbol_escape}" href=${symbol_escape}"plug-in/easyui/themes/icon.css${symbol_escape}" type=${symbol_escape}"text/css${symbol_escape}"></link>");
	    	ls_include.append("<link rel=${symbol_escape}"stylesheet${symbol_escape}" type=${symbol_escape}"text/css${symbol_escape}" href=${symbol_escape}"plug-in/accordion/css/accordion.css${symbol_escape}"></link>");
	    	ls_include.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/easyui/jquery.easyui.min.1.3.2.js${symbol_escape}"></script>");
	    	ls_include.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/easyui/locale/easyui-lang-zh_CN.js${symbol_escape}"></script>");
	    	ls_include.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/tools/syUtil.js${symbol_escape}"></script>");
	    	ls_include.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/My97DatePicker/WdatePicker.js${symbol_escape}"></script>");
	    	ls_include.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/lhgDialog/lhgdialog.min.js${symbol_escape}"></script>");
	    	ls_include.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/tools/curdtools.js${symbol_escape}"></script>");
	    	ls_include.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/tools/easyuiextend.js${symbol_escape}"></script>");
	    	ls_include.append("<link rel=${symbol_escape}"stylesheet${symbol_escape}" href=${symbol_escape}"plug-in/Validform/css/style.css${symbol_escape}" type=${symbol_escape}"text/css${symbol_escape}"/>");
	    	ls_include.append("<link rel=${symbol_escape}"stylesheet${symbol_escape}" href=${symbol_escape}"plug-in/Validform/css/tablefrom.css${symbol_escape}" type=${symbol_escape}"text/css${symbol_escape}"/>");
	    	ls_include.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/Validform/js/Validform_v5.3.1_min.js${symbol_escape}"></script>");
	    	ls_include.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/Validform/js/Validform_Datatype.js${symbol_escape}"></script>");
	    	ls_include.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/Validform/js/datatype.js${symbol_escape}"></script>");
	    	ls_include.append("<script type=${symbol_escape}"text/javascript${symbol_escape}" src=${symbol_escape}"plug-in/Validform/plugin/passwordStrength/passwordStrength-min.js${symbol_escape}"></script>");
	    	ls_include.append("<script type=${symbol_escape}"text/javascript${symbol_escape}">${symbol_dollar}(function(){${symbol_dollar}(${symbol_escape}"${symbol_pound}formobj${symbol_escape}").Validform({tiptype:4,btnSubmit:${symbol_escape}"${symbol_pound}btn_sub${symbol_escape}",btnReset:${symbol_escape}"${symbol_pound}btn_reset${symbol_escape}",ajaxPost:true,usePlugin:{passwordstrength:{minLen:6,maxLen:18,trigger:function(obj,error){if(error){obj.parent().next().find(${symbol_escape}".Validform_checktip${symbol_escape}").show();obj.find(${symbol_escape}".passwordStrength${symbol_escape}").hide();}else{${symbol_dollar}(${symbol_escape}".passwordStrength${symbol_escape}").show();obj.parent().next().find(${symbol_escape}".Validform_checktip${symbol_escape}").hide();}}}},callback:function(data){var win = frameElement.api.opener;if(data.success==true){frameElement.api.close();win.tip(data.msg);}else{if(data.responseText==''||data.responseText==undefined)${symbol_dollar}(${symbol_escape}"${symbol_pound}formobj${symbol_escape}").html(data.msg);else ${symbol_dollar}(${symbol_escape}"${symbol_pound}formobj${symbol_escape}").html(data.responseText); return false;}win.reloadTable();}});});</script>");
	    	ls_include.append("<style>");
	    	ls_include.append("body{font-size:12px;}");
	    	ls_include.append("table{border: 1px solid ${symbol_pound}000000;padding:0; margin:0 auto;border-collapse: collapse;width:100%;align:right;}");
	    	ls_include.append("td {border: 1px solid ${symbol_pound}000000;background: ${symbol_pound}fff;font-size:12px;padding: 3px 3px 3px 8px;color: ${symbol_pound}000000;word-break: keep-all;}");
	    	ls_include.append("</style>${symbol_escape}r${symbol_escape}n<body");
			pattern = Pattern.compile("<body",Pattern.CASE_INSENSITIVE);   
			matcher = pattern.matcher(htmlStr);   
			htmlStr = matcher.replaceAll(ls_include.toString().replace("${symbol_dollar}", "${symbol_escape}${symbol_escape}${symbol_dollar}"));
			
/*			pattern = Pattern.compile("<p[^>]+>",Pattern.CASE_INSENSITIVE);   
			matcher = pattern.matcher(htmlStr);   
			htmlStr = matcher.replaceAll("");    
			
			pattern = Pattern.compile("<${symbol_escape}${symbol_escape}/p>",Pattern.CASE_INSENSITIVE);   
			matcher = pattern.matcher(htmlStr);   
			htmlStr = matcher.replaceAll("");    
*/			
			
			//添加<form>语句 
			String ls_form = "<form action=${symbol_escape}"cgFormBuildController.do?saveOrUpdate${symbol_escape}" id=${symbol_escape}"formobj${symbol_escape}" name=${symbol_escape}"formobj${symbol_escape}" method=${symbol_escape}"post${symbol_escape}">${symbol_escape}r${symbol_escape}n" +
							 "<input type=${symbol_escape}"hidden${symbol_escape}" name=${symbol_escape}"tableName${symbol_escape}" value=${symbol_escape}"${symbol_escape}${symbol_escape}${symbol_dollar}{tableName?if_exists?html}${symbol_escape}"${symbol_escape}${symbol_escape}/>${symbol_escape}r${symbol_escape}n" +
							 "<input type=${symbol_escape}"hidden${symbol_escape}" name=${symbol_escape}"id${symbol_escape}" value=${symbol_escape}"${symbol_escape}${symbol_escape}${symbol_dollar}{id?if_exists?html}${symbol_escape}"${symbol_escape}${symbol_escape}/>${symbol_escape}r${symbol_escape}n" +
							 "<input type=${symbol_escape}"hidden${symbol_escape}" id=${symbol_escape}"btn_sub${symbol_escape}" class=${symbol_escape}"btn_sub${symbol_escape}"${symbol_escape}${symbol_escape}/>${symbol_escape}r${symbol_escape}n<table";
			pattern = Pattern.compile("<table",Pattern.CASE_INSENSITIVE);   
			matcher = pattern.matcher(htmlStr);   
			htmlStr = matcher.replaceAll(ls_form);    
			
			//添加</form>语句
			pattern = Pattern.compile("</table>",Pattern.CASE_INSENSITIVE);   
			matcher = pattern.matcher(htmlStr);   
			htmlStr = matcher.replaceAll("</table>${symbol_escape}r${symbol_escape}n</form>");    
			
			/*
			//添加input语句
			pattern = Pattern.compile(regEx_attr6,Pattern.CASE_INSENSITIVE);   
			matcher = pattern.matcher(htmlStr);   

			StringBuffer sb = new StringBuffer(); 
			String thStr = "";
			String inputStr = "";

			boolean result = matcher.find(); 
			while(result) {
				thStr = matcher.group(1);
//				inputStr = "<input type=${symbol_escape}"text${symbol_escape}" name=${symbol_escape}""+thStr+"${symbol_escape}" value=${symbol_escape}"${symbol_escape}${symbol_escape}${symbol_dollar}{"+thStr+"?if_exists?html}${symbol_escape}"${symbol_escape}${symbol_escape}/>";
				inputStr = "";
				if(fieldMap.get(thStr)!=null){
					inputStr = FormHtmlUtil.getFormHTML(fieldMap.get(thStr));
					inputStr +="<span class=${symbol_escape}"Validform_checktip${symbol_escape}">&nbsp;</span>";
				}
				matcher.appendReplacement(sb, inputStr); 
				result = matcher.find(); 
			} 
			matcher.appendTail(sb); 
			htmlStr = sb.toString(); 
			*/
			
		}catch(Exception e) {   
        	LogUtil.error(e.getMessage());
		}
		return htmlStr;
	}
	
	
	public static void main(String[] args) {
		try{
		OfficeHtmlUtil wordtohtml = new OfficeHtmlUtil();
//		wordtohtml.wordToHtml("D://jeecg//qjd.doc", "D://jeecg//test.html");
		wordtohtml.WordConverterHtml("D://jeecg//qjd.doc", "D://jeecg//test.html");
		String htmlStr = wordtohtml.getInfo("D://jeecg//test.html");
		htmlStr = wordtohtml.doPoiHtml(htmlStr);
		wordtohtml.stringToFile(htmlStr,"D://jeecg//tt.html");
	}catch (IOException e4) {}
		
	}
	
	

}
