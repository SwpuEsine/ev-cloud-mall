package com.provider.umc.web;


import com.ev.cloud.db.domain.LitemallFeedback;
import com.ev.cloud.db.domain.LitemallUser;
import com.ev.cloud.db.service.LitemallFeedbackService;
import com.ev.cloud.db.service.LitemallUserService;
import com.ev.common.base.dto.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 意见反馈服务
 *
 * @author Yogeek
 * @date 2018/8/25 14:10
 */
@RestController
@RequestMapping("/wx/user/feedback")
@Validated
public class WxFeedbackController extends BaseController{

    @Autowired
    private LitemallFeedbackService feedbackService;
    @Autowired
    private LitemallUserService userService;

    private String validate(LitemallFeedback feedback) {
        String content = feedback.getContent();
        if (StringUtils.isEmpty(content)) {
            return "内容不能为空";
        }

        String type = feedback.getFeedType();
        if (StringUtils.isEmpty(type)) {
            return "type不能为空";
        }

        Boolean hasPicture = feedback.getHasPicture();
        if (hasPicture == null || !hasPicture) {
            feedback.setPicUrls(new String[0]);
        }

        // 测试手机号码是否正确
        String mobile = feedback.getMobile();
        if (StringUtils.isEmpty(mobile)) {
            return "联系方式不能为空";
        }
        return null;
    }

    /**
     * 添加意见反馈
     *
     * @param
     * @param feedback 意见反馈
     * @return 操作结果
     */
    @PostMapping("submit")
    public JsonResult submit(@RequestBody LitemallFeedback feedback) {
        Integer userId = getUserId();
        if (userId == null) {
            return JsonResult.unAuthorized("未登录");
        }
        String error = validate(feedback);
        if (error != null) {
            return JsonResult.error(error);
        }

        LitemallUser user = userService.findById(userId);
        String username = user.getUsername();
        feedback.setId(null);
        feedback.setUserId(userId);
        feedback.setUsername(username);
        //状态默认是0，1表示状态已发生变化
        feedback.setStatus(1);
        feedbackService.add(feedback);

        return JsonResult.ok("反馈成功");
    }

}
