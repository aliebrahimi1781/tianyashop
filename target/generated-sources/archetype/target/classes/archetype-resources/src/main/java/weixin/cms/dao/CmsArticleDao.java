#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package weixin.cms.dao;

import java.util.List;
import java.util.Map;

import ${package}.minidao.annotation.Arguments;
import ${package}.minidao.annotation.MiniDao;
import ${package}.minidao.annotation.ResultType;
import ${package}.minidao.annotation.Sql;
import ${package}.minidao.hibernate.MiniDaoSupportHiber;

import weixin.cms.entity.AdEntity;
import weixin.cms.entity.CmsArticleEntity;

@MiniDao
public interface CmsArticleDao extends MiniDaoSupportHiber<CmsArticleEntity> {

	@Arguments( { "cmsArticleEntity", "page", "rows" })
	@ResultType("weixin.cms.entity.CmsArticleEntity")
	public List<CmsArticleEntity> list(CmsArticleEntity cmsArticleEntity);

	@Arguments( { "cmsArticleEntity", "page", "rows" })
	@ResultType("weixin.cms.entity.CmsArticleEntity")
	public List<CmsArticleEntity> list(CmsArticleEntity cmsArticleEntity,
			int page, int rows);

	@Arguments( { "params" })
	@ResultType("weixin.cms.entity.CmsArticleEntity")
	public List<CmsArticleEntity> listByMap(Map params);

	@Arguments( { "params", "page", "rows" })
	@ResultType("weixin.cms.entity.CmsArticleEntity")
	public List<CmsArticleEntity> listByMap(Map params, int page, int rows);

	@Arguments( { "params" })
	@ResultType("java.lang.Integer")
	public Integer getCount(Map params);

}
