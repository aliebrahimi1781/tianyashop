#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package weixin.cms.service.impl;
import weixin.cms.service.WeixinCmsSiteServiceI;
import ${package}.core.common.service.impl.CommonServiceImpl;
import weixin.cms.entity.WeixinCmsSiteEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("weixinCmsSiteService")
@Transactional
public class WeixinCmsSiteServiceImpl extends CommonServiceImpl implements WeixinCmsSiteServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((WeixinCmsSiteEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((WeixinCmsSiteEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((WeixinCmsSiteEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(WeixinCmsSiteEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(WeixinCmsSiteEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(WeixinCmsSiteEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,WeixinCmsSiteEntity t){
 		sql  = sql.replace("${symbol_pound}{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("${symbol_pound}{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("${symbol_pound}{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("${symbol_pound}{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("${symbol_pound}{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("${symbol_pound}{site_name}",String.valueOf(t.getSiteName()));
 		sql  = sql.replace("${symbol_pound}{company_tel}",String.valueOf(t.getCompanyTel()));
 		sql  = sql.replace("${symbol_pound}{site_logo}",String.valueOf(t.getSiteLogo()));
 		sql  = sql.replace("${symbol_pound}{site_template_style}",String.valueOf(t.getSiteTemplateStyle()));
 		sql  = sql.replace("${symbol_pound}{accountid}",String.valueOf(t.getAccountid()));
 		sql  = sql.replace("${symbol_pound}{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}