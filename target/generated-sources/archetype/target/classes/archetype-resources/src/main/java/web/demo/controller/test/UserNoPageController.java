#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.demo.controller.test;

import ${package}.web.system.controller.core.UserController;
import ${package}.web.system.pojo.base.*;
import ${package}.web.system.service.SystemService;
import ${package}.web.system.service.UserService;
import org.apache.log4j.Logger;
import ${package}.core.common.hibernate.qbc.CriteriaQuery;
import ${package}.core.common.model.json.DataGrid;
import ${package}.core.constant.Globals;
import ${package}.tag.core.easyui.TagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName: NoPageController
 * @Description: 对用户datagrid做无分页的例子
 * @author cici
 */
@Controller
@RequestMapping("/userNoPageController")
public class UserNoPageController {
	/**
	 * Logger for this class
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(UserController.class);

	private UserService userService;
	private SystemService systemService;

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
    /**
     * 用户列表页面跳转
     *
     * @return
     */
    @RequestMapping(params = "user")
    public String user(HttpServletRequest request) {
        String departsReplace = "";
        List<TSDepart> departList = systemService.getList(TSDepart.class);
        for (TSDepart depart : departList) {
            if (departsReplace.length() > 0) {
                departsReplace += ",";
            }
            departsReplace += depart.getDepartname() + "_" + depart.getId();
        }
        request.setAttribute("departsReplace", departsReplace);
        return "jeecg/demo/base/nopage/userList";
    }
    @RequestMapping(params = "datagridNoPage")
    public void datagridNoPage(TSUser user,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
        //查询条件组装器
        ${package}.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, user);

        Short[] userstate = new Short[] { Globals.User_Normal, Globals.User_ADMIN };
        cq.in("status", userstate);
        cq.add();
        this.systemService.getDataGridReturn(cq, false);
        TagUtil.datagrid(response, dataGrid);
    }
}