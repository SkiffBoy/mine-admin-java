package com.skiffboy.mine.admin.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI().info(
                        new Info()
                                .title("MineAdmin-Java")
                                .version("3.0.0")
                                .description("MineAdmin-Java 是一款基于 SpringBoot 开发的开源管理系统，提供了用户管理、权限管理、系统设置、系统监控等功能。")
                                .termsOfService("https://www.mineadmin.com")
                                .contact(new Contact().name("MineAdmin-Java").url("https://mineadmin.skiffboy.com/about"))
                                .license(new License().name("Apache2.0").url("https://github.com/skiffboy/MineAdmin/blob/master/LICENSE"))
                )
                .addServersItem(new Server().url("http://127.0.0.1:8501").description("本地服务"))
                .addServersItem(new Server().url("https://demo.mineadmin.com").description("演示服务"))
                .externalDocs(new ExternalDocumentation().description("开发文档").url("https://v3.doc.mineadmin.com"))
                .components(new Components()
                        .addSecuritySchemes("Bearer",
                                new io.swagger.v3.oas.models.security.SecurityScheme()
                                        .name("Authorization")
                                        .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT"))
                        .addSecuritySchemes("ApiKey",
                                new io.swagger.v3.oas.models.security.SecurityScheme()
                                        .name("token")
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER))
                );

    }


}
