package com.xyqq.service.impl;

import com.xyqq.model.Version;
import com.xyqq.dao.VersionMapper;
import com.xyqq.service.IVersionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xyqq
 * @since 2024-11-08
 */
@Service
public class VersionServiceImpl extends ServiceImpl<VersionMapper, Version> implements IVersionService {

}
