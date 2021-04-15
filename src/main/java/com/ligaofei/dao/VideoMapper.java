package com.ligaofei.dao;

import com.ligaofei.domain.Video;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface VideoMapper {

    /**
     * 根据视频id查找视频对象
     * @param videoId
     * @return
     */
    Video selectById(@Param("video_id") int videoId);


    /**
     * 查询全部视频列表
     * @return
     */
    List<Video> selectListByXML();


    /**
     * 查询全部视频列表
     * @return
     * 通过注解读取(如果sql简单，没有过多的表关联，则用注解相对简单，mapper.xml中不用写)
     */
    @Select("select * from video")
    List<Video> selectList();

    /**
     * 根据评分和标题模糊查询
     * @param point
     * @param title
     * @return
     */
    List<Video> selectByPointAndTitleLike(@Param("point") double point, @Param("title") String title);

    /**
     * 新增一条视频记录
     * @param video
     * @return
     */
    int add(Video video);

    /**
     * 批量插入
     * @param list
     * @return
     */
    int addBatch(List<Video> list);
}
