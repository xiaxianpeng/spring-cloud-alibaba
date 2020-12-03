package com.example.demo.service;

import com.example.demo.dao.share.ShareMapper;
import com.example.demo.domain.dto.ShareDTO;
import com.example.demo.domain.entity.share.Share;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @date 2020/12/03
 * @time 20:24
 */
@Service
public class ShareService {

    @Autowired
    private ShareMapper shareMapper;

    public ShareDTO findById(Integer id) {
        // 获取分享详情
        Share share = this.shareMapper.selectByPrimaryKey(id);
        // 发布人id
        Integer userId = share.getUserId();
        String wxNickname = "";// todo
        ShareDTO shareDTO = new ShareDTO();
        // 消息的装配
        BeanUtils.copyProperties(share, shareDTO);
        shareDTO.setWxNickname(wxNickname);
        return shareDTO;
    }
}
