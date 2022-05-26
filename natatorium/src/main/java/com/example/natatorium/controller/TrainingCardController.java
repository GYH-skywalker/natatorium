package com.example.natatorium.controller;

import com.example.natatorium.common.base.BaseController;
import com.example.natatorium.entity.TrainingCard;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @author GuYuHang
* @description 针对表【training_card(培训卡)】的Controller
* @createDate 2022-04-21 16:38:02
*/
@RestController
@RequestMapping("/Trc")
public class TrainingCardController extends BaseController<TrainingCard> {

}