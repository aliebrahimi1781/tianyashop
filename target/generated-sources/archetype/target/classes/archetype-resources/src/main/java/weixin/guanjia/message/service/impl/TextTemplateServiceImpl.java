#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package weixin.guanjia.message.service.impl;

import java.io.Serializable;

import ${package}.core.common.service.impl.CommonServiceImpl;
import ${package}.core.util.ResourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import weixin.guanjia.account.service.WeixinAccountServiceI;
import weixin.guanjia.message.entity.TextTemplate;
import weixin.guanjia.message.service.TextTemplateServiceI;

@Service("textTemplateService")
@Transactional
public class TextTemplateServiceImpl extends CommonServiceImpl implements TextTemplateServiceI {
	@Autowired
	private WeixinAccountServiceI weixinAccountService;
	@Override
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TextTemplate)entity);
 	}
 	@Override
 	public <T> Serializable save(T entity) {
 		((TextTemplate)entity).setAccountId(ResourceUtil.getWeiXinAccountId());
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TextTemplate)entity);
 		return t;
 	}
 	@Override
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TextTemplate)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TextTemplate t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TextTemplate t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TextTemplate t){
	 	return true;
 	}
 	
}