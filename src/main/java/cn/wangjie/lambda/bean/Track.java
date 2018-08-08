package cn.wangjie.lambda.bean;

import lombok.Data;

/**
 * @program: lambda
 * @description: 专辑中的一支曲目
 * @author: WangJie
 * @create: 2018-08-07 17:37
 **/
@Data
public class Track {
    /** 曲目名 */
    private String name;
    /** 时长 */
    private Integer time;

    public Track() {}

    public Track(String name, Integer time) {
        this.name = name;
        this.time = time;
    }
}
