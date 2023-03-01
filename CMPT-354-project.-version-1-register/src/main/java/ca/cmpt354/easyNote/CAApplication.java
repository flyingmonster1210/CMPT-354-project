// Yang Zou  yza497   301385615
package ca.cmpt354.easyNote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

//@ServletComponentScan("ca.cmpt354.easyNote.filter")
@SpringBootApplication
public class CAApplication {
    public static void main(String[] args) {
        SpringApplication.run(CAApplication.class, args);
    }
}