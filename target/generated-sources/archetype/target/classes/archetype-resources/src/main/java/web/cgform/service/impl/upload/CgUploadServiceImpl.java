#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.cgform.service.impl.upload;

import java.util.Map;

import ${package}.core.common.service.impl.CommonServiceImpl;
import ${package}.core.util.ContextHolderUtils;
import ${package}.core.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${package}.web.cgform.dao.upload.CgFormUploadDao;
import ${package}.web.cgform.entity.upload.CgUploadEntity;
import ${package}.web.cgform.service.upload.CgUploadServiceI;
@Service("cgUploadService")
@Transactional
public class CgUploadServiceImpl extends CommonServiceImpl implements CgUploadServiceI {
	@Autowired
	private CgFormUploadDao cgFormUploadDao;
	
	public void deleteFile(CgUploadEntity file) {
		//step.1 删除附件
		String sql = "select * from t_s_attachment where id = ?";
		Map<String, Object> attachmentMap = commonDao.findOneForJdbc(sql, file.getId());
		//附件相对路径
		String realpath = (String) attachmentMap.get("realpath");
		String fileName = FileUtils.getFilePrefix2(realpath);
		
		//获取部署项目绝对地址
		String realPath = ContextHolderUtils.getSession().getServletContext().getRealPath("/");
		FileUtils.delete(realPath+realpath);
		FileUtils.delete(realPath+fileName+".pdf");
		FileUtils.delete(realPath+fileName+".swf");
		//step.2 删除数据
		commonDao.delete(file);
	}

	
	public void writeBack(String cgFormId,String cgFormName,String cgFormField,String fileId,String fileUrl) {
		try{
			cgFormUploadDao.updateBackFileInfo(cgFormId, cgFormName, cgFormField, fileId, fileUrl);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("文件上传失败："+e.getMessage());
		}
	}

}
