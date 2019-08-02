package com.ev.cloud.web.controller;


import com.ev.cloud.db.domain.LitemallAddress;
import com.ev.common.base.dto.JsonResult;
import com.ev.common.utils.RegexUtil;
import com.ev.common.utils.ResponseUtil;
import com.provider.umc.api.service.WxUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * 用户收货地址服务
 */
@RestController
@RequestMapping("/wx/address")
@Validated
@Api(tags = "地址管理")
public class WxAddressController {


	@Autowired
	private WxUserClient userClient;


	/**
	 * 用户收货地址列表
	 *
	 * @param
	 * @return 收货地址列表
	 */
	@GetMapping(path="list", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "查询地址列表",notes = "查询所有地址")
	@ResponseStatus(HttpStatus.OK)
	public JsonResult list() {
		return userClient.list();
	}

	/**
	 * 收货地址详情
	 *
	 * @param
	 * @param
	 * @return
	 */
	@GetMapping("detail")
	@ApiOperation(value = "查询地址详情",notes = "查询地址详情")
	public JsonResult detail(@NotNull Integer id) {
		return userClient.detail(id);
	}



	@PostMapping("save")
	@ApiOperation(value = "保存地址",notes = "保存地址")
	public JsonResult save(@RequestBody LitemallAddress address) {
		return userClient.save(address);
	}

	/**
	 * 删除收货地址
	 * @param
	 * @param address 用户收货地址，{ id: xxx }
	 * @return 删除操作结果
	 */
	@PostMapping("delete")
	@ApiOperation(value = "删除地址",notes = "删除地址")
	public JsonResult delete(@RequestBody LitemallAddress address) {
		return userClient.delete(address);
	}

}