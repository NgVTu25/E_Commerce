# JWT Authentication & Authorization Guide

## Tổng quan
Hệ thống đã được tích hợp JWT (JSON Web Token) authentication và role-based authorization với 3 roles chính:
- **ROLE_ADMIN**: Quyền quản trị toàn bộ hệ thống
- **ROLE_MANAGER**: Quyền quản lý sản phẩm, đơn hàng, khách hàng
- **ROLE_USER**: Quyền người dùng cơ bản (xem sản phẩm, tạo đơn hàng)

## Cấu hình

### 1. Chạy SQL Script
Chạy file `src/main/resources/db/init-auth.sql` để tạo bảng users, roles và dữ liệu mẫu.

### 2. Cấu hình JWT trong application.properties
```properties
# JWT Configuration
jwt.secret=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
jwt.expiration=86400000
jwt.refresh-expiration=604800000
```

## API Endpoints

### Authentication Endpoints (Public)

#### 1. Đăng ký tài khoản mới
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "user1",
  "password": "password123",
  "email": "user1@example.com",
  "fullName": "Nguyen Van A",
  "roles": ["ROLE_USER"]
}
```

**Response:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "username": "user1",
  "email": "user1@example.com",
  "roles": ["ROLE_USER"]
}
```

#### 2. Đăng nhập
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

**Response:** Giống như đăng ký

#### 3. Refresh Token
```http
POST /api/auth/refresh
Authorization: Bearer {refresh_token}
```

## Sử dụng JWT Token

### Gửi request với JWT
Thêm header `Authorization` vào mọi request cần authentication:

```http
GET /api/product
Authorization: Bearer {access_token}
```

## Phân quyền theo Endpoint

### Public (Không cần authentication)
- `POST /api/auth/register`
- `POST /api/auth/login`
- `POST /api/auth/refresh`
- `GET /api/product/**` - Xem sản phẩm

### USER Role
- `POST /api/order/**` - Tạo đơn hàng
- `GET /api/order/**` - Xem đơn hàng của mình

### MANAGER Role (bao gồm quyền USER)
- `POST /api/category/**` - Tạo danh mục
- `PUT /api/category/**` - Cập nhật danh mục
- `POST /api/product/**` - Tạo sản phẩm
- `PUT /api/product/**` - Cập nhật sản phẩm
- `POST /api/customer/**` - Tạo khách hàng
- `PUT /api/customer/**` - Cập nhật khách hàng
- `/api/supplier/**` - Quản lý nhà cung cấp
- `/api/shipper/**` - Quản lý đơn vị vận chuyển

### ADMIN Role (toàn quyền)
- `DELETE /api/**` - Xóa bất kỳ resource nào
- Tất cả quyền của MANAGER và USER

## Validation Rules

### RegisterRequest
- `username`: 3-50 ký tự, bắt buộc, unique
- `password`: Tối thiểu 6 ký tự, bắt buộc
- `email`: Email hợp lệ, bắt buộc, unique
- `fullName`: Tối đa 100 ký tự
- `roles`: Optional, mặc định là ROLE_USER

### LoginRequest
- `username`: Bắt buộc
- `password`: Bắt buộc

## Testing với Postman/cURL

### 1. Đăng ký user mới
```bash
curl -X POST http://localhost:8888/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "test123",
    "email": "test@example.com",
    "fullName": "Test User"
  }'
```

### 2. Đăng nhập
```bash
curl -X POST http://localhost:8888/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

### 3. Sử dụng token để truy cập protected endpoint
```bash
curl -X GET http://localhost:8888/api/order \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

### 4. Tạo sản phẩm (cần MANAGER hoặc ADMIN role)
```bash
curl -X POST http://localhost:8888/api/product \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "productName": "New Product",
    "unitPrice": 100.00,
    "unitsInStock": 50,
    "categoryId": 1,
    "supplierId": 1
  }'
```

## Error Handling

### 401 Unauthorized
- Token không hợp lệ hoặc đã hết hạn
- Chưa đăng nhập

### 403 Forbidden
- Không có quyền truy cập endpoint này
- Role không đủ quyền

### 400 Bad Request
- Validation error
- Username hoặc email đã tồn tại

## Security Best Practices

1. **Lưu trữ token an toàn**: Không lưu token trong localStorage, nên dùng httpOnly cookies
2. **Refresh token**: Sử dụng refresh token để lấy access token mới khi hết hạn
3. **HTTPS**: Luôn sử dụng HTTPS trong production
4. **Token expiration**: Access token hết hạn sau 24h, refresh token sau 7 ngày
5. **Password encoding**: Mật khẩu được mã hóa bằng BCrypt

## Troubleshooting

### Token hết hạn
Sử dụng refresh token endpoint để lấy access token mới

### 403 Forbidden khi truy cập endpoint
Kiểm tra role của user có đủ quyền không

### Username already exists
Username phải unique, chọn username khác

## Default Admin Account
- **Username**: admin
- **Password**: admin123
- **Email**: admin@example.com
- **Role**: ROLE_ADMIN

**⚠️ Lưu ý**: Đổi mật khẩu admin ngay sau khi khởi tạo hệ thống!
