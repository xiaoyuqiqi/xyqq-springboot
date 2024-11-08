package com.xyqq.service.impl;

import com.xyqq.model.Users;
import com.xyqq.dao.UsersMapper;
import com.xyqq.service.IUsersService;
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
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

}
