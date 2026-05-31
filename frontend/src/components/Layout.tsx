import { Link, NavLink, Outlet } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export function Layout() {
  const { isAuthenticated, username, logout } = useAuth();

  return (
    <div className="app-shell">
      <header className="site-header">
        <Link to="/" className="brand">
          Northwind Shop
        </Link>
        <nav className="main-nav">
          <NavLink to="/" end>
            Sản phẩm
          </NavLink>
          <NavLink to="/categories">Danh mục</NavLink>
        </nav>
        <div className="header-actions">
          {isAuthenticated ? (
            <>
              <span className="user-pill">Xin chào, {username}</span>
              <button type="button" className="btn btn-ghost" onClick={logout}>
                Đăng xuất
              </button>
            </>
          ) : (
            <>
              <Link to="/login" className="btn btn-ghost">
                Đăng nhập
              </Link>
              <Link to="/register" className="btn btn-primary">
                Đăng ký
              </Link>
            </>
          )}
        </div>
      </header>
      <main className="site-main">
        <Outlet />
      </main>
      <footer className="site-footer">
        <p>Northwind E-commerce — Frontend kết nối Spring Boot API</p>
      </footer>
    </div>
  );
}
