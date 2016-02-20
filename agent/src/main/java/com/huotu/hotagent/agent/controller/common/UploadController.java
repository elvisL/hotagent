package com.huotu.hotagent.agent.controller.common;

import com.huotu.hotagent.agent.service.StaticResourceService;
import com.huotu.hotagent.common.constant.ApiResult;
import com.huotu.hotagent.common.constant.ResultCodeEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.util.Arrays;
import java.util.UUID;

/**
 * Created by chendeyu on 2016/1/7.
 */
@Controller
public class UploadController {
    private static final Log log = LogFactory.getLog(UploadController.class);

    @Autowired
    private StaticResourceService staticResourceService;



    @RequestMapping("/uploadImg")
    @ResponseBody
    public ApiResult uploadImg(MultipartFile qualify,String qualifyUri) throws Exception {
        //delete img
        if(qualify.getSize()==0) {
            staticResourceService.deleteResource(qualifyUri);
            return ApiResult.resultWith(ResultCodeEnum.SUCCESS,"");
        }
        if (ImageIO.read(qualify.getInputStream()) == null) {
            return ApiResult.resultWith(ResultCodeEnum.NOT_IMG);
        }
        //change img
        if(!StringUtils.isEmpty(qualifyUri)) {
            staticResourceService.deleteResource(qualifyUri);
        }
        String fileName = qualify.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String path = StaticResourceService.AGENT_IMG + UUID.randomUUID().toString() + "." + suffix;
        staticResourceService.uploadResource(path, qualify.getInputStream());
        String fullPath = staticResourceService.getResource(path).toString();
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS,Arrays.asList(path,fullPath).toArray());
    }


}