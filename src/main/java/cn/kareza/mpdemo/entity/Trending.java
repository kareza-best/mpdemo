package cn.kareza.mpdemo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

// 热搜实体类
@Data
public class Trending {

    @TableId(type = IdType.ID_WORKER)               // 唯一标示
    private Long id;
    private Date startDate;                         // 起始日期
    private String entries;                         // 词条
    private int topRanking;                         // 最高排名
    private Date startTime;                         // 起始时间
    private Date endTime;                           // 结束时间
    private String remarks;                         // 备注

    @TableField(fill = FieldFill.INSERT)            // 创建时间
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)     // 更改时间
    private Date updateTime;

    @Version                                        // 版本号
    @TableField(fill = FieldFill.INSERT)
    private Integer version;

    @TableLogic
    private Integer deleted;                        // 逻辑删除 1(true) 0(flase)

}
