import { useEffect, useMemo, useState } from 'react';
import PageHeader from '../../components/PageHeader';
import SearchBar from '../../components/SearchBar';
import { createShipper, fetchShippers } from '../../api/shippers';
import type { Shipper } from '../../types';

const empty: Shipper = {
  companyName: '',
  phone: '0123456789',
};

export default function ManageShippersPage() {
  const [items, setItems] = useState<Shipper[]>([]);
  const [search, setSearch] = useState('');
  const [form, setForm] = useState<Shipper | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [saving, setSaving] = useState(false);

  const load = async () => {
    setLoading(true);
    try {
      setItems(await fetchShippers());
    } catch (e) {
      setError(e instanceof Error ? e.message : 'Lỗi tải shipper');
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
        s.phone.includes(q) ||
        String(s.shipperId ?? '').includes(q),
    );
  }, [items, search]);

  const save = async () => {
    if (!form) return;
    setSaving(true);
    setError('');
    try {
      await createShipper(form);
      setForm(null);
      await load();
    } catch (e) {
      setError(e instanceof Error ? e.message : 'Lưu thất bại');
    } finally {
      setSaving(false);
    }
  };

  return (
    <div>
      <PageHeader
        title="Shipper"
        subtitle="Đơn vị vận chuyển — xem danh sách và thêm mới"
        actions={
          <button type="button" className="btn btn-primary" onClick={() => setForm({ ...empty })}>
            + Thêm shipper
          </button>
        }
      />
      <SearchBar value={search} onChange={setSearch} placeholder="Tên, SĐT, ID..." />
      {error && <div className="alert alert-error">{error}</div>}
      {loading ? (
        <p className="muted">Đang tải...</p>
      ) : (
        <div className="card-grid">
          {filtered.map((s) => (
            <article key={s.shipperId ?? s.phone} className="info-card">
              <span className="info-card-id">#{s.shipperId}</span>
              <h3>{s.companyName}</h3>
              <p>{s.phone}</p>
            </article>
          ))}
        </div>
      )}

      {form && (
        <div className="modal-backdrop" onClick={() => setForm(null)}>
          <div className="modal" onClick={(e) => e.stopPropagation()}>
            <h2>Thêm shipper</h2>
            <label>
              Tên công ty
              <input
                className="input"
                value={form.companyName}
                onChange={(e) => setForm({ ...form, companyName: e.target.value })}
              />
            </label>
            <label>
              Điện thoại
              <input
                className="input"
                value={form.phone}
                onChange={(e) => setForm({ ...form, phone: e.target.value })}
              />
            </label>
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
