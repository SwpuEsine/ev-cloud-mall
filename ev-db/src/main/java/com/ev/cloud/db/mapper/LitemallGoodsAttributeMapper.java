package com.ev.cloud.db.mapper;

import com.ev.cloud.db.domain.LitemallGoodsAttribute;
import com.ev.cloud.db.domain.LitemallGoodsAttributeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LitemallGoodsAttributeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_attribute
     *
     * @mbg.generated
     */
    long countByExample(LitemallGoodsAttributeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_attribute
     *
     * @mbg.generated
     */
    int deleteByExample(LitemallGoodsAttributeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_attribute
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_attribute
     *
     * @mbg.generated
     */
    int insert(LitemallGoodsAttribute record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_attribute
     *
     * @mbg.generated
     */
    int insertSelective(LitemallGoodsAttribute record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_attribute
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallGoodsAttribute selectOneByExample(LitemallGoodsAttributeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_attribute
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallGoodsAttribute selectOneByExampleSelective(@Param("example") LitemallGoodsAttributeExample example, @Param("selective") LitemallGoodsAttribute.Column... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_attribute
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<LitemallGoodsAttribute> selectByExampleSelective(@Param("example") LitemallGoodsAttributeExample example, @Param("selective") LitemallGoodsAttribute.Column... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_attribute
     *
     * @mbg.generated
     */
    List<LitemallGoodsAttribute> selectByExample(LitemallGoodsAttributeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_attribute
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallGoodsAttribute selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") LitemallGoodsAttribute.Column... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_attribute
     *
     * @mbg.generated
     */
    LitemallGoodsAttribute selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_attribute
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallGoodsAttribute selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_attribute
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") LitemallGoodsAttribute record, @Param("example") LitemallGoodsAttributeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_attribute
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") LitemallGoodsAttribute record, @Param("example") LitemallGoodsAttributeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_attribute
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(LitemallGoodsAttribute record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_attribute
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(LitemallGoodsAttribute record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_attribute
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") LitemallGoodsAttributeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_goods_attribute
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}