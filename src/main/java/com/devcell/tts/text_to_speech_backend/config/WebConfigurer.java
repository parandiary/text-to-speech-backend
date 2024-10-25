package com.devcell.tts.text_to_speech_backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.ServletContext;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
public class WebConfigurer implements ServletContextInitializer, WebServerFactoryCustomizer<WebServerFactory> {

    private static final Logger LOG = LoggerFactory.getLogger(WebConfigurer.class);

    private final Environment env;

    public WebConfigurer(Environment env) {
        this.env = env;
        
    }

    @Override
    public void onStartup(ServletContext servletContext) {
        if (env.getActiveProfiles().length != 0) {
            LOG.info("Web application configuration, using profiles: {}", (Object[]) env.getActiveProfiles());
        }

        LOG.info("Web application fully configured");
    }

    /**
     * Customize the Servlet engine: Mime types, the document root, the cache.
     */
//    @Override
//    public void customize(WebServerFactory server) {
//        // When running in an IDE or with ./mvnw spring-boot:run, set location of the static web assets.
//        setLocationForStaticAssets(server);
//    }
    
    @Override
	public void customize(WebServerFactory factory) {
		// TODO Auto-generated method stub
		
	}

//    private void setLocationForStaticAssets(WebServerFactory server) {
//        if (server instanceof ConfigurableServletWebServerFactory servletWebServer) {
//            File root;
//            String prefixPath = resolvePathPrefix();
//            root = new File(prefixPath + "target/classes/static/");
//            if (root.exists() && root.isDirectory()) {
//                servletWebServer.setDocumentRoot(root);
//            }
//        }
//    }

    /**
     * Resolve path prefix to static resources.
     */
//    private String resolvePathPrefix() {
//        String fullExecutablePath = decode(this.getClass().getResource("").getPath(), StandardCharsets.UTF_8);
//        String rootPath = Paths.get(".").toUri().normalize().getPath();
//        String extractedPath = fullExecutablePath.replace(rootPath, "");
//        int extractionEndIndex = extractedPath.indexOf("target/");
//        if (extractionEndIndex <= 0) {
//            return "";
//        }
//        return extractedPath.substring(0, extractionEndIndex);
//    }
    
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/api/**")  // 특정 경로에 대해 CORS 허용
//                        .allowedOrigins("*")  // 모든 도메인 허용
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                        .allowedHeaders("*")
//                        .allowCredentials(true);
                
                registry.addMapping("/**") // 모든 경로에 대해 허용
//                		.allowedOrigins("*")  // 모든 도메인 허용
		                .allowedOrigins("http://localhost:5173") // React 개발 서버 허용
		                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
		                .allowedHeaders("*")
		                .allowCredentials(true);
            }
        };
    }

	

//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = jHipsterProperties.getCors();
//        if (!CollectionUtils.isEmpty(config.getAllowedOrigins()) || !CollectionUtils.isEmpty(config.getAllowedOriginPatterns())) {
//            LOG.debug("Registering CORS filter");
//            source.registerCorsConfiguration("/api/**", config);
//            source.registerCorsConfiguration("/management/**", config);
//            source.registerCorsConfiguration("/v3/api-docs", config);
//            source.registerCorsConfiguration("/swagger-ui/**", config);
//        }
//        return new CorsFilter(source);
//    }
}