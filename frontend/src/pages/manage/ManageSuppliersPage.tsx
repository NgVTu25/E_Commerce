import { useEffect, useMemo, useState } from 'react';
import PageHeader from '../../components/PageHeader';
import SearchBar from '../../components/SearchBar';
import {
  createSupplier,
  deleteSupplier,
  fetchSuppliers,
  updateSupplier,
} from '../../api/suppliers';
import { useAuth } from '../../context/AuthContext';
import type { Supplier } from '../../types';
import { isAdmin } from '../../utils/roles';

const empty: Supplier = {
  companyName: '',
  contactName: '',
  contactTitle: '',
  address: '',
  city: '',
  region: '',
  postalCode: '10000',
  country: 'Vietnam',
  phone: '0123456789',
  fax: '',
  homePage: '',
};

export default function ManageSuppliersPage() {
  const { roles } = useAuth();
  const [items, setItems] = useState<Supplier[]>([]);
  const [search, setSearch] = useState('');
  const [form, setForm] = useState<Supplier | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [saving, setSaving] = useState(false);

  const load = async () => {
    setLoading(true);
    try {
      setItems(await fetchSuppliers());
    } catch (e) {
      setError(e instanceof Error ? e.message : 'Lỗi tải nhà cung cấp');
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
      (s) =>
        s.companyName.toLowerCase().includes(q) ||
        s.contactName.toLowerCase().includes(q) ||
        String(s.supplierId ?? '').includes(q),
    );
  }, [items, search]);

  const save = async () => {
    if (!form) return;
    setSaving(true);
    setError('');
    try {
      if (form.supplierId) {
        await updateSupplier(form.supplierId, form);
      } else {
        await createSupplier(form);
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
    if (!confirm('Xóa nhà cung cấp này?')) return;
    try {
      await deleteSupplier(id);
      await load();
    } catch (e) {
      setError(e instanceof Error ? e.message : 'Xóa thất bại');
    }
  };

  const fields: [keyof Supplier, string][] = [
    ['companyName', 'Tên công ty'],
    ['contactName', 'Người liên hệ'],
    ['contactTitle', 'Chức danh'],
    ['address', 'Địa chỉ'],
    ['city', 'Thành phố'],
    ['region', 'Vùng'],
    ['postalCode', 'Mã bưu điện'],
    ['country', 'Quốc gia'],
    ['phone', 'Điện thoại'],
    ['fax', 'Fax'],
    ['homePage', 'Website'],
  ];

  return (
    <div>
      <PageHeader
        title="Nhà cung cấp"
        subtitle="Quản lý supplier (supply)"
        actions={
          <button type="button" className="btn btn-primary" onClick={() => setForm({ ...empty })}>
            + Thêm
          </button>
        }
      />
      <SearchBar value={search} onChange={setSearch} />
      {error && <div className="alert alert-error">{error}</div>}
      {loading ? (
        <p className="muted">Đang tải...</p>
      ) : (
        <div className="table-wrap">
          <table className="data-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Công ty</th>
                <th>Liên hệ</th>
                <th>Thành phố</th>
                <th>Quốc gia</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {filtered.map((s) => (
                <tr key={s.supplierId}>
                  <td>{s.supplierId}</td>
                  <td>{s.companyName}</td>
                  <td>{s.contactName}</td>
                  <td>{s.city}</td>
                  <td>{s.country}</td>
                  <td className="table-actions">
                    <button type="button" className="btn btn-sm" onClick={() => setForm({ ...s })}>
                      Sửa
                    </button>
                    {isAdmin(roles) && s.supplierId && (
                      <button
                        type="button"
                        className="btn btn-sm btn-danger"
                        onClick={() => remove(s.supplierId!)}
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
          <div className="modal modal-wide" onClick={(e) => e.stopPropagation()}>
            <h2>{form.supplierId ? 'Sửa nhà cung cấp' : 'Thêm nhà cung cấp'}</h2>
            <div className="form-grid">
              {fields.map(([key, label]) => (
                <label key={key}>
                  {label}
                  <input
                    className="input"
                    value={form[key] ?? ''}
                    onChange={(e) => setForm({ ...form, [key]: e.target.value })}
                  />
                </label>
              ))}
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
