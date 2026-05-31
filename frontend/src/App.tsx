import { Navigate, Route, Routes } from 'react-router-dom';
import Layout from './components/Layout';
import AdminLayout from './components/AdminLayout';
import ProtectedRoute from './components/ProtectedRoute';
import HomePage from './pages/HomePage';
import ProductDetailPage from './pages/ProductDetailPage';
import CategoriesPage from './pages/CategoriesPage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import ManageProductsPage from './pages/manage/ManageProductsPage';
import ManageCustomersPage from './pages/manage/ManageCustomersPage';
import ManageEmployeesPage from './pages/manage/ManageEmployeesPage';
import ManageSuppliersPage from './pages/manage/ManageSuppliersPage';
import ManageShippersPage from './pages/manage/ManageShippersPage';
import ManageTerritoriesPage from './pages/manage/ManageTerritoriesPage';

export default function App() {
  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route index element={<HomePage />} />
        <Route path="products/:id" element={<ProductDetailPage />} />
        <Route path="categories" element={<CategoriesPage />} />
        <Route path="login" element={<LoginPage />} />
        <Route path="register" element={<RegisterPage />} />
      </Route>

      <Route path="/manage" element={<ProtectedRoute />}>
        <Route element={<AdminLayout />}>
          <Route index element={<Navigate to="products" replace />} />
          <Route path="products" element={<ManageProductsPage />} />
          <Route path="customers" element={<ManageCustomersPage />} />
          <Route path="employees" element={<ManageEmployeesPage />} />
          <Route path="suppliers" element={<ManageSuppliersPage />} />
          <Route path="shippers" element={<ManageShippersPage />} />
          <Route path="territories" element={<ManageTerritoriesPage />} />
        </Route>
      </Route>
    </Routes>
  );
}
