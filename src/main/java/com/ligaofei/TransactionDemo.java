package com.ligaofei;


import com.ligaofei.dao.VideoMapper;
import com.ligaofei.domain.Video;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 事务测试类
 * 检查数据库的 引擎 ，改为innodb
 * 事务管理记得改为这个mybatis-config.xml
 * <transactionManager type="JDBC"/>
 *
 * 事务管理形式 MANAGED，设置非自动提交，然后注释 commit, 依旧可以保存成功，事务是无效的
 * 如果不是web程序，然后使用的事务管理形式是MANAGED, 那么将没有事务管理功能，MANAGED依赖WEB程序
 *
 * 不用重点关注，开发项目的事务控制基本是交给Spring，或者使用分布式事务（消息队列，TCC等）
 */
public class TransactionDemo {

    public static void main(String [] args) throws IOException {

        String resouce = "config/mybatis-config.xml";

        //读取配置文件
        InputStream inputStream =  Resources.getResourceAsStream(resouce);

        //构建Session工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //false是非自动提交， true是自动提交事务
        SqlSession sqlSession = sqlSessionFactory.openSession(false);

        //获取Session
        try{

            VideoMapper videoMapper = sqlSession.getMapper(VideoMapper.class);

            Video video1 = new Video();
            video1.setTitle("小滴课堂 微服务架构");
            videoMapper.add(video1);

            int i = 1/0;

            sqlSession.commit();

        }catch (Exception e){
            e.printStackTrace();
            sqlSession.rollback();
        }
        sqlSession.close();
    }

}
