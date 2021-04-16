package com.ligaofei.dao;


import com.ligaofei.domain.User;
import com.ligaofei.domain.VideoOrder;

import java.util.List;

public interface VideoOrderMapper {

    /**
     * 查询全部订单，关联用户信息
     * @return
     */
    List<VideoOrder> queryVideoOrderList();

    /**
     * 查询全部用户的全部订单
     * @return
     */
    List<User> queryUserOrder();

    /**
     * 查询全部订单，关联用户信息 懒加载
     * @return
     */
    List<VideoOrder> queryVideoOrderListLazy();

}
