package com.finbyte.million;

import com.impossibl.postgres.api.jdbc.PGConnection;
import com.impossibl.postgres.api.jdbc.PGNotificationListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicInteger;


@SpringBootApplication
@Component
public class MillionApplication implements ApplicationRunner {

    AtomicInteger counter = new AtomicInteger();
    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MillionApplication.class);
        app.run(args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        PGNotificationListener listener = new PGNotificationListener() {
            @Override
            public void notification(int processId, String channelName, String payload) {

                System.out.println(counter.getAndIncrement() + " " + payload);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        try (PGConnection connection = (PGConnection) dataSource.getConnection()){
            connection.addNotificationListener(listener);
            Statement statement = connection.createStatement();
            final boolean listen_events = statement.execute("LISTEN events");
            System.out.println("listen statement:" + listen_events);
            statement.close();
            while(true){}

        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
