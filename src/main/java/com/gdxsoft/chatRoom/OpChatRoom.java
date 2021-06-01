/**
 * 
 */
package com.gdxsoft.chatRoom;

import com.gdxsoft.chatRoom.dao.ChatRoom;
import com.gdxsoft.chatRoom.dao.ChatRoomDao;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.USnowflake;

/**
 * @author admin
 *
 */
public class OpChatRoom {

	public static ChatRoom updateRoom(long roomId, RequestValue rv) throws Exception {
		ChatRoom r = new ChatRoom();
		r.startRecordChanged();
		r.initOrUpdateValues(rv);

		r.setChtRomId(roomId);

		ChatRoomDao d = new ChatRoomDao();
		d.updateRecord(r);

		return r;
	}

	public static ChatRoom createRoom(RequestValue rv) throws Exception {
		ChatRoom cr = new ChatRoom();
		cr.startRecordChanged();
		cr.initOrUpdateValues(rv);

		long snowflakeId = USnowflake.nextId();
		cr.setChtRomId(snowflakeId);

		ChatRoomDao d = new ChatRoomDao();

		d.newRecord(cr);

		return cr;
	}

	public static ChatRoom getRoom(long roomId) {
		ChatRoomDao d = new ChatRoomDao();
		return d.getRecord(roomId);
	}
}
