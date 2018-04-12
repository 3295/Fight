package com.example.demo;
import com.example.demo.configure.dynamicMultiDataSource.DataSourceConfig;
import com.example.demo.configure.dynamicMultiDataSource.DynamicDataSource;
import com.example.demo.configure.staticMultiDataSource.FirstDataSourceConfig;
import com.example.demo.configure.staticMultiDataSource.SecondDataSourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@Import(value = {FirstDataSourceConfig.class, SecondDataSourceConfig.class})//多数据源，分包实现
@Import(DataSourceConfig.class)//多数据源，基于注解+AOP实现
public class Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		builder.sources(this.getClass());
		return super.configure(builder);
	}

}
