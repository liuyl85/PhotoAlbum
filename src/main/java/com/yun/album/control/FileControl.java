package com.yun.album.control;

import com.yun.album.bean.ResultData;
import com.yun.album.bean.User;
import com.yun.album.common.Constant;
import com.yun.album.common.StatusCode;
import com.yun.album.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/file")
public class FileControl {
    private final Logger logger = LoggerFactory.getLogger(FileControl.class);
    @Resource
    private FileService fileService;

    @PostMapping(value = "/create_folder", params = {"parentId", "name"})
    public ResultData createFolder(@RequestAttribute(Constant.REQUEST_ATTRIBUTE_NAME_USER) User user, long parentId, String name) {
        try {
            if(StringUtils.isEmpty(name) || !name.matches("^\\w{1,10}$")){
                return new ResultData(StatusCode.PARAMS_IS_WRONGFUL);
            }

            boolean createResult = fileService.createFolder(user.getId(), parentId, name);
            return createResult ? new ResultData(StatusCode.SUCCESS) : new ResultData(StatusCode.ERROR);
        } catch (Exception e) {
            logger.error("create folder error.", e);
            return new ResultData(StatusCode.ERROR);
        }
    }

}
