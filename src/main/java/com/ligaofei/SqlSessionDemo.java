package com.ligaofei;

import com.ligaofei.dao.VideoMapper;
import com.ligaofei.dao.VideoOrderMapper;
import com.ligaofei.domain.User;
import com.ligaofei.domain.Video;
import com.ligaofei.domain.VideoOrder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class SqlSessionDemo {


    public static void main(String [] args) throws IOException {

        String resouce = "config/mybatis-config.xml";

        //读取配置文件
        InputStream inputStream =  Resources.getResourceAsStream(resouce);

        //构建Session工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //获取Session
        try(SqlSession sqlSession = sqlSessionFactory.openSession()){

            VideoMapper videoMapper = sqlSession.getMapper(VideoMapper.class);

//            Video video = videoMapper.selectById(44);
//
//            System.out.println(video.toString());
//
//            //通过注解
//            List<Video> videoList =  videoMapper.selectList();
//            System.out.println("通过注解");
//            System.out.println(videoList.toString());
//
//            //通过xml
//            List<Video> videoList2 = videoMapper.selectListByXML();
//
//            System.out.println("通过xml");
//            System.out.println(videoList2.toString());
//
//            //多参数查询
//            List<Video> videoList3 = videoMapper.selectByPointAndTitleLike(8.7,"HTML");
//            System.out.println("多参数查询");
//            System.out.println(videoList3.toString());
//
//
//            //新增一条记录
//            Video video0 =  new Video();
//            video0.setTitle("小滴课堂面试专题900道000");
//            video0.setCoverImg("xdclass.net/aaa.png000");
//            video0.setPoint(9.41);
//            video0.setCreateTime(new Date());
//            video0.setPrice(9911);
//            video0.setSummary("这个是面试专题概要00");
//
//            int rows = videoMapper.add(video0);
//
//            System.out.println("新增一条记录");
//            System.out.println(rows);
//
//            System.out.println(video0.toString());
//
//            //批量插入
//            Video video1 =  new Video();
//            video1.setTitle("小滴课堂面试专题900道1111");
//            video1.setCoverImg("xdclass.net/aaa.png1111");
//            video1.setPoint(9.2);
//            video1.setCreateTime(new Date());
//            video1.setPrice(9922);
//            video1.setSummary("这个是面试专题概要1111");
//
//            Video video2 =  new Video();
//            video2.setTitle("小滴课堂面试专题900道2222");
//            video2.setCoverImg("xdclass.net/aaa.png2222");
//            video2.setPoint(9.2);
//            video2.setCreateTime(new Date());
//            video2.setPrice(9922);
//            video2.setSummary("这个是面试专题概要22222");
//
//
//            List<Video> list = new ArrayList<>();
//            list.add(video1);
//            list.add(video2);
//
//            int rows2 =  videoMapper.addBatch(list);
//            System.out.println("批量插入");
//            System.out.println(rows2);
//            System.out.println(list.toString());
//
//            //更新视频
//            Video video3 =  new Video();
//            video3.setId(66);
//            video3.setTitle("小滴课堂面试专题900道 2021年新版");
//            video3.setCoverImg("xdclass.net/6666.png");
//
//            System.out.println("更新视频");
//            videoMapper.updateVideo(video3);
//
//            //动态选择更新视频
//            Video video4 =  new Video();
//            video4.setId(72);
//            video4.setTitle("小滴课堂面试专题900道 2021年新版");
//            video4.setCoverImg("xdclass.net/6666.png");
//
//            System.out.println("动态选择更新视频");
//            videoMapper.updateVideoSelective(video4);
//
//            //删除操作
//            System.out.println("删除操作");
//            Map<String,Object> map = new HashMap<>();
//            map.put("createTime","2021-01-11 09:33:20");
//            map.put("price",9000);
//            int rows3 = videoMapper.deleteByCreateTimeAndPrice(map);
//            System.out.println(rows3);
//
//            //根据id查询视频
//            System.out.println("根据id查询视频");
//            Video video5 = videoMapper.selectBaseFieldByIdWithResultMap(45);
//            System.out.println(video5.toString());
//
//
//            System.out.println("resultmap association关联查询 查询全部订单，关联用户信息");
//            // resultmap association关联查询
//            VideoOrderMapper videoOrderMapper = sqlSession.getMapper(VideoOrderMapper.class);
//            List<VideoOrder> videoOrderList = videoOrderMapper.queryVideoOrderList();
//
//            System.out.println(videoOrderList.toString());
//
//            System.out.println("resultmap collection测试查询全部用户的全部订单");
//            //resultmap collection测试
//            List<User> userList = videoOrderMapper.queryUserOrder();
//            System.out.println(userList.toString());


            /**
             * 测试Mybatis一级缓存，通过打印sql日志可以发现，只查询了一次数据库
             *
             * 一级缓存的作用域是SQLSession，同一个SqlSession中执行相同的SQL查询(相同的SQL和参数)，第一次会去查询数据库并写在缓存中，第二次会直接从缓存中取
             * 基于PerpetualCache 的 HashMap本地缓存 默认开启一级缓存
             * 失效策略：当执行SQL时候两次查询中间发生了增删改的操作，即insert、update、delete等操作commit后会清空该SQLSession缓存; 比如sqlsession关闭，或者清空等
             */
//            for(int i=0; i<2; i++){
//                Video video6 = videoMapper.selectById(44);
//                System.out.println(video6.toString());
//            }



            // resultmap association关联查询(测试懒加载)
            //dubug模式测试懒加载不准确，可以直接run
            VideoOrderMapper videoOrderMapper = sqlSession.getMapper(VideoOrderMapper.class);
            List<VideoOrder> videoOrderList = videoOrderMapper.queryVideoOrderListLazy();
            System.out.println(videoOrderList.size());

            //6条订单记录，但是只查询3次用户信息，是因为部分用户信息走了一级缓存sqlsession
            for(VideoOrder videoOrder : videoOrderList){
                System.out.println(videoOrder.getVideoTitle());
                //注释掉下面这句则不会查询用户信息
                System.out.println(videoOrder.getUser().getName());
            }
        }

    }

}
