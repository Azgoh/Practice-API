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
    '$2a$10$Qq0L6G78LxFsyEzN6KHJje8DppAvlJ6yz5RfnhH7fPqXOT.S6oGQK', -- password: admin123
    'ADMIN',
    true,
    NULL,
    'LOCAL'
);

