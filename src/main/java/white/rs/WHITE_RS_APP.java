package white.rs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("white.rs.mapper")
public class WHITE_RS_APP {
    public static void main(String[] args) {
        SpringApplication.run(WHITE_RS_APP.class, args);
    }
}