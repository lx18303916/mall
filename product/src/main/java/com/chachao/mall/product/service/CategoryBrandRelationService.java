package com.chachao.mall.product.service;

import com.chachao.mall.product.entity.BrandEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chachao.common.utils.PageUtils;
import com.chachao.mall.product.entity.CategoryBrandRelationEntity;

import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author leifengyang
 * @email leifengyang@gmail.com
 * @date 2019-11-17 21:25:25
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveDetail(CategoryBrandRelationEntity categoryBrandRelation);

    void updateBrand(Long brandId, String name);

    void updateCategory(Long catId, String name);

    List<BrandEntity> getBrandsByCatId(Long catId);

}

