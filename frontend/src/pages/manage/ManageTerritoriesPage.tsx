import { useEffect, useMemo, useState } from 'react';
import PageHeader from '../../components/PageHeader';
import SearchBar from '../../components/SearchBar';
import { fetchTerritories, fetchTerritory } from '../../api/territories';
import type { Territory } from '../../types';

export default function ManageTerritoriesPage() {
  const [items, setItems] = useState<Territory[]>([]);
  const [search, setSearch] = useState('');
  const [selectedId, setSelectedId] = useState<string | null>(null);
  const [detail, setDetail] = useState<Territory | null>(null);
  const [loading, setLoading] = useState(true);
  const [detailLoading, setDetailLoading] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    fetchTerritories()
      .then(setItems)
      .catch((e) => setError(e instanceof Error ? e.message : 'Lỗi tải territories'))
      .finally(() => setLoading(false));
  }, []);

  useEffect(() => {
    if (!selectedId) {
      setDetail(null);
      return;
    }
    setDetailLoading(true);
    fetchTerritory(selectedId)
      .then(setDetail)
      .catch((e) => setError(e instanceof Error ? e.message : 'Lỗi tải chi tiết'))
      .finally(() => setDetailLoading(false));
  }, [selectedId]);

  const filtered = useMemo(() => {
    const q = search.trim().toLowerCase();
    if (!q) return items;
    return items.filter(
      (t) =>
        t.territoryId.toLowerCase().includes(q) ||
        t.territoryDescription.toLowerCase().includes(q) ||
        (t.regionDescription ?? '').toLowerCase().includes(q),
    );
  }, [items, search]);

  return (
    <div className="split-layout">
      <div className="split-main">
        <PageHeader
          title="Territories"
          subtitle="Xem vùng bán hàng và nhân viên phụ trách"
        />
        <SearchBar
          value={search}
          onChange={setSearch}
          placeholder="Mã territory, mô tả, region..."
        />
        {error && <div className="alert alert-error">{error}</div>}
        {loading ? (
          <p className="muted">Đang tải...</p>
        ) : (
          <div className="territory-grid">
            {filtered.map((t) => (
              <button
                key={t.territoryId}
                type="button"
                className={
                  selectedId === t.territoryId ? 'territory-card selected' : 'territory-card'
                }
                onClick={() => setSelectedId(t.territoryId)}
              >
                <strong>{t.territoryId}</strong>
                <span>{t.territoryDescription}</span>
                <small>{t.regionDescription || `Region ${t.regionId ?? '—'}`}</small>
              </button>
            ))}
          </div>
        )}
      </div>

      <aside className="detail-panel">
        {detailLoading && <p className="muted">Đang tải chi tiết...</p>}
        {!detailLoading && detail ? (
          <>
            <h3>{detail.territoryId}</h3>
            <p>{detail.territoryDescription}</p>
            <p className="muted">
              Region: {detail.regionDescription || detail.regionId || '—'}
            </p>
            <h4>Nhân viên trong territory</h4>
            {detail.employees && detail.employees.length > 0 ? (
              <ul className="employee-mini-list">
                {detail.employees.map((e) => (
                  <li key={e.employeeId}>
                    <strong>{e.fullName}</strong>
                    <span>
                      {e.title || '—'} · {e.city || '—'}
                    </span>
                  </li>
                ))}
              </ul>
            ) : (
              <p className="muted">Không có nhân viên gán</p>
            )}
          </>
        ) : !detailLoading ? (
          <p className="muted">Chọn một territory để kiểm tra nhân viên</p>
        ) : null}
      </aside>
    </div>
  );
}
