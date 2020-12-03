package com.example.demo.domain.entity.share;

import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "share")
public class Share {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    private Integer title;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Integer updateTime;

    @Column(name = "is_original")
    private Byte isOriginal;

    private String author;

    private String cover;

    private String summary;

    private Integer price;

    @Column(name = "download_url")
    private String downloadUrl;

    @Column(name = "buy_count")
    private Integer buyCount;

    @Column(name = "show_flag")
    private Byte showFlag;

    @Column(name = "audit_status")
    private String auditStatus;

    private String reason;
}