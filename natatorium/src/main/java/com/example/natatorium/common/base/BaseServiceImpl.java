package com.example.natatorium.common.base;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


public class BaseServiceImpl<M extends BaseIMapper<T>,T>
        extends ServiceImpl<M,T>
        implements BaseIService<T> {
}
