package com.provider.pmc.web;
import com.ev.cloud.db.domain.LitemallGoods;
import com.ev.cloud.db.domain.LitemallTopic;
import com.ev.cloud.db.service.LitemallGoodsService;
import com.ev.cloud.db.service.LitemallTopicService;
import com.ev.common.base.dto.JsonResult;
import com.ev.common.base.validator.Order;
import com.ev.common.base.validator.Sort;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 专题服务
 */
@RestController
@RequestMapping("/wx/goods/topic")
@ApiOperation(value = "推荐管理",notes = "推荐管理")
@Validated
public class WxTopicController {

    @Autowired
    private LitemallTopicService topicService;
    @Autowired
    private LitemallGoodsService goodsService;

    /**
     * 专题列表
     *
     * @param page 分页页数
     * @param limit 分页大小
     * @return 专题列表
     */
    @GetMapping("list")
    @ApiOperation(value = "推荐列表",notes = "推荐列表")
    public JsonResult list(@RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer limit,
                           @Sort @RequestParam(defaultValue = "add_time") String sort,
                           @Order @RequestParam(defaultValue = "desc") String order) {
        List<LitemallTopic> topicList = topicService.queryList(page, limit, sort, order);
        int total = topicService.queryTotal();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("data", topicList);
        data.put("count", total);
        return JsonResult.ok(data);
    }

    /**
     * 专题详情
     *
     * @param id 专题ID
     * @return 专题详情
     */
    @GetMapping("detail")
    @ApiOperation(value = "推荐详情",notes = "推荐详情")
    public JsonResult detail(@NotNull Integer id) {
        Map<String, Object> data = new HashMap<>();
        LitemallTopic topic = topicService.findById(id);
        data.put("topic", topic);
        List<LitemallGoods> goods = new ArrayList<>();
        for (Integer i : topic.getGoods()) {
            LitemallGoods good = goodsService.findByIdVO(i);
            if (null != good)
                goods.add(good);
        }
        data.put("goods", goods);
        return JsonResult.ok(data);
    }

    /**
     * 相关专题
     *
     * @param id 专题ID
     * @return 相关专题
     */
    @GetMapping("related")
    @ApiOperation(value = "相关推荐",notes = "相关推荐")
    public JsonResult related(@NotNull Integer id) {
        List<LitemallTopic> topicRelatedList = topicService.queryRelatedList(id, 0, 4);
        return JsonResult.ok(topicRelatedList);
    }
}