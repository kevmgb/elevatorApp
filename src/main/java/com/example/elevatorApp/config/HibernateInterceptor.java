package com.example.elevatorApp.config;

import com.example.elevatorApp.repository.QueryLogRepository;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HibernateInterceptor implements StatementInspector {
    private final QueryLogRepository queryLogRepository;

    @Autowired
    public HibernateInterceptor(QueryLogRepository queryLogRepository) {
        this.queryLogRepository = queryLogRepository;
    }

    @Override
    public String inspect(String sql) {
        // Saving sql queries here results in a recursive call to this method leading to a Stack Overflow error
        // Suggested implementation is to queue generated query logs to an audit service which saves the logs to the database.
        return sql;
    }
}
