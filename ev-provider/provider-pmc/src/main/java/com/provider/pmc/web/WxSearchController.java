package com.provider.pmc.web;


import com.ev.cloud.db.domain.LitemallKeyword;
import com.ev.cloud.db.service.LitemallKeywordService;
import com.ev.cloud.db.service.LitemallSearchHistoryService;
import com.ev.common.base.dto.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品搜索服务
 * <p>
 * 注意：目前搜索功能非常简单，只是基于关键字匹配。
 */
@RestController
@RequestMapping("/wx/goods/search")
@Validated
@Api(tags = "搜索管理")
public class WxSearchController {

    @Autowired
    private LitemallKeywordService keywordsService;
    @Autowired
    private LitemallSearchHistoryService searchHistoryService;

    /**
     * 搜索页面信息
     * <p>
     * 如果用户已登录，则给出用户历史搜索记录；
     * 如果没有登录，则给出空历史搜索记录。
     *
     * @param  用户ID，可选
     * @return 搜索页面信息
     */
    @GetMapping("index")
    @ApiOperation(value = "首页信息",notes = "首页信息")
    public JsonResult index() {
        //取出输入框默认的关键词
        LitemallKeyword defaultKeyword = keywordsService.queryDefault();
        //取出热闹关键词
        List<LitemallKeyword> hotKeywordList = keywordsService.queryHots();


        Map<String, Object> data = new HashMap<String, Object>();
        data.put("defaultKeyword", defaultKeyword);
        //data.put("historyKeywordList", historyList);
        data.put("hotKeywordList", hotKeywordList);
        return JsonResult.ok(data);
    }

    /**
     * 关键字提醒
     * <p>
     * 当用户输入关键字一部分时，可以推荐系统中合适的关键字。
     *
     * @param keyword 关键字
     * @return 合适的关键字
     */
    @GetMapping("helper")
    @ApiOperation(value = "关键字提醒",notes = "关键字提醒")
    public JsonResult helper(@NotEmpty String keyword,
                         @RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "10") Integer limit) {
        List<LitemallKeyword> keywordsList = keywordsService.queryByKeyword(keyword, page, limit);
        String[] keys = new String[keywordsList.size()];
        int index = 0;
        for (LitemallKeyword key : keywordsList) {
            keys[index++] = key.getKeyword();
        }
        return JsonResult.ok(keys);
    }

    /**
     * 清除用户搜索历史
     *
     * @param userId 用户ID
     * @return 清理是否成功
     */
    @PostMapping("clearhistory")
    @ApiOperation(value = "清空搜索历史",notes = "清除用户搜索历史")
    public JsonResult clearhistory(Integer userId) {
        if (userId == null) {
            return JsonResult.unAuthorized("未登陆");
        }
        searchHistoryService.deleteByUid(userId);
        return JsonResult.ok("清空历史");
    }
}
