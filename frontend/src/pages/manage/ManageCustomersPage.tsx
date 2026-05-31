import { useEffect, useMemo, useState } from 'react';
import PageHeader from '../../components/PageHeader';
import SearchBar from '../../components/SearchBar';
import {
  createCustomer,
  deleteCustomer,
  fetchCustomers,
  updateCustomer,
} from '../../api/customers';
import { useAuth } from '../../context/AuthContext';
import type { Customer } from '../../types';
import { isAdmin } from '../../utils/roles';

const empty: Customer = {
  companyName: '',
  contactTitle: '',
  address: '',
  city: '',
  region: '',
  postalCode: '10000',
  phone: '0123456789',
  fax: '',
};

export default function ManageCustomersPage() {
  const { roles } = useAuth();
  const [items, setItems] = useState<Customer[]>([]);
  const [search, setSearch] = useState('');
  const [selected, setSelected] = useState<Customer | null>(null);
  const [form, setForm] = useState<Customer | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [saving, setSaving] = useState(false);

  const load = async () => {
    setLoading(true);
    try {
      setItems(await fetchCustomers());
    } catch (e) {
      setError(e instanceof Error ? e.message : 'Lỗi tải khách hàng');
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
      (c) =>
        c.companyName.toLowerCase().includes(q) ||
        (c.customerId ?? '').toLowerCase().includes(q) ||
        c.city.toLowerCase().includes(q),
    );
  }, [items, search]);

  const save = async () => {
    if (!form) return;
    setSaving(true);
    setError('');
    try {
      if (form.customerId) {
        await updateCustomer(form.customerId, form);
      } else {
        await createCustomer(form);
      }
      setForm(null);
      setSelected(null);
      await load();
    } catch (e) {
      setError(e instanceof Error ? e.message : 'Lưu thất bại');
    } finally {
      setSaving(false);
    }
  };

  const remove = async (id: string) => {
    if (!confirm('Xóa khách hàng này?')) return;
    try {
      await deleteCustomer(id);
      setSelected(null);
      await load();
    } catch (e) {
      setError(e instanceof Error ? e.message : 'Xóa thất bại');
    }
  };

  return (
    <div className="split-layout">
      <div className="split-main">
        <PageHeader
          title="Khách hàng"
          subtitle="Xem và quản lý danh sách customer"
          actions={
            <button type="button" className="btn btn-primary" onClick={() => setForm({ ...empty })}>
              + Thêm
            </button>
          }
        />
        <SearchBar value={search} onChange={setSearch} placeholder="Tên công ty, ID, thành phố..." />
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
                  <th>Thành phố</th>
                  <th>Điện thoại</th>
                </tr>
              </thead>
              <tbody>
                {filtered.map((c) => (
                  <tr
                    key={c.customerId}
                    className={selected?.customerId === c.customerId ? 'row-selected' : ''}
                    onClick={() => setSelected(c)}
                  >
                    <td>{c.customerId}</td>
                    <td>{c.companyName}</td>
                    <td>{c.city}</td>
                    <td>{c.phone}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>

      <aside className="detail-panel">
        {selected ? (
          <>
            <h3>{selected.companyName}</h3>
            <dl className="detail-list">
              <dt>ID</dt>
              <dd>{selected.customerId}</dd>
              <dt>Liên hệ</dt>
              <dd>{selected.contactTitle || '—'}</dd>
              <dt>Địa chỉ</dt>
              <dd>
                {selected.address}, {selected.city}
                {selected.region ? `, ${selected.region}` : ''}
              </dd>
              <dt>Điện thoại</dt>
              <dd>{selected.phone}</dd>
              <dt>Fax</dt>
              <dd>{selected.fax || '—'}</dd>
            </dl>
            <div className="detail-actions">
              <button type="button" className="btn btn-sm" onClick={() => setForm({ ...selected })}>
                Sửa
              </button>
              {isAdmin(roles) && selected.customerId && (
                <button
                  type="button"
                  className="btn btn-sm btn-danger"
                  onClick={() => remove(selected.customerId!)}
                >
                  Xóa
                </button>
              )}
            </div>
          </>
        ) : (
          <p className="muted">Chọn một khách hàng để xem chi tiết</p>
        )}
      </aside>

      {form && (
        <div className="modal-backdrop" onClick={() => setForm(null)}>
          <div className="modal modal-wide" onClick={(e) => e.stopPropagation()}>
            <h2>{form.customerId ? 'Sửa khách hàng' : 'Thêm khách hàng'}</h2>
            <div className="form-grid">
              {(
                [
                  ['companyName', 'Tên công ty'],
                  ['contactTitle', 'Chức danh'],
                  ['address', 'Địa chỉ'],
                  ['city', 'Thành phố'],
                  ['region', 'Vùng'],
                  ['postalCode', 'Mã bưu điện'],
                  ['phone', 'Điện thoại'],
                  ['fax', 'Fax'],
                ] as const
              ).map(([key, label]) => (
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
