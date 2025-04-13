package zin.rashidi.terpsichore;

import org.springframework.boot.SpringApplication;

public class TestTerpsichoreApplication {

    public static void main(String[] args) {
        SpringApplication.from(TerpsichoreApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
