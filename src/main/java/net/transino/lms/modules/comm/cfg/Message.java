package net.transino.lms.modules.comm.cfg;

import lombok.Getter;
import lombok.Setter;

/**
 * @author veggieg
 * @since 5.0
 */

@Setter
@Getter
public class Message {
    private String head;
    private String body;
    private boolean eof = false;
}
