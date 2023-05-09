package com.chachao.mall.product.service.impl;

import com.chachao.mall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chachao.common.utils.PageUtils;
import com.chachao.common.utils.Query;

import com.chachao.mall.product.dao.CategoryDao;
import com.chachao.mall.product.entity.CategoryEntity;
import com.chachao.mall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

//    @Autowired
//    CategoryDao categoryDao;

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {

        List<CategoryEntity> all = baseMapper.selectList(null);
        List<CategoryEntity> root = new ArrayList<>();
        for(CategoryEntity each : all) {
            if(each.getParentCid() == 0) root.add(each);
        }
        root = root.stream().sorted((root1, root2)->{
            return (root1.getSort() == null ? 0 : root1.getSort()) - (root2.getSort() == null ? 0 : root2.getSort());
        }).collect(Collectors.toList());
        for(CategoryEntity cur : root) {
            cur.setChildren(getSubCategory(cur, all));
        }

        return root;
    }

    private List<CategoryEntity> getSubCategory(CategoryEntity par, List<CategoryEntity> all) {
        List<CategoryEntity> res = new ArrayList<>();
        for(CategoryEntity each : all) {
            if(par.getCatId().equals(each.getParentCid())) res.add(each);
        }
        res = res.stream().sorted((root1, root2)->{
            return (root1.getSort() == null ? 0 : root1.getSort())  - (root2.getSort() == null ? 0 : root2.getSort());
        }).collect(Collectors.toList());
        par.setChildren(res);
        for(CategoryEntity each: res) {
            each.setChildren(getSubCategory(each, all));
        }
        return res;
    }


    @Override
    public void removeMenuByIds(List<Long> asList) {
        //TODO  1、检查当前删除的菜单，是否被别的地方引用

        //逻辑删除
        baseMapper.deleteBatchIds(asList);
    }

    //[2,25,225]
    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        List<Long> parentPath = findParentPath(catelogId, paths);

        Collections.reverse(parentPath);


        return parentPath.toArray(new Long[parentPath.size()]);
    }

    /**
     * 级联更新所有关联的数据
     * @param category
     */
    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
    }

    //225,25,2
    private List<Long> findParentPath(Long catelogId,List<Long> paths){
        //1、收集当前节点id
        paths.add(catelogId);
        CategoryEntity byId = this.getById(catelogId);
        if(byId.getParentCid()!=0){
            findParentPath(byId.getParentCid(),paths);
        }
        return paths;

    }




}