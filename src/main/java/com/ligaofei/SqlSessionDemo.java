package com.ligaofei;

import com.ligaofei.dao.VideoMapper;
import com.ligaofei.domain.Video;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

            Video video = videoMapper.selectById(44);

            System.out.println(video.toString());

            //通过注解
            List<Video> videoList =  videoMapper.selectList();
            System.out.println("通过注解");
            System.out.println(videoList.toString());

            //通过xml
            List<Video> videoList2 = videoMapper.selectListByXML();

            System.out.println("通过xml");
            System.out.println(videoList2.toString());

            //多参数查询
            List<Video> videoList3 = videoMapper.selectByPointAndTitleLike(8.7,"HTML");
            System.out.println("多参数查询");
            System.out.println(videoList3.toString());


            //新增一条记录
            Video video0 =  new Video();
            video0.setTitle("小滴课堂面试专题900道000");
            video0.setCoverImg("xdclass.net/aaa.png000");
            video0.setPoint(9.41);
            video0.setCreateTime(new Date());
            video0.setPrice(9911);
            video0.setSummary("这个是面试专题概要00");

            int rows = videoMapper.add(video0);

            System.out.println("新增一条记录");
            System.out.println(rows);

            System.out.println(video0.toString());

            //批量插入
            Video video1 =  new Video();
            video1.setTitle("小滴课堂面试专题900道1111");
            video1.setCoverImg("xdclass.net/aaa.png1111");
            video1.setPoint(9.2);
            video1.setCreateTime(new Date());
            video1.setPrice(9922);
            video1.setSummary("这个是面试专题概要1111");

            Video video2 =  new Video();
            video2.setTitle("小滴课堂面试专题900道2222");
            video2.setCoverImg("xdclass.net/aaa.png2222");
            video2.setPoint(9.2);
            video2.setCreateTime(new Date());
            video2.setPrice(9922);
            video2.setSummary("这个是面试专题概要22222");


            List<Video> list = new ArrayList<>();
            list.add(video1);
            list.add(video2);

            int rows2 =  videoMapper.addBatch(list);
            System.out.println("批量插入");
            System.out.println(rows2);
            System.out.println(list.toString());
        }

    }

}
