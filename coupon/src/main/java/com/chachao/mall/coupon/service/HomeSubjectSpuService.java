package com.chachao.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chachao.common.utils.PageUtils;
import com.chachao.mall.coupon.entity.HomeSubjectSpuEntity;

import java.util.Map;

/**
 * 专题商品
 *
 * @author leifengyang
 * @email leifengyang@gmail.com
 * @date 2019-10-08 09:36:40
 */
public interface HomeSubjectSpuService extends IService<HomeSubjectSpuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

