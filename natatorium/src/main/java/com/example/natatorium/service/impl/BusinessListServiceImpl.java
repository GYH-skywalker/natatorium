package com.example.natatorium.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.natatorium.common.base.BaseServiceImpl;
import com.example.natatorium.entity.BusinessList;
import com.example.natatorium.mapper.BusinessListMapper;
import com.example.natatorium.service.IBusinessListService;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【business_list(业务卡列表)】的数据库操作Service实现
* @createDate 2022-04-10 17:53:31
*/
@Service
public class BusinessListServiceImpl
        extends BaseServiceImpl<BusinessListMapper, BusinessList>
        implements IBusinessListService {

}
