#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package weixin.guanjia.core.entity.message.customer;

public class Text {
	private String content;

	public Text() {

	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}
}
