import { useEffect, useMemo, useState } from 'react';
import PageHeader from '../../components/PageHeader';
import SearchBar from '../../components/SearchBar';
import { fetchEmployee, fetchEmployees } from '../../api/employees';
import type { Employee } from '../../types';

export default function ManageEmployeesPage() {
  const [items, setItems] = useState<Employee[]>([]);
  const [search, setSearch] = useState('');
  const [selectedId, setSelectedId] = useState<number | null>(null);
  const [detail, setDetail] = useState<Employee | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    fetchEmployees()
      .then(setItems)
      .catch((e) => setError(e instanceof Error ? e.message : 'Lỗi tải nhân viên'))
      .finally(() => setLoading(false));
  }, []);

  useEffect(() => {
    if (selectedId == null) {
      setDetail(null);
      return;
    }
    fetchEmployee(selectedId)
      .then(setDetail)
      .catch((e) => setError(e instanceof Error ? e.message : 'Lỗi tải chi tiết'));
  }, [selectedId]);

  const filtered = useMemo(() => {
    const q = search.trim().toLowerCase();
    if (!q) return items;
    return items.filter(
      (e) =>
        `${e.firstName} ${e.lastName}`.toLowerCase().includes(q) ||
        (e.title ?? '').toLowerCase().includes(q) ||
        String(e.employeeId).includes(q),
    );
  }, [items, search]);

  return (
    <div className="split-layout">
      <div className="split-main">
        <PageHeader title="Nhân viên" subtitle="Xem danh sách và territory gán cho từng người" />
        <SearchBar value={search} onChange={setSearch} placeholder="Tên, chức danh, ID..." />
        {error && <div className="alert alert-error">{error}</div>}
        {loading ? (
          <p className="muted">Đang tải...</p>
        ) : (
          <div className="table-wrap">
            <table className="data-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Họ tên</th>
                  <th>Chức danh</th>
                  <th>Thành phố</th>
                </tr>
              </thead>
              <tbody>
                {filtered.map((e) => (
                  <tr
                    key={e.employeeId}
                    className={selectedId === e.employeeId ? 'row-selected' : ''}
                    onClick={() => setSelectedId(e.employeeId)}
                  >
                    <td>{e.employeeId}</td>
                    <td>
                      {e.firstName} {e.lastName}
                    </td>
                    <td>{e.title || '—'}</td>
                    <td>{e.city || '—'}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>

      <aside className="detail-panel">
        {detail ? (
          <>
            <h3>
              {detail.firstName} {detail.lastName}
            </h3>
            <dl className="detail-list">
              <dt>ID</dt>
              <dd>{detail.employeeId}</dd>
              <dt>Chức danh</dt>
              <dd>{detail.title || '—'}</dd>
              <dt>Điện thoại</dt>
              <dd>{detail.homePhone || '—'}</dd>
              <dt>Quốc gia</dt>
              <dd>{detail.country || '—'}</dd>
              <dt>Báo cáo cho (ID)</dt>
              <dd>{detail.reportsTo ?? '—'}</dd>
            </dl>
            <h4>Territories</h4>
            {detail.territoryIds && detail.territoryIds.length > 0 ? (
              <ul className="tag-list">
                {detail.territoryIds.map((t) => (
                  <li key={t} className="tag">
                    {t}
                  </li>
                ))}
              </ul>
            ) : (
              <p className="muted">Chưa gán territory</p>
            )}
          </>
        ) : (
          <p className="muted">Chọn nhân viên để xem chi tiết</p>
        )}
      </aside>
    </div>
  );
}
