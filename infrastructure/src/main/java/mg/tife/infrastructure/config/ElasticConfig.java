package mg.tife.infrastructure.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.builder(new HttpHost("elasticsearch", 9200, "http"))
                .build();
    }


}
