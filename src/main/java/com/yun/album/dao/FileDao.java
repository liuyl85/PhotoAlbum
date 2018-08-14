package com.yun.album.dao;

import com.yun.album.bean.FileInfo;
import com.yun.album.bean.Folder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文件夹表操作接口
 */
@Component
public interface FileDao {

    /**
     * 添加文件夹
     * @param userId 用户编号
     * @param folder 文件夹对象
     */
    void insertFolder(@Param("userId") long userId, @Param("folder") Folder folder);

    /**
     * 添加文件
     * @param userId 用户编号
     * @param file 文件对象
     */
    void insertFile(@Param("userId") long userId, @Param("folder") FileInfo file);

    /**
     * 删除文件夹
     * @param userId 用户编号
     * @param folderId 文件夹编号
     * @return 操作结果
     */
    int deleteFolder(@Param("userId") long userId, @Param("id") long folderId);

    /**
     * 删除文件
     * @param userId 用户编号
     * @param fileIds 文件编号
     * @return 操作结果
     */
    int deleteFile(@Param("userId") long userId, @Param("id") long[] fileIds);

    /**
     * 更新文件
     * @param userId 用户编号
     * @param id 文件编号
     * @param folderId 将要更新到的文件夹编号
     * @param name 将要更新成的文件名称
     * @param updateTime 更新时间
     * @return 操作结果
     */
    int updateFile(@Param("userId") long userId, @Param("id") long id, @Param("folderId") Long folderId, @Param("name") String name, @Param("updateTime") LocalDateTime updateTime);

    /**
     * 查询文件夹
     * @param userId 用户编号
     * @param id 文件夹编号
     * @return 操作结果
     */
    Folder selectFolderById(@Param("userId") long userId, @Param("id") long id);

    /**
     * 查询文件
     * @param userId 用户编号
     * @param id 文件编号
     * @return 操作结果
     */
    FileInfo selectFileById(@Param("userId") long userId, @Param("id") long id);

    /**
     * 查询该文件夹下文件
     * @param userId 用户编号
     * @param folderId 文件夹编号
     * @return 操作结果
     */
    List<Folder> selectFileByParentId(@Param("userId") long userId, @Param("folderId") long folderId);

    /**
     * 根据父文件夹编号查询子文件
     * @param userId 用户编号
     * @param parentId 父文件夹编号
     * @param childId 子文件编号
     * @return 操作结果
     */
    int selectChildFile(@Param("userId") long userId, @Param("parentId") long parentId, @Param("childId") long childId);

    /**
     * 查询该文件夹下子文件数量
     * @param userId 用户编号
     * @param folderId 文件夹编号
     * @return 操作结果
     */
    int selectChildNumber(@Param("userId") long userId, @Param("folderId") long folderId);
}
