package com.luojiash.test.service;

import com.luojiash.test.dto.Student;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class SqlSessionTest {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Test
    public void testSessionCache() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Student student1 = sqlSession.selectOne("com.luojiash.test.dao.StudentDAO.getStudent", 1);
        Student student2 = sqlSession.selectOne("com.luojiash.test.dao.StudentDAO.getStudent", 1);
        /*Mybatis cacheEnabled 为true（默认即为true），session缓存生效，所以只有第一次查询连接到数据库，第二次直接从缓存取得结果。
        查看DEBUG内容只打印一次SQL。*/
        Assert.assertTrue(student1 == student2);
        sqlSession.close();

        Student student3 = sqlSessionFactory.openSession().selectOne("com.luojiash.test.dao.StudentDAO.getStudent", 1);
        Assert.assertFalse(student1 == student3); // 无session缓存
    }

    @Test
    public void testSecondLevelCache() {
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        sqlSession1.selectOne("com.luojiash.test.dao.StudentDAO.getStudentWithCache", 1);

        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        sqlSession2.selectOne("com.luojiash.test.dao.StudentDAO.getStudentWithCache", 1);

        /* 只有commit或close后才会缓存，所以sqlSession2还会连接数据库，sqlSession3取缓存。注意SQL DEBUG。
        二级缓存Student需要实现Serializable接口。*/
        sqlSession1.commit();
        System.out.println("---");

        SqlSession sqlSession3 = sqlSessionFactory.openSession();
        sqlSession3.selectOne("com.luojiash.test.dao.StudentDAO.getStudentWithCache", 1);

        sqlSession1.close();
        sqlSession2.close();
        sqlSession3.close();
    }
}
