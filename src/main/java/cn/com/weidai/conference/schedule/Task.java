package cn.com.weidai.conference.schedule;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by lwz on 2018/01/16 17:10.
 */
@Getter
@Setter
public class Task {

    private int     cmd;
    private Integer roomId;

    public Task(int cmd, Integer roomId) {
        this.cmd = cmd;
        this.roomId = roomId;
    }
}
