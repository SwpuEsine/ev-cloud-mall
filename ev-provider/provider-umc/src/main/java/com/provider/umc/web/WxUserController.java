package com.provider.umc.web;


import com.ev.cloud.db.domain.LitemallAddress;
import com.ev.cloud.db.domain.LitemallFeedback;
import com.ev.cloud.db.dto.WxLoginInfo;
import com.ev.cloud.db.service.LitemallAddressService;
import com.ev.common.base.dto.JsonResult;
import com.ev.common.utils.RegexUtil;
import com.provider.umc.api.service.WxUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户收货地址服务
 */
@RestController
@RequestMapping("/wx/user")
@Validated
@Api(tags = "地址管理")
public class WxUserController extends BaseController implements WxUserClient {


	@Autowired
	private LitemallAddressService addressService;

	/**
	 * 用户收货地址列表
	 *
	 * @param
	 * @return 收货地址列表
	 */

	@GetMapping("address/list")
	@ApiOperation(value = "查询地址列表",notes = "查询所有地址")
	public JsonResult list() {
		Integer userId = getUserId();
		List<LitemallAddress> addressList = addressService.queryByUid(userId);
		return JsonResult.ok(addressList);
	}

	private String validate(LitemallAddress address) {
		String message="";
		String name = address.getName();
		if (org.springframework.util.StringUtils.isEmpty(name)) {
			message="用户名不能为空";
		}

		// 测试收货手机号码是否正确
		String mobile = address.getTel();
		if (org.springframework.util.StringUtils.isEmpty(mobile)) {
			message="mobile不能为空";
		}
		if (!RegexUtil.isMobileExact(mobile)) {
			message="mobile不合法";
		}

		String areaCode = address.getAreaCode();
		if (org.springframework.util.StringUtils.isEmpty(areaCode)) {
			message="areaCode错误";
		}

		String detailedAddress = address.getAddressDetail();
		if (org.springframework.util.StringUtils.isEmpty(detailedAddress)) {
			message="detailedAddress错误"; }

		Boolean isDefault = address.getIsDefault();
		if (isDefault == null) {
			message="默认不能为空";
		}
		return message;
	}
	/**
	 * 收货地址详情
	 *
	 * @param
	 * @param id     收货地址ID
	 * @return 收货地址详情
	 */
	@GetMapping("address/detail")
	@ApiOperation(value = "查询地址详情",notes = "查询地址详情")
	public JsonResult detail( Integer id) {
		Integer userId = getUserId();
		LitemallAddress address = addressService.query(userId, id);
		if (address == null) {
			return JsonResult.error("参数值不对");
		}
		return JsonResult.ok(address);
	}


	/**
	 * 添加或更新收货地址
	 *
	 * @param address 用户收货地址
	 * @return 添加或更新操作结果
	 */
	@PostMapping("address/save")
	@ApiOperation(value = "保存地址",notes = "保存地址")
	public JsonResult save(@RequestBody LitemallAddress address) {
		Integer userId = getUserId();
		String error = validate(address);
		if (error != null) {
			return JsonResult.error(error);
		}
		if (address.getIsDefault()) {
			// 重置其他收获地址的默认选项
			addressService.resetDefault(userId);
		}

		if (address.getId() == null || address.getId().equals(0)) {
			address.setId(null);
			address.setUserId(userId);
			addressService.add(address);
		} else {
			address.setUserId(userId);
			if (addressService.update(address) == 0) {
				return JsonResult.error("更新失败");
			}
		}
		return JsonResult.ok(address.getId());
	}

	/**
	 * 删除收货地址
	 * @param
	 * @param address 用户收货地址，{ id: xxx }
	 * @return 删除操作结果
	 */
	@PostMapping("address/delete")
	@ApiOperation(value = "删除地址",notes = "删除地址")
	public JsonResult delete(@RequestBody LitemallAddress address) {
		Integer id = address.getId();
		if (id == null) {
			return JsonResult.error("参数有误!");
		}
		addressService.delete(id);
		return JsonResult.ok("删除成功");
	}

	@Override
	public JsonResult loginByWeixin(WxLoginInfo wxLoginInfo) {
		return null;
	}

	@Override
	public JsonResult sessionEnable() {
		return null;
	}

	@Override
	public JsonResult logout() {
		return null;
	}

	@Override
	public JsonResult list(Integer page, Integer limit, String sort, String order) {
		return null;
	}

	@Override
	public JsonResult mylist(Short status, Integer page, Integer limit, String sort, String order) {
		return null;
	}

	@Override
	public JsonResult selectlist(Integer cartId, Integer grouponRulesId) {
		return null;
	}

	@Override
	public JsonResult receive(String body) {
		return null;
	}

	@Override
	public JsonResult exchange(String body) {
		return null;
	}

	@Override
	public JsonResult submit(LitemallFeedback feedback) {
		return null;
	}

}