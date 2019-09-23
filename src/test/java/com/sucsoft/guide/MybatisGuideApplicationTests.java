package com.sucsoft.guide;

import com.sucsoft.guide.bean.FileInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.Reader;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisGuideApplicationTests {

	@Test
	public void userFindByIdTest(){
		//定义读取文件名
		String resources = "mybatis-config.xml";
		//创建流
		Reader reader=null;
		try {
			//读取mybatis-config.xml文件到reader对象中
			reader= Resources.getResourceAsReader(resources);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//初始化mybatis,创建SqlSessionFactory类的实例
		SqlSessionFactory sqlMapper=new SqlSessionFactoryBuilder().build(reader);
		//创建session实例
		SqlSession session=sqlMapper.openSession();
		//传入参数查询，返回结果
		FileInfo fileInfo=session.selectOne("findAll");
		//输出结果
		System.out.println(fileInfo.toString());
		//关闭session
		session.close();
	}

}
