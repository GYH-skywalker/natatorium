package com.example.natatorium.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.natatorium.common.base.BaseServiceImpl;
import com.example.natatorium.entity.basicMemberInfo;
import com.example.natatorium.mapper.basicMemberInfoMapper;
import com.example.natatorium.service.IbasicMemberInfoService;
import org.springframework.stereotype.Service;

@Service
public class basicMemberInfoImpl
        extends BaseServiceImpl<basicMemberInfoMapper,basicMemberInfo>
        implements IbasicMemberInfoService {
}
