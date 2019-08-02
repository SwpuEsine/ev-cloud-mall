package com.ev.cloud.db.service;


import com.ev.cloud.db.domain.LitemallGoodsProduct;
import com.ev.cloud.db.domain.LitemallGoodsProductExample;
import com.ev.cloud.db.mapper.GoodsProductMapper;
import com.ev.cloud.db.mapper.LitemallGoodsProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class LitemallGoodsProductService {
    @Resource
    private LitemallGoodsProductMapper litemallGoodsProductMapper;
    @Resource
    private GoodsProductMapper goodsProductMapper;

    public List<LitemallGoodsProduct> queryByGid(Integer gid) {
        LitemallGoodsProductExample example = new LitemallGoodsProductExample();
        example.or().andGoodsIdEqualTo(gid).andDeletedEqualTo(false);
        return litemallGoodsProductMapper.selectByExample(example);
    }

    public LitemallGoodsProduct findById(Integer id) {
        return litemallGoodsProductMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        litemallGoodsProductMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(LitemallGoodsProduct goodsProduct) {
        goodsProduct.setAddTime(LocalDateTime.now());
        goodsProduct.setUpdateTime(LocalDateTime.now());
        litemallGoodsProductMapper.insertSelective(goodsProduct);
    }

    public int count() {
        LitemallGoodsProductExample example = new LitemallGoodsProductExample();
        example.or().andDeletedEqualTo(false);
        return (int) litemallGoodsProductMapper.countByExample(example);
    }

    public void deleteByGid(Integer gid) {
        LitemallGoodsProductExample example = new LitemallGoodsProductExample();
        example.or().andGoodsIdEqualTo(gid);
        litemallGoodsProductMapper.logicalDeleteByExample(example);
    }

    public int addStock(Integer id, Short num){
        return goodsProductMapper.addStock(id, num);
    }

    public int reduceStock(Integer id, Short num){
        return goodsProductMapper.reduceStock(id, num);
    }

    @Transactional(rollbackFor = Exception.class)
    public String preCheckProduct(Integer productId, Integer number, BigDecimal price) {

        LitemallGoodsProduct product = litemallGoodsProductMapper.findByIdWithLock(productId);

        String error=null;
        //取得规格的信息,判断规格库存 库存需要加锁
        if (product == null || number > product.getNumber()) {
            error="库存不足";
            return error;
        }

        //价格不加锁
        if (product.getPrice().compareTo(price)!=0){
            error="商品价格价格发生变化";
            return error;
        }

        return error;
    }
}