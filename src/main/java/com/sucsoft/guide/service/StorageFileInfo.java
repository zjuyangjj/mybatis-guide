package com.sucsoft.guide.service;

import com.sucsoft.guide.bean.FileInfo;
import com.sucsoft.guide.bean.Fileform;
import com.sucsoft.jt.acjtutil.JtIdCreateUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Date;

/**
 * @Author YangJJ
 * @Date 2019/9/20 11:20
 * @Description
 */
@Service
public class StorageFileInfo {

    @Autowired
    private MongoTemplate mongoTemplate;

    private FileInfo saveFileInfo(Fileform form, String filePath, String fileStatus) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setDate(new Date());
        fileInfo.setFileName(form.getFileName());
        fileInfo.setFilePath(filePath);
        fileInfo.setFileSize(form.getFileSize());
        fileInfo.setId(JtIdCreateUtil.Companion.generateUUID());
        fileInfo.setMd5(form.getMd5());
        fileInfo.setFileStatus(fileStatus);
        return fileInfo;
    }

    private FileInfo queryFileInfo(String id) {
        String resource = "D:\\workSpace\\easy-ud\\easy-ud-sql\\src\\main\\resource\\mybatis-config.xml";
        FileInfo fileInfo = new FileInfo();
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            try (SqlSession session = sqlSessionFactory.openSession()) {
                fileInfo = session.selectOne("org.mybatis.example.BlogMapper.selectBlog", id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileInfo;
    }

    @Value("${fileInfoFolder:D\\fileInfo\\}")
    private String txtPath = "";

    public void saveFileInfoInText(Fileform form, String filePath, String fileStatus) {
        FileInfo fileInfo = saveFileInfo(form, filePath, fileStatus);
        File file = new File(txtPath + "fileInfo.txt");
        try {
            Writer out = new FileWriter(file);
            out.write(fileInfo.toString());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFileInfoInMongo(Fileform form, String filePath, String fileStatus) {
        mongoTemplate.save(saveFileInfo(form, filePath, fileStatus), "fileInfo");
    }
}
