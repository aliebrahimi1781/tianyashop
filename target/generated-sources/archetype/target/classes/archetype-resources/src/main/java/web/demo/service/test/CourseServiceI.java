#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.demo.service.test;

import ${package}.web.demo.entity.test.CourseEntity;

import ${package}.core.common.service.CommonService;

public interface CourseServiceI extends CommonService{

	/**
	 * 保存课程
	 *@Author JueYue
	 *@date   2013-11-10
	 *@param  course
	 */
	void saveCourse(CourseEntity course);
	/**
	 * 更新课程
	 *@Author JueYue
	 *@date   2013-11-10
	 *@param  course
	 */
	void updateCourse(CourseEntity course);

}
