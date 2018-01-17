package cn.com.weidai.conference.media;

import cn.com.weidai.conference.entity.Client;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lwz on 2018/01/16 15:08.
 */
public class Cache {

    // roomId
    public static final Map<Integer, ChannelGroup> ROOM_CHANNEL_GROUP = new ConcurrentHashMap<>();
    // uid
    //    public static final Map<Long, ChannelGroup>    USR_CHANNEL_GROUP  = new ConcurrentHashMap<>();

    public static void pushCache(Client client, ChannelHandlerContext ctx) {
        // 房间列表中如果不存在则为该频道,则新增一个频道 ChannelGroup
        if (!ROOM_CHANNEL_GROUP.containsKey(client.getRoomId())) {
            ROOM_CHANNEL_GROUP.put(client.getRoomId(), new DefaultChannelGroup(GlobalEventExecutor.INSTANCE));
        }
        // 确定有房间号,才将客户端加入到频道中
        Cache.ROOM_CHANNEL_GROUP.get(client.getRoomId()).add(ctx.channel());
        //
        //        if (!USR_CHANNEL_GROUP.containsKey(client.getUid())) {
        //            USR_CHANNEL_GROUP.put(client.getUid(), new DefaultChannelGroup(GlobalEventExecutor.INSTANCE));
        //        }
        //        Cache.USR_CHANNEL_GROUP.get(client.getUid()).add(ctx.channel());
    }

    public static void removeCache(Client client, ChannelHandlerContext ctx) {
        if (client != null && client.getRoomId() != null) {
            if (Cache.ROOM_CHANNEL_GROUP.containsKey(client.getRoomId())) {
                Cache.ROOM_CHANNEL_GROUP.get(client.getRoomId()).remove(ctx.channel());
            }

            //            if (Cache.USR_CHANNEL_GROUP.containsKey(client.getUid())) {
            //                Cache.USR_CHANNEL_GROUP.get(client.getUid()).remove(ctx.channel());
            //            }
        }
    }
}
