-- Create users table
CREATE TABLE IF NOT EXISTS users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    full_name VARCHAR(100),
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create roles table
CREATE TABLE IF NOT EXISTS roles (
    role_id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR(255)
);

-- Create user_roles junction table
CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE
);

-- Insert default roles
INSERT INTO roles (name, description) VALUES 
    ('ROLE_ADMIN', 'Administrator role with full access'),
    ('ROLE_MANAGER', 'Manager role with management access'),
        ('ROLE_USER', 'Regular user role with basic access')
ON CONFLICT (name) DO NOTHING;

-- Insert default admin user (password: admin123)
-- Password is BCrypt encoded with strength 10
-- Note: Use UPDATE below if user already exists with wrong password
INSERT INTO users (username, password, email, full_name, enabled) VALUES 
    ('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMye1J8JYJmZa6QwvKYXJQNqXbLhJz0L3/C', 'admin@example.com', 'System Administrator', TRUE)
ON CONFLICT (username) DO UPDATE SET password = '$2a$10$N9qo8uLOickgx2ZMRZoMye1J8JYJmZa6QwvKYXJQNqXbLhJz0L3/C';

-- Assign ROLE_ADMIN to admin user
INSERT INTO user_roles (user_id, role_id)
SELECT u.user_id, r.role_id
FROM users u, roles r
WHERE u.username = 'admin' AND r.name = 'ROLE_ADMIN'
ON CONFLICT DO NOTHING;

COMMENT ON TABLE users IS 'User accounts for authentication';
COMMENT ON TABLE roles IS 'User roles for authorization';
COMMENT ON TABLE user_roles IS 'Many-to-many relationship between users and roles';
