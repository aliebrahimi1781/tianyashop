#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.cgform.common;

import ${package}.core.util.StringUtil;

import ${package}.web.cgform.entity.config.CgFormFieldEntity;
/**   
 * @author 张代浩
 * @date 2013-08-11 09:47:30
 * @version V1.0   
 */
public class FormHtmlUtil {
	
	
	/**
     *根据CgFormFieldEntity表属性配置，返回表单HTML代码
     */
    public static String getFormHTML(CgFormFieldEntity cgFormFieldEntity){
    	String html="";
        if(cgFormFieldEntity.getShowType().equals("text")){
        	html=getTextFormHtml(cgFormFieldEntity);
        }else if(cgFormFieldEntity.getShowType().equals("password")){
        	html=getPwdFormHtml(cgFormFieldEntity);
        }else if(cgFormFieldEntity.getShowType().equals("radio")){
        	html=getRadioFormHtml(cgFormFieldEntity);
        }else if(cgFormFieldEntity.getShowType().equals("checkbox")){
        	html=getCheckboxFormHtml(cgFormFieldEntity);
        }else if(cgFormFieldEntity.getShowType().equals("list")){
        	html=getListFormHtml(cgFormFieldEntity);
        }else if(cgFormFieldEntity.getShowType().equals("date")){
        	html=getDateFormHtml(cgFormFieldEntity);
        }else if(cgFormFieldEntity.getShowType().equals("datetime")){
        	html=getDatetimeFormHtml(cgFormFieldEntity);
        }else if(cgFormFieldEntity.getShowType().equals("file")){
        	html=getFileFormHtml(cgFormFieldEntity);
        }else if(cgFormFieldEntity.getShowType().equals("textarea")){
        	html=getTextAreaFormHtml(cgFormFieldEntity);
        }else if(cgFormFieldEntity.getShowType().equals("popup")){
        	html=getPopupFormHtml(cgFormFieldEntity);
        }
        else {
        	html=getTextFormHtml(cgFormFieldEntity);
        }
        return html;
    }
    /**
     * 返回textarea的表单html
     * @param cgFormFieldEntity
     * @return style="width: 300px" class="inputxt" rows="6"
     */
    private static String getTextAreaFormHtml(
			CgFormFieldEntity cgFormFieldEntity) {
    	StringBuilder html = new StringBuilder("");
    	 html.append("<textarea  style=${symbol_escape}"width: 300px${symbol_escape}" rows=${symbol_escape}"6${symbol_escape}" ");
    	 html.append("id=${symbol_escape}"").append(cgFormFieldEntity.getFieldName()).append("${symbol_escape}" ");
         html.append("name=${symbol_escape}"").append(cgFormFieldEntity.getFieldName()).append("${symbol_escape}" ");
         if("Y".equals(cgFormFieldEntity.getIsNull())){
       	  html.append("ignore=${symbol_escape}"ignore${symbol_escape}" ");
         }
         if(cgFormFieldEntity.getFieldValidType()!=null&&cgFormFieldEntity.getFieldValidType().length()>0){
       	  html.append("datatype=${symbol_escape}"").append(cgFormFieldEntity.getFieldValidType()).append("${symbol_escape}" ");
         }else{
   		  html.append("datatype=${symbol_escape}"*${symbol_escape}" ");
   	  }
         html.append("${symbol_escape}${symbol_escape}>");
         html.append("${symbol_escape}${symbol_escape}${symbol_dollar}{").append(cgFormFieldEntity.getFieldName()).append("?if_exists?html}</textarea> ");
		return html.toString();
	}

