package com.example.content.controller;

import com.example.annotation.CheckAuthorization;
import com.example.common.domain.dto.content.ShareAuditDTO;
import com.example.content.domain.entity.share.Share;
import com.example.content.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @date 2020/12/10
 * @time 下午7:04
 */
@RestController
@RequestMapping("/admin/shares")
public class ShareAdminController {

    @Autowired
    ShareService shareService;

    @PutMapping("/audit/{id}")
    @CheckAuthorization("admin")
    public Share auditById(@PathVariable Integer id, @RequestBody ShareAuditDTO shareAuditDTO) {
        return shareService.auditShare(id, shareAuditDTO);
    }
}
