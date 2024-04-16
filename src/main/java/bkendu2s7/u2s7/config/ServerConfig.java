package bkendu2s7.u2s7.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ServerConfig {

    @Bean
    public Cloudinary cloudinaryUploader(@Value("${cloudinary.name}") String name,
                                         @Value("${cloudinary.key}") String key,
                                         @Value("${cloudinary.secret}") String secret){
        Map<String, String> configuration = new HashMap<>();
        configuration.put("cloud_name",name);
        configuration.put("api_key",key);
        configuration.put("api_secret",secret);
        return new Cloudinary(configuration);
    }
}
