package org.rafal.featureflags.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseIntegrationTest {

    @Autowired
    private ServletWebServerApplicationContext webServerAppCtx;

    protected FeaturesClient adminClient;

    protected FeaturesClient userClient;

    @BeforeEach
    public void setUp() {
        int port = webServerAppCtx.getWebServer().getPort();

        adminClient = new FeaturesClient("http://localhost:" + port,
                "admin", "admin");

        userClient = new FeaturesClient("http://localhost:" + port,
                "user", "password");
    }

}
