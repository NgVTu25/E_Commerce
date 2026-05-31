import { useEffect, useState } from 'react';
import { fetchCategories } from '../api/categories';
import type { Category } from '../types';

export function CategoriesPage() {
  const [categories, setCategories] = useState<Category[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    let cancelled = false;
    fetchCategories()
      .then((data) => {
        if (!cancelled) setCategories(data);
      })
      .catch((err: Error) => {
        if (!cancelled) setError(err.message);
      })
      .finally(() => {
        if (!cancelled) setLoading(false);
      });
    return () => {
      cancelled = true;
    };
  }, []);

  return (
    <section className="page">
      <div className="page-header">
        <div>
          <h1>Danh mục</h1>
          <p className="muted">Danh sách category từ API.</p>
        </div>
      </div>

      {loading && <p className="status">Đang tải...</p>}
      {error && <p className="error-banner">{error}</p>}

      <ul className="category-list">
        {categories.map((cat) => (
          <li key={cat.id ?? cat.categoryName} className="category-item">
            <h3>{cat.categoryName}</h3>
            {cat.description && <p>{cat.description}</p>}
          </li>
        ))}
      </ul>
    </section>
  );
}
