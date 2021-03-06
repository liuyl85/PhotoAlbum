package com.yun.album.service;

import com.yun.album.bean.PageInfo;
import com.yun.album.bean.Photo;
import com.yun.album.bean.PhotoInfo;

/**
 * 图片操作接口
 */
public interface PhotoService {

    /**
     * 创建文件夹
     * @param userId 用户编号
     * @param parentId 文件夹上级编号
     * @param folderName 文件夹名称
     * @return 操作结果
     */
    boolean createFolder(long userId, long parentId, String folderName);

    /**
     * 添加图片
     * @param userId 用户编号
     * @param folderId 文件夹编号
     * @param photoName 图片名称
     * @param photoPath 图片文件路径
     * @return 操作结果
     */
    boolean addPhotoInfo(long userId, long folderId, String photoName, String photoPath);

    /**
     * 重命名文件
     * @param userId 用户编号
     * @param fileId 文件编号
     * @param fileName 文件夹名称
     * @return 操作结果
     */
    boolean renameFile(long userId, long fileId, String fileName);

    /**
     * 移动文件
     * @param userId 用户编号
     * @param fileId 文件编号
     * @param folderId 文件夹编号
     * @return 操作结果
     */
    boolean moveFile(long userId, long fileId, long folderId);

    /**
     * 删除文件夹
     * @param userId 用户编号
     * @param folderId 文件夹编号
     * @return 操作结果
     */
    boolean deleteFolder(long userId, long folderId);

    /**
     * 删除文件
     * @param userId 用户编号
     * @param fileIds 文件编号
     * @return 操作结果
     */
    boolean deleteFile(long userId, long... fileIds);

    /**
     * 获取图片
     * @param userId 用户编号
     * @param photoId 图片编号
     * @return 结果
     */
    Photo getPhotoById(long userId, long photoId);

    /**
     * 获取文件夹下文件分页
     * @param userId 用户编号
     * @param folderId 文件夹编号
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return 分页对象
     */
    PageInfo<PhotoInfo> getFileByParentId(long userId, long folderId, int pageNum, int pageSize);

}
