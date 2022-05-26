package com.example.natatorium.controller;

import com.example.natatorium.common.base.BaseController;
import com.example.natatorium.entity.SwipeCardRecords;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @author GuYuHang
* @description 针对表【swipe_card_records(刷卡记录)】的Controller
* @createDate 2022-04-23 21:23:28
*/
@RestController
@RequestMapping("/scr")
public class SwipeCardRecordsController extends BaseController<SwipeCardRecords> {

}