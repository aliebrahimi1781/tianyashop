#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package weixin.guanjia.message.model;

public class TextMessageKf extends BaseMessage {
	private TextItem text;

	public TextItem getText() {
		return text;
	}
	public void setText(TextItem text) {
		this.text = text;
	}
}
