#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package weixin.guanjia.core.entity.message.resp;

/**
 * 音乐消息
 * @author Administrator
 *
 */
public class MusicMessageResp extends BaseMessageResp{
	// 音乐
    private Music Music;

    public Music getMusic() {
            return Music;
    }

    public void setMusic(Music music) {
            Music = music;
    }
}
