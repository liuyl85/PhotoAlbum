package com.yun.album.dao;

import com.yun.album.bean.Folder;
import com.yun.album.bean.Photo;
import com.yun.album.bean.PhotoInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 图片表操作接口
 */
@Component
public interface PhotoDao {

    /**
     * 添加文件夹
     * @param userId 用户编号
     * @param folder 文件夹对象
     */
    void insertFolder(@Param("userId") long userId, @Param("folder") Folder folder);

    /**
     * 添加图片
     * @param userId 用户编号
     * @param photo 图片对象
     */
    void insertPhoto(@Param("userId") long userId, @Param("photo") PhotoInfo photo);

    /**
     * 删除文件夹
     * @param userId 用户编号
     * @param folderId 文件夹编号
     * @return 操作结果
     */
    int deleteFolder(@Param("userId") long userId, @Param("id") long folderId);

    /**
     * 删除图片
     * @param userId 用户编号
     * @param photoIds 图片编号
     * @return 操作结果
     */
    int deletePhoto(@Param("userId") long userId, @Param("ids") long[] photoIds);

    /**
     * 更新文件
     * @param userId 用户编号
     * @param id 文件编号
     * @param parentId 将要更新成的上级文件夹编号
     * @param name 将要更新成的文件名称
     * @param updateTime 更新时间
     * @return 操作结果
     */
    int updateFile(@Param("userId") long userId, @Param("id") long id, @Param("parentId") Long parentId, @Param("name") String name, @Param("updateTime") LocalDateTime updateTime);

    /**
     * 查询文件夹
     * @param userId 用户编号
     * @param id 文件夹编号
     * @return 操作结果
     */
    int selectFolderExist(@Param("userId") long userId, @Param("id") long id);

    /**
     * 查询图片
     * @param userId 用户编号
     * @param id 图片编号
     * @return 操作结果
     */
    Photo selectPhotoById(@Param("userId") long userId, @Param("id") long id);

    /**
     * 查询该文件夹下文件
     * @param userId 用户编号
     * @param parentId 文件夹编号
     * @return 操作结果
     */
    List<PhotoInfo> selectFileByParentId(@Param("userId") long userId, @Param("parentId") long parentId);

    /**
     * 根据父文件夹编号查询子文件
     * @param userId 用户编号
     * @param parentId 父文件夹编号
     * @param childId 子文件编号
     * @return 操作结果
     */
    int selectChildExist(@Param("userId") long userId, @Param("parentId") long parentId, @Param("childId") long childId);

    /**
     * 查询文件夹下子文件数量
     * @param userId 用户编号
     * @param folderId 文件夹编号
     * @return 操作结果
     */
    int selectChildNumber(@Param("userId") long userId, @Param("id") long folderId);
}
