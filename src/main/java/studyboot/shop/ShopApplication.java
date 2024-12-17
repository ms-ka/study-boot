package studyboot.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//W podanym kodzie utworzono podstawową konfigurację aplikacji Spring Boot

//Klasa, która uruchamia aplikację Spring Boot.
//Adnotacja, która jest skrótem dla trzech innych adnotacji

@SpringBootApplication
public class ShopApplication {

    public static void main(String[] args) {
        //Metoda SpringApplication.run uruchamia aplikację Spring Boot:
        SpringApplication.run(ShopApplication.class, args);
    }
}
