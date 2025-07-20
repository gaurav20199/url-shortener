INSERT INTO users (email, password, name, role)
VALUES ('admin@gmail.com', 'admin', 'Administrator', 'ROLE_ADMIN'),
       ('demo@gmail.com', 'secret', 'Demo', 'ROLE_USER');

INSERT INTO short_urls (short_key, original_url, created_by, created_at, expires_at, is_private, click_count)
VALUES ('rs1Aed', 'https://docs.spring.io/spring-framework/reference/data-access/transaction/motivation.html', 1, TIMESTAMP '2025-07-20', NULL, FALSE,
        0),
       ('hujfDf', 'https://docs.spring.io/spring-framework/reference/data-access/dao.html', 1,
        TIMESTAMP '2025-07-20', NULL, FALSE, 0);