	/**
     *返回text类型的表单html
     */
    private static String getTextFormHtml(CgFormFieldEntity cgFormFieldEntity)
    {
      StringBuilder html = new StringBuilder("");
      html.append("<input type=${symbol_escape}"text${symbol_escape}" ");
      html.append("id=${symbol_escape}"").append(cgFormFieldEntity.getFieldName()).append("${symbol_escape}" ");
      html.append("name=${symbol_escape}"").append(cgFormFieldEntity.getFieldName()).append("${symbol_escape}" ");
      if(cgFormFieldEntity.getFieldLength()!=null&&cgFormFieldEntity.getFieldLength()>0){
    	  html.append("style=${symbol_escape}"width:").append(cgFormFieldEntity.getFieldLength()).append("px${symbol_escape}" ");
      }
      html.append("value=${symbol_escape}"${symbol_escape}${symbol_escape}${symbol_dollar}{").append(cgFormFieldEntity.getFieldName()).append("?if_exists?html}${symbol_escape}" ");
      if("Y".equals(cgFormFieldEntity.getIsNull())){
    	  html.append("ignore=${symbol_escape}"ignore${symbol_escape}" ");
      }
      if(cgFormFieldEntity.getFieldValidType()!=null&&cgFormFieldEntity.getFieldValidType().length()>0){
    	  html.append("datatype=${symbol_escape}"").append(cgFormFieldEntity.getFieldValidType()).append("${symbol_escape}" ");
      }else{
    	  if("int".equals(cgFormFieldEntity.getType())){
    		  html.append("datatype=${symbol_escape}"n${symbol_escape}" ");
    	  }else if("double".equals(cgFormFieldEntity.getType())){
    		  html.append("datatype=${symbol_escape}"${symbol_escape}${symbol_escape}/^(-?${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}d+)(${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}.${symbol_escape}${symbol_escape}${symbol_escape}${symbol_escape}d+)?${symbol_escape}${symbol_escape}${symbol_dollar}${symbol_escape}${symbol_escape}/${symbol_escape}" ");
    	  }else{
    		  html.append("datatype=${symbol_escape}"*${symbol_escape}" ");
    	  }
      }
      html.append("${symbol_escape}${symbol_escape}/>");
      return html.toString();
    }
    
