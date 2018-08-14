package com.yun.album.service;

import com.yun.album.bean.FileInfo;
import com.yun.album.bean.Folder;
import com.yun.album.bean.PageInfo;

/**
 * 文件操作接口
 */
public interface FileService {

    /**
     * 创建文件夹
     * @param userId 用户编号
     * @param parentId 文件夹上级编号
     * @param folderName 文件夹名称
     * @return 操作结果
     */
    boolean createFolder(long userId, long parentId, String folderName);

    /**
     * 添加文件
     * @param userId 用户编号
     * @param folderId 文件夹编号
     * @param file 文件对象
     * @return 操作结果
     */
    boolean addFileInfo(long userId, long folderId, FileInfo file);

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
     * 获取文件夹
     * @param userId 用户编号
     * @param folderId 文件夹编号
     * @return 结果
     */
    Folder getFolderById(long userId, long folderId);

    /**
     * 获取文件
     * @param userId 用户编号
     * @param fileId 文件编号
     * @return 结果
     */
    FileInfo getFileById(long userId, long fileId);

    /**
     * 获取文件夹下文件分页
     * @param userId 用户编号
     * @param folderId 文件夹编号
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return 分页对象
     */
    PageInfo<FileInfo> getFileByParentId(long userId, long folderId, int pageNum, int pageSize);

}
