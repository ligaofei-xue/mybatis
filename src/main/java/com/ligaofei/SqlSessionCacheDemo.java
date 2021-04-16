package com.ligaofei;

import com.ligaofei.dao.VideoMapper;
import com.ligaofei.domain.Video;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * 测试二级缓存
 * 实现二级缓存的时候，MyBatis建议返回的POJO是可序列化的， 也就是建议实现Serializable接口
 * 优先查询二级缓存-》查询一级缓存-》数据库
 * 基于PerpetualCache 的 HashMap本地缓存，可自定义存储源，如 Ehcache/Redis等
 * 默认是没有开启二级缓存
 *
 * 需要控制全局mapper里面某个方法不使用缓存，可以配置 useCache="false"
 */
public class SqlSessionCacheDemo {


    public static void main(String [] args) throws IOException {

        String resouce = "config/mybatis-config.xml";

        //读取配置文件
        InputStream inputStream =  Resources.getResourceAsStream(resouce);

        //构建Session工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //获取Session
        try{

            SqlSession sqlSession1 = sqlSessionFactory.openSession();
            VideoMapper videoMapper1 = sqlSession1.getMapper(VideoMapper.class);
            Video video1 = videoMapper1.selectById(44);
            System.out.println(video1.toString());
            sqlSession1.commit();//commit之后就会清空一级缓存

            //测试二级缓存，所以创建多个SqlSession

            //通过打印sql日志可以发现，只查询了一次数据库
            SqlSession sqlSession2 = sqlSessionFactory.openSession();
            VideoMapper videoMapper2 = sqlSession1.getMapper(VideoMapper.class);
            Video video2 = videoMapper2.selectById(44);
            System.out.println(video2.toString());
            sqlSession2.commit();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
