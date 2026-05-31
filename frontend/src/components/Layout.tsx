import { Link, NavLink, Outlet } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { hasManagerAccess } from '../utils/roles';

export default function Layout() {
  const { isAuthenticated, username, logout, roles } = useAuth();
  const canManage = hasManagerAccess(roles);

  return (
    <div className="app-shell">
      <header className="site-header">
        <Link to="/" className="brand">
          Northwind Shop
        </Link>
        <nav className="site-nav">
          <NavLink to="/" end>
            Sản phẩm
          </NavLink>
          <NavLink to="/categories">Danh mục</NavLink>
          {canManage && (
            <NavLink to="/manage" className="nav-manage">
              Quản lý
            </NavLink>
          )}
        </nav>
        <div className="site-auth">
          {isAuthenticated ? (
            <>
              <span className="greeting">Xin chào, {username}</span>
              <button type="button" className="btn btn-sm" onClick={logout}>
                Đăng xuất
              </button>
            </>
          ) : (
            <>
              <Link to="/login">Đăng nhập</Link>
              <Link to="/register" className="btn btn-sm btn-primary">
                Đăng ký
              </Link>
            </>
          )}
        </div>
      </header>
      <main className="site-main">
        <Outlet />
      </main>
      <footer className="site-footer">Northwind E-Commerce</footer>
    </div>
  );
}
