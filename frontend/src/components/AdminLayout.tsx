import { NavLink, Outlet } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const navItems = [
  { to: '/manage/products', label: 'Sản phẩm' },
  { to: '/manage/customers', label: 'Khách hàng' },
  { to: '/manage/employees', label: 'Nhân viên' },
  { to: '/manage/suppliers', label: 'Nhà cung cấp' },
  { to: '/manage/shippers', label: 'Shipper' },
  { to: '/manage/territories', label: 'Territories' },
];

export default function AdminLayout() {
  const { username } = useAuth();

  return (
    <div className="manage-shell">
      <aside className="manage-sidebar">
        <div className="manage-sidebar-head">
          <strong>Quản lý</strong>
          <span className="muted">{username}</span>
        </div>
        <nav className="manage-nav">
          {navItems.map((item) => (
            <NavLink
              key={item.to}
              to={item.to}
              className={({ isActive }) => (isActive ? 'manage-nav-link active' : 'manage-nav-link')}
            >
              {item.label}
            </NavLink>
          ))}
        </nav>
        <NavLink to="/" className="manage-back">
          ← Về cửa hàng
        </NavLink>
      </aside>
      <main className="manage-main">
        <Outlet />
      </main>
    </div>
  );
}
