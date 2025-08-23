INSERT INTO users (
    username,
    email,
    password,
    role,
    enabled,
    verification_token,
    auth_provider
)
VALUES (
    'admin',
    'admin@example.com',
    '$2a$12$pCZbFA6MDLy2PL4EaQqih.hvdGM/wZ1Gue/fjhU.B2n24sxuW04ze', -- password: admin123
    'USER',
    true,
    NULL,
    'LOCAL'
);

