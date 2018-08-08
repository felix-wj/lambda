package cn.wangjie.lambda.bean;

import lombok.Data;

import java.util.List;

/**
 * @program: lambda
 * @description: 创作音乐的个人团队
 * @author: WangJie
 * @create: 2018-08-07 17:30
 **/
@Data
public class Artist {
    /** 艺术家或乐队名称 */
    private String name;
    /** 乐队成员 */
    private List<String> members;
    /** 乐队来自哪里*/
    private String origin;

    public Artist(String name) {
        this.name = name;
    }
    public Artist(String name,String origin) {
        this.name = name;
        this.origin = origin;
    }
}
