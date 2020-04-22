package com.mondari;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * MQ消息
 * </p>
 *
 * @author limondar
 * @date 2020/4/22
 */
@Accessors(chain = true)
@Data
public class Message implements Serializable {
    String id;
    String content;
}
