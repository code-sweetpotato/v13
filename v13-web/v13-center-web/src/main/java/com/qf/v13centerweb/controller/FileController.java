package com.qf.v13centerweb.controller;



import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qf.v13.pojo.ResultBean;
import com.qf.v13centerweb.polo.WangeditorResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("file")
public class FileController {

    @Autowired
    private FastFileStorageClient client;

    @Value("${image.server}")
    private String imageServer;

    @RequestMapping("upload")
    @ResponseBody
    public ResultBean uploadFile(MultipartFile file)  {
        /*
        * 上传图片要做到事
        * 1.从前端得到文件类型
        * 2.将文件储存到图片储存服务器上
        * 3.将储存路径返回到前端,方便储存对象时,路径储存到数据库中
        *
        * */
        String fileName = file.getOriginalFilename();//得到乐事.jgp
        //返回图片的储存路径,数据库后期展示图片需要路径
        StorePath storePath = null;
        String path =null;
        try {

            storePath = client.uploadFile(file.getInputStream(), file.getSize(), fileName.substring(fileName.indexOf(".") + 1), null);
            path = imageServer+storePath.getFullPath();
            //System.out.println(storePath.getFullPath());
        } catch (IOException e) {
            return new ResultBean("404","上传失败!");
        }

        return new ResultBean("200",path);
    }
    @RequestMapping("mulFilesupload")
    @ResponseBody
    public WangeditorResultBean uploadFiles(MultipartFile[] files){
        String[] data = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            String fileName = file.getOriginalFilename();//乐事.txt
            try {
                StorePath storePath = client.uploadFile(file.getInputStream(), file.getSize(), fileName.substring(fileName.indexOf(".") + 1), null);

                String path = new StringBuffer(imageServer).append(storePath.getFullPath()).toString();

                data[i] = path;
            } catch (IOException e) {
                //出现异常
                return new WangeditorResultBean("1",null);
            }

        }

        return new WangeditorResultBean("0",data);



    }
}
