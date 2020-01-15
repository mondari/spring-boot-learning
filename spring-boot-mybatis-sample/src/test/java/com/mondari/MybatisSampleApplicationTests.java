package com.mondari;

import com.mondari.mapper.BlogMapper;
import com.mondari.model.Blog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisSampleApplicationTests {

	@Resource
	BlogMapper blogMapper;

	/**
	 * 复杂结果映射（不用select）
	 */
	@Test
	public void selectBlogDetails() {
		Blog blog = blogMapper.selectBlogDetails("1");
		Assert.isTrue(blog.getAuthor() != null, "author can't be null");
		Assert.isTrue(blog.getPosts().size() == 2, "size is not 2");
	}

	/**
	 * 嵌套 Select 查询
	 */
	@Test
	public void selectBlog() {
		Blog blog = blogMapper.selectBlog("1");
		Assert.isTrue(blog.getAuthor() != null, "author can't be null");
		Assert.isTrue(blog.getPosts().size() == 2, "size is not 2");
	}

}
