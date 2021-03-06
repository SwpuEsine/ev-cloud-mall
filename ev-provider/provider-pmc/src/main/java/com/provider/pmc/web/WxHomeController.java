package com.provider.pmc.web;


import com.ev.cloud.db.domain.LitemallCategory;
import com.ev.cloud.db.domain.LitemallGoods;
import com.ev.cloud.db.service.*;
import com.ev.common.base.constant.SystemConfig;
import com.ev.common.base.dto.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 首页服务
 */
@RestController
@RequestMapping("/wx/goods/home")
@Slf4j
@Api(tags = "首页管理")
public class WxHomeController {


    @Autowired
    private LitemallAdService adService;

    @Autowired
    private LitemallGoodsService goodsService;

    @Autowired
    private LitemallBrandService brandService;

    @Autowired
    private LitemallTopicService topicService;

    @Autowired
    private LitemallCategoryService categoryService;

    @Autowired
    private LitemallGrouponRulesService grouponRulesService;

    @Autowired
    private LitemallCouponService couponService;

    private final static ArrayBlockingQueue<Runnable> WORK_QUEUE = new ArrayBlockingQueue<>(9);

    private final static RejectedExecutionHandler HANDLER = new ThreadPoolExecutor.CallerRunsPolicy();

    private static ThreadPoolExecutor executorService = new ThreadPoolExecutor(9, 9, 1000, TimeUnit.MILLISECONDS, WORK_QUEUE, HANDLER);

    @GetMapping("/cache")
    @ApiOperation(value = "缓存管理",notes = "缓存管理")
    public JsonResult cache(@NotNull String key) {
        if (!key.equals("litemall_cache")) {
            return JsonResult.error("获取失败");
        }

        // 清除缓存
        HomeCacheManager.clearAll();
        return JsonResult.ok("缓存已清除");
    }

    /**
     * 首页数据
     * @param  ，非空。为登录状态为null
     * @return 首页数据
     */
    @GetMapping("/index")
    @ApiOperation(value = "首页数据",notes = "首页数据")
    public JsonResult index() {
        //优先从缓存中读取
        if (HomeCacheManager.hasData(HomeCacheManager.INDEX)) {
            return JsonResult.ok(HomeCacheManager.getCacheData(HomeCacheManager.INDEX));
        }
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Map<String, Object> data = new HashMap<>();

        Callable<List> bannerListCallable = () -> adService.queryIndex();

        Callable<List> channelListCallable = () -> categoryService.queryChannelLimit(0, SystemConfig.getChannelLimit());

//        Callable<List> couponListCallable;
//        if(userId == null){
//            couponListCallable = () -> couponService.queryList(0, 3);
//        } else {
//            couponListCallable = () -> couponService.queryAvailableList(userId,0, 3);
//        }


        Callable<List> newGoodsListCallable = () -> goodsService.queryByNew(0, SystemConfig.getNewLimit());

        Callable<List> hotGoodsListCallable = () -> goodsService.queryByHot(0, SystemConfig.getHotLimit());

//        Callable<List> brandListCallable = () -> brandService.queryVO(0, SystemConfig.getBrandLimit());
//
//        Callable<List> topicListCallable = () -> topicService.queryList(0, SystemConfig.getTopicLimit());
//
//        //团购专区
//        Callable<List> grouponListCallable = () -> grouponRulesService.queryList(0, 5);
//
//        Callable<List> floorGoodsListCallable = this::getCategoryList;

        FutureTask<List> bannerTask = new FutureTask<>(bannerListCallable);
        FutureTask<List> channelTask = new FutureTask<>(channelListCallable);
   //     FutureTask<List> couponListTask = new FutureTask<>(couponListCallable);
        FutureTask<List> newGoodsListTask = new FutureTask<>(newGoodsListCallable);
        FutureTask<List> hotGoodsListTask = new FutureTask<>(hotGoodsListCallable);
//        FutureTask<List> brandListTask = new FutureTask<>(brandListCallable);
//        FutureTask<List> topicListTask = new FutureTask<>(topicListCallable);
//        FutureTask<List> grouponListTask = new FutureTask<>(grouponListCallable);
//        FutureTask<List> floorGoodsListTask = new FutureTask<>(floorGoodsListCallable);


//        executorService.submit(couponListTask);

        executorService.submit(newGoodsListTask);
        executorService.submit(hotGoodsListTask);
        executorService.submit(bannerTask);
        executorService.submit(channelTask);

//        executorService.submit(brandListTask);
//        executorService.submit(topicListTask);
//        executorService.submit(grouponListTask);
//        executorService.submit(floorGoodsListTask);

        try {


//            data.put("couponList", couponListTask.get());


            data.put("hotGoodsList", hotGoodsListTask.get());
            data.put("banner", bannerTask.get());
            data.put("channel", channelTask.get());
            data.put("newGoodsList", newGoodsListTask.get());


//            data.put("brandList", brandListTask.get());
//            data.put("topicList", topicListTask.get());
//            data.put("grouponList", grouponListTask.get());
//            data.put("floorGoodsList", floorGoodsListTask.get());
            //缓存数据
            HomeCacheManager.loadData(HomeCacheManager.INDEX, data);
        }
        catch (Exception e) {
            e.printStackTrace();
        }finally {
            executorService.shutdown();
        }
        return JsonResult.ok(data);
    }

    private List<Map> getCategoryList() {
        List<Map> categoryList = new ArrayList<>();
        List<LitemallCategory> catL1List = categoryService.queryL1WithoutRecommend(0, SystemConfig.getCatlogListLimit());
        for (LitemallCategory catL1 : catL1List) {
            List<LitemallCategory> catL2List = categoryService.queryByPid(catL1.getId());
            List<Integer> l2List = new ArrayList<>();
            for (LitemallCategory catL2 : catL2List) {
                l2List.add(catL2.getId());
            }

            List<LitemallGoods> categoryGoods;
            if (l2List.size() == 0) {
                categoryGoods = new ArrayList<>();
            } else {
                categoryGoods = goodsService.queryByCategory(l2List, 0, SystemConfig.getCatlogMoreLimit());
            }

            Map<String, Object> catGoods = new HashMap<>();
            catGoods.put("id", catL1.getId());
            catGoods.put("name", catL1.getName());
            catGoods.put("goodsList", categoryGoods);
            categoryList.add(catGoods);
        }
        return categoryList;
    }
}