    /**
     *返回password类型的表单html
     */
    private static String getPwdFormHtml(CgFormFieldEntity cgFormFieldEntity)
    {
      StringBuilder html = new StringBuilder("");
      html.append("<input type=${symbol_escape}"password${symbol_escape}" ");
      html.append("id=${symbol_escape}"").append(cgFormFieldEntity.getFieldName()).append("${symbol_escape}" ");
      html.append("name=${symbol_escape}"").append(cgFormFieldEntity.getFieldName()).append("${symbol_escape}" ");
      if(cgFormFieldEntity.getFieldLength()!=null&&cgFormFieldEntity.getFieldLength()>0){
    	  html.append("style=${symbol_escape}"width:").append(cgFormFieldEntity.getFieldLength()).append("px${symbol_escape}" ");
      }
      html.append("value=${symbol_escape}"${symbol_escape}${symbol_escape}${symbol_dollar}{").append(cgFormFieldEntity.getFieldName()).append("?if_exists?html}${symbol_escape}" ");
      if("Y".equals(cgFormFieldEntity.getIsNull())){
    	  html.append("ignore=${symbol_escape}"ignore${symbol_escape}" ");
      }
      if(cgFormFieldEntity.getFieldValidType()!=null&&cgFormFieldEntity.getFieldValidType().length()>0){
    	  html.append("datatype=${symbol_escape}"").append(cgFormFieldEntity.getFieldValidType()).append("${symbol_escape}" ");
      }else{
		  html.append("datatype=${symbol_escape}"*${symbol_escape}" ");
	  }
      html.append("${symbol_escape}${symbol_escape}/>");
      return html.toString();
    }
    
    
    /**
     *返回radio类型的表单html  
     */
    private static String getRadioFormHtml(CgFormFieldEntity cgFormFieldEntity)
    {
    	
    	if(StringUtil.isEmpty(cgFormFieldEntity.getDictField())){
      	  return getTextFormHtml(cgFormFieldEntity);
        }else{
  	      StringBuilder html = new StringBuilder("");
  	      html.append("<@DictData name=${symbol_escape}""+cgFormFieldEntity.getDictField()+"${symbol_escape}"");
  	      if(!StringUtil.isEmpty(cgFormFieldEntity.getDictTable())){
  	    	html.append(" tablename=${symbol_escape}""+cgFormFieldEntity.getDictTable()+"${symbol_escape}"");
  	      }
  	      html.append(" var=${symbol_escape}"dictDataList${symbol_escape}">");
  	      html.append("<${symbol_pound}list dictDataList as dictdata>");
  	      html.append(" <input type=${symbol_escape}"radio${symbol_escape}" value=${symbol_escape}"${symbol_escape}${symbol_escape}${symbol_dollar}{dictdata.typecode?if_exists?html}${symbol_escape}" name=${symbol_escape}""+cgFormFieldEntity.getFieldName()+"${symbol_escape}" ");
  	      html.append("<${symbol_pound}if dictdata.typecode=='${symbol_escape}${symbol_escape}${symbol_dollar}{").append(cgFormFieldEntity.getFieldName()).append("?if_exists?html}'>");
  	      html.append(" checked=${symbol_escape}"true${symbol_escape}" ");
  	      html.append("</${symbol_pound}if> ");
  	      html.append(">");
  	      html.append("${symbol_escape}${symbol_escape}${symbol_dollar}{dictdata.typename?if_exists?html}");
  	      html.append("</${symbol_pound}list> ");
  	      html.append("</@DictData> ");
  	      return html.toString();
        }
    }
    
    
    /**
     *返回checkbox类型的表单html ${symbol_dollar}{data['${symbol_dollar}{po.field_name}']?if_exists?html}
     */
    private static String getCheckboxFormHtml(CgFormFieldEntity cgFormFieldEntity)
    {
    	  if(StringUtil.isEmpty(cgFormFieldEntity.getDictField())){
        	  return getTextFormHtml(cgFormFieldEntity);
          }else{
    	      StringBuilder html = new StringBuilder("");
    	      html.append("<${symbol_pound}assign checkboxstr>${symbol_escape}${symbol_escape}${symbol_dollar}{data['").append(cgFormFieldEntity.getFieldName()).append("']?if_exists?html}</${symbol_pound}assign>");
    	      html.append("<${symbol_pound}assign checkboxlist=checkboxstr?split(${symbol_escape}",${symbol_escape}")> ");
    	      html.append("<@DictData name=${symbol_escape}""+cgFormFieldEntity.getDictField()+"${symbol_escape}"");
      	      if(!StringUtil.isEmpty(cgFormFieldEntity.getDictTable())){
      	    	html.append(" tablename=${symbol_escape}""+cgFormFieldEntity.getDictTable()+"${symbol_escape}"");
      	      }
      	      html.append(" var=${symbol_escape}"dictDataList${symbol_escape}">");
    	      html.append("<${symbol_pound}list dictDataList as dictdata>");
    	      html.append(" <input type=${symbol_escape}"checkbox${symbol_escape}" value=${symbol_escape}"${symbol_escape}${symbol_escape}${symbol_dollar}{dictdata.typecode?if_exists?html}${symbol_escape}" name=${symbol_escape}""+cgFormFieldEntity.getFieldName()+"${symbol_escape}" ");
    	      html.append("<${symbol_pound}list checkboxlist as x >");
    	      html.append("<${symbol_pound}if dictdata.typecode=='${symbol_escape}${symbol_escape}${symbol_dollar}{x?if_exists?html}'>");
    	      html.append(" checked=${symbol_escape}"true${symbol_escape}" ");
    	      html.append("</${symbol_pound}if> ");
    	      html.append("</${symbol_pound}list> ");
    	      html.append(">");
    	      html.append("${symbol_escape}${symbol_escape}${symbol_dollar}{dictdata.typename?if_exists?html}");
    	      html.append("</${symbol_pound}list> ");
    	      html.append("</@DictData> ");
    	      return html.toString();
          }
    }
    
    
    /**
     *返回list类型的表单html
     */
    private static String getListFormHtml(CgFormFieldEntity cgFormFieldEntity)
    {
      if(StringUtil.isEmpty(cgFormFieldEntity.getDictField())){
    	  return getTextFormHtml(cgFormFieldEntity);
      }else{
	      StringBuilder html = new StringBuilder("");
	      html.append("<@DictData name=${symbol_escape}""+cgFormFieldEntity.getDictField()+"${symbol_escape}"");
	      if(!StringUtil.isEmpty(cgFormFieldEntity.getDictText())){
  	    	html.append(" text=${symbol_escape}""+cgFormFieldEntity.getDictText()+"${symbol_escape}"");
  	      }
  	      if(!StringUtil.isEmpty(cgFormFieldEntity.getDictTable())){
  	    	html.append(" tablename=${symbol_escape}""+cgFormFieldEntity.getDictTable()+"${symbol_escape}"");
  	      }
  	      html.append(" var=${symbol_escape}"dictDataList${symbol_escape}">");
	      html.append("<select name=${symbol_escape}""+cgFormFieldEntity.getFieldName()+"${symbol_escape}" id=${symbol_escape}""+cgFormFieldEntity.getFieldName()+"${symbol_escape}"> ");
	      html.append("<${symbol_pound}list dictDataList as dictdata>");
	      html.append(" <option value=${symbol_escape}"${symbol_escape}${symbol_escape}${symbol_dollar}{dictdata.typecode?if_exists?html}${symbol_escape}" ");
	      html.append("<${symbol_pound}if dictdata.typecode=='${symbol_escape}${symbol_escape}${symbol_dollar}{").append(cgFormFieldEntity.getFieldName()).append("?if_exists?html}'>");
	      html.append(" selected=${symbol_escape}"selected${symbol_escape}" ");
	      html.append("</${symbol_pound}if> ");
	      html.append(">");
	      html.append("${symbol_escape}${symbol_escape}${symbol_dollar}{dictdata.typename?if_exists?html}");
	      html.append("</option> ");
	      html.append("</${symbol_pound}list> ");
	      html.append("</select>");
	      html.append("</@DictData> ");
	      return html.toString();
      }
    }
    
    
    /**
     *返回date类型的表单html
     */
    private static String getDateFormHtml(CgFormFieldEntity cgFormFieldEntity)
    {
      StringBuilder html = new StringBuilder("");
      html.append("<input type=${symbol_escape}"text${symbol_escape}" ");
      html.append("id=${symbol_escape}"").append(cgFormFieldEntity.getFieldName()).append("${symbol_escape}" ");
      html.append("name=${symbol_escape}"").append(cgFormFieldEntity.getFieldName()).append("${symbol_escape}" ");
      html.append("class=${symbol_escape}"Wdate${symbol_escape}" ");
      html.append("onClick=${symbol_escape}"WdatePicker()${symbol_escape}" ");
      if(cgFormFieldEntity.getFieldLength()!=null&&cgFormFieldEntity.getFieldLength()>0){
    	  html.append("style=${symbol_escape}"width:").append(cgFormFieldEntity.getFieldLength()).append("px${symbol_escape}" ");
      }
      html.append("value=${symbol_escape}"${symbol_escape}${symbol_escape}${symbol_dollar}{").append(cgFormFieldEntity.getFieldName()).append("?if_exists?html}${symbol_escape}" ");
      if("Y".equals(cgFormFieldEntity.getIsNull())){
    	  html.append("ignore=${symbol_escape}"ignore${symbol_escape}" ");
      }
      if(cgFormFieldEntity.getFieldValidType()!=null&&cgFormFieldEntity.getFieldValidType().length()>0){
    	  html.append("datatype=${symbol_escape}"").append(cgFormFieldEntity.getFieldValidType()).append("${symbol_escape}" ");
      }else{
		  html.append("datatype=${symbol_escape}"*${symbol_escape}" ");
	  }
      html.append("${symbol_escape}${symbol_escape}/>");
      return html.toString();
    }
    
