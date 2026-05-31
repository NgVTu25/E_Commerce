import { useEffect, useMemo, useState } from 'react';
import PageHeader from '../../components/PageHeader';
import SearchBar from '../../components/SearchBar';
import { fetchCategories } from '../../api/categories';
import { createProduct, deleteProduct, fetchProducts, updateProduct } from '../../api/products';
import { fetchSuppliers } from '../../api/suppliers';
import { useAuth } from '../../context/AuthContext';
import type { Category, Product, Supplier } from '../../types';
import { isAdmin } from '../../utils/roles';

const emptyProduct: Product = {
  productName: '',
  quantityPerUnit: '',
  unitPrice: 1,
  unitsInStock: 0,
  unitsOnOrder: 0,
  reorderLevel: 0,
  discontinued: 0,
  categoryId: 1,
  supplierId: 1,
};

export default function ManageProductsPage() {
  const { roles } = useAuth();
  const [items, setItems] = useState<Product[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);
  const [suppliers, setSuppliers] = useState<Supplier[]>([]);
  const [search, setSearch] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [form, setForm] = useState<Product | null>(null);
  const [saving, setSaving] = useState(false);

  const load = async () => {
    setLoading(true);
    setError('');
    try {
      const [products, cats, sups] = await Promise.all([
        fetchProducts(),
        fetchCategories(),
        fetchSuppliers(),
      ]);
      setItems(products);
      setCategories(cats);
      setSuppliers(sups);
    } catch (e) {
      setError(e instanceof Error ? e.message : 'Lỗi tải dữ liệu');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    load();
  }, []);

  const filtered = useMemo(() => {
    const q = search.trim().toLowerCase();
    if (!q) return items;
    return items.filter(
      (p) =>
        p.productName.toLowerCase().includes(q) ||
        String(p.productId ?? '').includes(q),
    );
  }, [items, search]);

  const openCreate = () => {
    const firstCat = categories[0]?.categoryId ?? 1;
    const firstSup = suppliers[0]?.supplierId ?? 1;
    setForm({ ...emptyProduct, categoryId: firstCat, supplierId: firstSup });
  };

  const openEdit = (p: Product) => setForm({ ...p });

  const save = async () => {
    if (!form) return;
    setSaving(true);
    setError('');
    try {
      if (form.productId) {
        await updateProduct(form.productId, form);
      } else {
        await createProduct(form);
      }
      setForm(null);
      await load();
    } catch (e) {
      setError(e instanceof Error ? e.message : 'Lưu thất bại');
    } finally {
      setSaving(false);
    }
  };

  const remove = async (id: number) => {
    if (!confirm('Xóa sản phẩm này?')) return;
    try {
      await deleteProduct(id);
      await load();
    } catch (e) {
      setError(e instanceof Error ? e.message : 'Xóa thất bại');
    }
  };

  return (
    <div>
      <PageHeader
        title="Quản lý sản phẩm"
        subtitle="Thêm, sửa và theo dõi tồn kho"
        actions={
          <button type="button" className="btn btn-primary" onClick={openCreate}>
            + Thêm sản phẩm
          </button>
        }
      />

      <SearchBar value={search} onChange={setSearch} placeholder="Tìm theo tên hoặc ID..." />

      {error && <div className="alert alert-error">{error}</div>}
      {loading && <p className="muted">Đang tải...</p>}

      {!loading && (
        <div className="table-wrap">
          <table className="data-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Tên</th>
                <th>Giá</th>
                <th>Tồn</th>
                <th>Danh mục</th>
                <th>NCC</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {filtered.map((p) => (
                <tr key={p.productId}>
                  <td>{p.productId}</td>
                  <td>{p.productName}</td>
                  <td>{Number(p.unitPrice).toLocaleString('vi-VN')} đ</td>
                  <td>{p.unitsInStock}</td>
                  <td>{p.categoryId}</td>
                  <td>{p.supplierId}</td>
                  <td className="table-actions">
                    <button type="button" className="btn btn-sm" onClick={() => openEdit(p)}>
                      Sửa
                    </button>
                    {isAdmin(roles) && p.productId && (
                      <button
                        type="button"
                        className="btn btn-sm btn-danger"
                        onClick={() => remove(p.productId!)}
                      >
                        Xóa
                      </button>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {form && (
        <div className="modal-backdrop" onClick={() => setForm(null)}>
          <div className="modal" onClick={(e) => e.stopPropagation()}>
            <h2>{form.productId ? 'Sửa sản phẩm' : 'Thêm sản phẩm'}</h2>
            <div className="form-grid">
              <label>
                Tên
                <input
                  className="input"
                  value={form.productName}
                  onChange={(e) => setForm({ ...form, productName: e.target.value })}
                />
              </label>
              <label>
                Đơn vị
                <input
                  className="input"
                  value={form.quantityPerUnit ?? ''}
                  onChange={(e) => setForm({ ...form, quantityPerUnit: e.target.value })}
                />
              </label>
              <label>
                Giá
                <input
                  type="number"
                  className="input"
                  value={form.unitPrice}
                  onChange={(e) => setForm({ ...form, unitPrice: Number(e.target.value) })}
                />
              </label>
              <label>
                Tồn kho
                <input
                  type="number"
                  className="input"
                  value={form.unitsInStock}
                  onChange={(e) => setForm({ ...form, unitsInStock: Number(e.target.value) })}
                />
              </label>
              <label>
                Danh mục
                <select
                  className="input"
                  value={form.categoryId}
                  onChange={(e) => setForm({ ...form, categoryId: Number(e.target.value) })}
                >
                  {categories.map((c) => (
                    <option key={c.categoryId} value={c.categoryId}>
                      {c.categoryName}
                    </option>
                  ))}
                </select>
              </label>
              <label>
                Nhà cung cấp
                <select
                  className="input"
                  value={form.supplierId}
                  onChange={(e) => setForm({ ...form, supplierId: Number(e.target.value) })}
                >
                  {suppliers.map((s) => (
                    <option key={s.supplierId} value={s.supplierId}>
                      {s.companyName}
                    </option>
                  ))}
                </select>
              </label>
              <label>
                Ngừng bán (0/1)
                <input
                  type="number"
                  className="input"
                  min={0}
                  max={1}
                  value={form.discontinued ?? 0}
                  onChange={(e) => setForm({ ...form, discontinued: Number(e.target.value) })}
                />
              </label>
            </div>
            <div className="modal-actions">
              <button type="button" className="btn" onClick={() => setForm(null)}>
                Hủy
              </button>
              <button type="button" className="btn btn-primary" disabled={saving} onClick={save}>
                {saving ? 'Đang lưu...' : 'Lưu'}
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
