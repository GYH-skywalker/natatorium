package com.example.natatorium.service.impl;

import com.example.natatorium.common.base.BaseServiceImpl;
import com.example.natatorium.entity.BusinessControl;
import com.example.natatorium.service.IBusinessControlService;
import com.example.natatorium.mapper.BusinessControlMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
* @author GuYuHang
* @description 针对表【business_control(业务总控)】的数据库操作Service实现
* @createDate 2022-04-20 09:27:44
*/
@Service
@Primary
public class BusinessControlServiceImpl extends BaseServiceImpl<BusinessControlMapper, BusinessControl>
    implements IBusinessControlService{

}




