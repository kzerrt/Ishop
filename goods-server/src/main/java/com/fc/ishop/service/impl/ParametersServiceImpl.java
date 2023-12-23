package com.fc.ishop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.Parameters;
import com.fc.ishop.mapper.ParametersMapper;
import com.fc.ishop.service.ParametersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author florence
 * @date 2023/12/13
 */
@Service("parametersService")
@Transactional
public class ParametersServiceImpl
        extends ServiceImpl<ParametersMapper, Parameters> implements ParametersService {
}
