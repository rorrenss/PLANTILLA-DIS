package es.ufv;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@PWA(name = "Project Base for Vaadin with Spring", shortName = "Project Base")
@Theme("my-theme")
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    //Cmabiar el main view con el diseño que pida en el examen
    //Dentro de model quitar las clases y implementar las que pidan (mismas que el backend)
    //Cambiar nombre de TurismoService con los que pida en el examen
    //Modificar el Dockerfile del front y el docker compose



}
