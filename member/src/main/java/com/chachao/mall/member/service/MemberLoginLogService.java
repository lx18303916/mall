package com.chachao.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chachao.common.utils.PageUtils;
import com.chachao.mall.member.entity.MemberLoginLogEntity;

import java.util.Map;

/**
 * 会员登录记录
 *
 * @author leifengyang
 * @email leifengyang@gmail.com
 * @date 2019-10-08 09:47:05
 */
public interface MemberLoginLogService extends IService<MemberLoginLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

