#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package weixin.guanjia.base.service.impl;

import java.io.Serializable;

import ${package}.core.common.service.impl.CommonServiceImpl;
import ${package}.core.util.ResourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import weixin.guanjia.account.service.WeixinAccountServiceI;
import weixin.guanjia.base.entity.Subscribe;
import weixin.guanjia.base.service.SubscribeServiceI;
import weixin.guanjia.message.entity.AutoResponse;

@Service("subscribeService")
@Transactional
public class SubscribeServiceImpl extends CommonServiceImpl implements
		SubscribeServiceI {
	@Autowired
	private WeixinAccountServiceI weixinAccountService;

	@Override
	public <T> void delete(T entity) {
		super.delete(entity);
		// 执行删除操作配置的sql增强
		this.doDelSql((Subscribe) entity);
	}

	@Override
	public <T> Serializable save(T entity) {
		Subscribe subscribe = (Subscribe) entity;
		subscribe.setAccountid(ResourceUtil.getWeiXinAccountId());
		Serializable t = super.save(subscribe);
		// 执行新增操作配置的sql增强
		this.doAddSql((Subscribe) entity);
		return t;
	}

	@Override
	public <T> void saveOrUpdate(T entity) {
		super.saveOrUpdate(entity);
		// 执行更新操作配置的sql增强
		this.doUpdateSql((Subscribe) entity);
	}

	/**
	 * 默认按钮-sql增强-新增操作
	 * 
	 * @param id
	 * @return
	 */
	public boolean doAddSql(Subscribe t) {
		return true;
	}

	/**
	 * 默认按钮-sql增强-更新操作
	 * 
	 * @param id
	 * @return
	 */
	public boolean doUpdateSql(Subscribe t) {
		return true;
	}

	/**
	 * 默认按钮-sql增强-删除操作
	 * 
	 * @param id
	 * @return
	 */
	public boolean doDelSql(Subscribe t) {
		return true;
	}

	/**
	 * 替换sql中的变量
	 * 
	 * @param sql
	 * @return
	 */
	// public String replaceVal(String sql,Subscribe t){
	// sql = sql.replace("${symbol_pound}{id}",String.valueOf(t.getId()));
	// sql = sql.replace("${symbol_pound}{accountname}",String.valueOf(t.getAccountname()));
	// sql = sql.replace("${symbol_pound}{accounttoken}",String.valueOf(t.getAccounttoken()));
	// sql =
	// sql.replace("${symbol_pound}{accountnumber}",String.valueOf(t.getAccountnumber()));
	// sql = sql.replace("${symbol_pound}{accounttype}",String.valueOf(t.getAccounttype()));
	// sql = sql.replace("${symbol_pound}{accountemail}",String.valueOf(t.getAccountemail()));
	// sql = sql.replace("${symbol_pound}{accountdesc}",String.valueOf(t.getAccountdesc()));
	// sql = sql.replace("${symbol_pound}{accountappid}",String.valueOf(t.getAccountappid()));
	// sql =
	// sql.replace("${symbol_pound}{accountappsecret}",String.valueOf(t.getAccountappsecret()));
	// sql =
	// sql.replace("${symbol_pound}{accountaccesstoken}",String.valueOf(t.getAccountaccesstoken()));
	// sql = sql.replace("${symbol_pound}{UUID}",UUID.randomUUID().toString());
	// return sql;
	// }
}