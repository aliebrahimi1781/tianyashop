#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package weixin.guanjia.core.entity.message.customer;

public class ImageMessage extends BaseMessage {
	private Image image;

	public void setImage(Image image) {
		this.image = image;
	}

	public Image getImage() {
		return image;
	}

}
