#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package weixin.guanjia.message.service.impl;

import java.io.Serializable;
import java.util.UUID;

import ${package}.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import weixin.guanjia.message.entity.AutoResponse;
import weixin.guanjia.message.service.AutoResponseServiceI;

@Service("autoResponseService")
@Transactional
public class AutoResponseServiceImpl extends CommonServiceImpl implements AutoResponseServiceI {

	@Override
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((AutoResponse)entity);
 	}
 	@Override
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((AutoResponse)entity);
 		return t;
 	}
 	@Override
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((AutoResponse)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(AutoResponse t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(AutoResponse t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(AutoResponse t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
// 	public String replaceVal(String sql,AutoResponse t){
// 		sql  = sql.replace("${symbol_pound}{id}",String.valueOf(t.getId()));
// 		sql  = sql.replace("${symbol_pound}{accountname}",String.valueOf(t.getAccountname()));
// 		sql  = sql.replace("${symbol_pound}{accounttoken}",String.valueOf(t.getAccounttoken()));
// 		sql  = sql.replace("${symbol_pound}{accountnumber}",String.valueOf(t.getAccountnumber()));
// 		sql  = sql.replace("${symbol_pound}{accounttype}",String.valueOf(t.getAccounttype()));
// 		sql  = sql.replace("${symbol_pound}{accountemail}",String.valueOf(t.getAccountemail()));
// 		sql  = sql.replace("${symbol_pound}{accountdesc}",String.valueOf(t.getAccountdesc()));
// 		sql  = sql.replace("${symbol_pound}{accountappid}",String.valueOf(t.getAccountappid()));
// 		sql  = sql.replace("${symbol_pound}{accountappsecret}",String.valueOf(t.getAccountappsecret()));
// 		sql  = sql.replace("${symbol_pound}{accountaccesstoken}",String.valueOf(t.getAccountaccesstoken()));
// 		sql  = sql.replace("${symbol_pound}{UUID}",UUID.randomUUID().toString());
// 		return sql;
// 	}
}