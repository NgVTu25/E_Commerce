import { Navigate, Outlet } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { hasManagerAccess } from '../utils/roles';

export default function ProtectedRoute() {
  const { isAuthenticated, roles } = useAuth();

  if (!isAuthenticated) {
    return <Navigate to="/login" replace state={{ from: '/manage' }} />;
  }

  if (!hasManagerAccess(roles)) {
    return (
      <div className="page">
        <div className="alert alert-error">
          Bạn cần quyền Admin hoặc Manager để truy cập khu vực quản lý. Đăng nhập bằng tài khoản admin.
        </div>
      </div>
    );
  }

  return <Outlet />;
}
