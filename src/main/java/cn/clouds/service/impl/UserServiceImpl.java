package cn.clouds.service.impl;

import cn.clouds.dao.UserDao;
import cn.clouds.dao.UserDaoAnnotation;
import cn.clouds.entity.User;
import cn.clouds.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author clouds
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @NonNull
    private final UserDaoAnnotation userDaoAnnotation;

    @NonNull
    private final UserDao userDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void create(User user) {
        userDaoAnnotation.create(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public List<User> selectAll() {
        return userDao.selectALl();
    }
}
