package com.example.natatorium.controller;

import com.example.natatorium.common.base.BaseController;
import com.example.natatorium.entity.OwnerCard;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @author GuYuHang
* @description 针对表【owner_card(业主卡)】的Controller
* @createDate 2022-04-21 16:37:39
*/
@RestController
@RequestMapping("/oc")
public class OwnerCardController extends BaseController<OwnerCard> {

}