    /**
     *返回datetime类型的表单html
     */
    private static String getDatetimeFormHtml(CgFormFieldEntity cgFormFieldEntity)
    {
      StringBuilder html = new StringBuilder("");
      html.append("<input type=${symbol_escape}"text${symbol_escape}" ");
      html.append("id=${symbol_escape}"").append(cgFormFieldEntity.getFieldName()).append("${symbol_escape}" ");
      html.append("name=${symbol_escape}"").append(cgFormFieldEntity.getFieldName()).append("${symbol_escape}" ");
      html.append("class=${symbol_escape}"Wdate${symbol_escape}" ");
      html.append("onClick=${symbol_escape}"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})${symbol_escape}" ");
      if(cgFormFieldEntity.getFieldLength()!=null&&cgFormFieldEntity.getFieldLength()>0){
    	  html.append("style=${symbol_escape}"width:").append(cgFormFieldEntity.getFieldLength()).append("px${symbol_escape}" ");
      }
      html.append("value=${symbol_escape}"${symbol_escape}${symbol_escape}${symbol_dollar}{").append(cgFormFieldEntity.getFieldName()).append("?if_exists?html}${symbol_escape}" ");
      if("Y".equals(cgFormFieldEntity.getIsNull())){
    	  html.append("ignore=${symbol_escape}"ignore${symbol_escape}" ");
      }
      if(cgFormFieldEntity.getFieldValidType()!=null&&cgFormFieldEntity.getFieldValidType().length()>0){
    	  html.append("datatype=${symbol_escape}"").append(cgFormFieldEntity.getFieldValidType()).append("${symbol_escape}" ");
      }else{
		  html.append("datatype=${symbol_escape}"*${symbol_escape}" ");
	  }
      html.append("${symbol_escape}${symbol_escape}/>");
      return html.toString();
    }
    
    /**
     *返回file类型的表单html
     */
    private static String getFileFormHtml(CgFormFieldEntity cgFormFieldEntity)
    {
      StringBuilder html = new StringBuilder("");
      html.append("<input type=${symbol_escape}"text${symbol_escape}" ");
      html.append("id=${symbol_escape}"").append(cgFormFieldEntity.getFieldName()).append("${symbol_escape}" ");
      html.append("name=${symbol_escape}"").append(cgFormFieldEntity.getFieldName()).append("${symbol_escape}" ");
      if(cgFormFieldEntity.getFieldLength()!=null&&cgFormFieldEntity.getFieldLength()>0){
    	  html.append("style=${symbol_escape}"width:").append(cgFormFieldEntity.getFieldLength()).append("px${symbol_escape}" ");
      }
      html.append("value=${symbol_escape}"${symbol_escape}${symbol_escape}${symbol_dollar}{").append(cgFormFieldEntity.getFieldName()).append("?if_exists?html}${symbol_escape}" ");
      html.append("${symbol_escape}${symbol_escape}/>");
      return html.toString();
    }
    
    
    /**
     *返回popup类型的表单html
     */
    private static String getPopupFormHtml(CgFormFieldEntity cgFormFieldEntity)
    {
      StringBuilder html = new StringBuilder("");
      html.append("<input type=${symbol_escape}"text${symbol_escape}" readonly=${symbol_escape}"readonly${symbol_escape}" class=${symbol_escape}"searchbox-inputtext${symbol_escape}" ");
      html.append("id=${symbol_escape}"").append(cgFormFieldEntity.getFieldName()).append("${symbol_escape}" ");
      html.append("name=${symbol_escape}"").append(cgFormFieldEntity.getFieldName()).append("${symbol_escape}" ");
      if(cgFormFieldEntity.getFieldLength()!=null&&cgFormFieldEntity.getFieldLength()>0){
    	  html.append("style=${symbol_escape}"width:").append(cgFormFieldEntity.getFieldLength()).append("px${symbol_escape}" ");
      }
      html.append("value=${symbol_escape}"${symbol_escape}${symbol_escape}${symbol_dollar}{").append(cgFormFieldEntity.getFieldName()).append("?if_exists?html}${symbol_escape}" ");
      html.append("onclick=${symbol_escape}"inputClick(this,'"+cgFormFieldEntity.getDictText()+"','"+cgFormFieldEntity.getDictTable()+"');${symbol_escape}" ");
      if("Y".equals(cgFormFieldEntity.getIsNull())){
    	  html.append("ignore=${symbol_escape}"ignore${symbol_escape}" ");
      }
      if(cgFormFieldEntity.getFieldValidType()!=null&&cgFormFieldEntity.getFieldValidType().length()>0){
    	  html.append("datatype=${symbol_escape}"").append(cgFormFieldEntity.getFieldValidType()).append("${symbol_escape}" ");
      }else{
		  html.append("datatype=${symbol_escape}"*${symbol_escape}" ");
	  }
      html.append("${symbol_escape}${symbol_escape}/>");
      return html.toString();
    }
}
