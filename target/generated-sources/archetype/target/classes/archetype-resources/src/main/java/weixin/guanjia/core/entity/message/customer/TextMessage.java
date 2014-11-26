#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package weixin.guanjia.core.entity.message.customer;

public class TextMessage extends BaseMessage {

	private Text text;

	public TextMessage() {
		// TODO Auto-generated constructor stub
	}

	public void setText(Text text) {
		this.text = text;
	}

	public Text getText() {
		return text;
	}

}
