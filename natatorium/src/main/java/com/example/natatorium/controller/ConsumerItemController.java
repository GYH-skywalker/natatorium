package com.example.natatorium.controller;

import com.example.natatorium.common.base.BaseController;
import com.example.natatorium.entity.ConsumerItem;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @author GuYuHang
* @description 针对表【consumer_item(消费项目
)】的Controller
* @createDate 2022-04-21 21:30:10
*/
@RestController
@RequestMapping("/ci")
public class ConsumerItemController extends BaseController<ConsumerItem> {

}