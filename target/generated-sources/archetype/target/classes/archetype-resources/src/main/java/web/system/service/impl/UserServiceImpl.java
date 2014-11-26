#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.system.service.impl;

import ${package}.web.system.pojo.base.TSRoleUser;
import ${package}.web.system.pojo.base.TSUser;
import ${package}.web.system.service.UserService;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import ${package}.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author  张代浩
 *
 */
@Service("userService")
@Transactional
public class UserServiceImpl extends CommonServiceImpl implements UserService {

	public TSUser checkUserExits(TSUser user){
		return this.commonDao.getUserByUserIdAndUserNameExits(user);
	}
	public String getUserRole(TSUser user){
		return this.commonDao.getUserRole(user);
	}
	
	public void pwdInit(TSUser user,String newPwd) {
			this.commonDao.pwdInit(user,newPwd);
	}
	
	public int getUsersOfThisRole(String id) {
		Criteria criteria = getSession().createCriteria(TSRoleUser.class);
		criteria.add(Restrictions.eq("TSRole.id", id));
		int allCounts = ((Long) criteria.setProjection(
				Projections.rowCount()).uniqueResult()).intValue();
		return allCounts;
	}
	
}
