#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package weixin.cms.dao;

import java.util.List;
import java.util.Map;

import ${package}.minidao.annotation.Arguments;
import ${package}.minidao.annotation.MiniDao;
import ${package}.minidao.annotation.ResultType;
import ${package}.minidao.hibernate.MiniDaoSupportHiber;

import weixin.cms.entity.AdEntity;

@MiniDao
public interface CmsAdDao extends MiniDaoSupportHiber<AdEntity> {

	@Arguments( { "adEntity"})
	@ResultType("weixin.cms.entity.AdEntity")
	public List<AdEntity> list(AdEntity adEntity);

	@Arguments( { "adEntity", "page", "rows" })
	@ResultType("weixin.cms.entity.AdEntity")
	public List<AdEntity> list(AdEntity adEntity, int page, int rows);

	@Arguments( { "params"})
	@ResultType("weixin.cms.entity.AdEntity")
	public List<AdEntity> listByMap(Map params);

	@Arguments( { "params", "page", "rows" })
	@ResultType("weixin.cms.entity.AdEntity")
	public List<AdEntity> listByMap(Map params, int page, int rows);
	
}
