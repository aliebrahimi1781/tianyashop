#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package weixin.idea.photo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ${package}.core.common.service.impl.CommonServiceImpl;
import ${package}.core.util.ContextHolderUtils;
import ${package}.core.util.FileUtils;

import weixin.idea.photo.entity.WeixinPhotoAlbumEntity;
import weixin.idea.photo.entity.WeixinPhotoEntity;
import weixin.idea.photo.service.WeixinPhotoAlbumServiceI;



@Service("weixinPhotoAlbumService")
@Transactional
public class WeixinPhotoAlbumServiceImpl extends CommonServiceImpl implements WeixinPhotoAlbumServiceI {

	public void deleteFile(WeixinPhotoEntity file) {
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

	@Override
	public void deleteFiles(WeixinPhotoAlbumEntity weixinPhotoAlbum) {
		List<WeixinPhotoEntity> photos = weixinPhotoAlbum.getPhotos();
		String sql = "select * from t_s_attachment where id = ?";
		for (WeixinPhotoEntity photo : photos) {
			Map<String, Object> attachmentMap = commonDao.findOneForJdbc(sql, photo.getId());
			//附件相对路径
			String realpath = (String) attachmentMap.get("realpath");
			String fileName = FileUtils.getFilePrefix2(realpath);
			
			//获取部署项目绝对地址
			String realPath = ContextHolderUtils.getSession().getServletContext().getRealPath("/");
			FileUtils.delete(realPath+realpath);
			FileUtils.delete(realPath+fileName+".pdf");
			FileUtils.delete(realPath+fileName+".swf");
		}
		
		
		String dsql = "delete from weixin_photo where photo_album_id = '" + weixinPhotoAlbum.getId() + "'";
		commonDao.updateBySqlString(dsql);
	}
	
}