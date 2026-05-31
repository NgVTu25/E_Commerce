import { useEffect, useState } from 'react';
import { fetchCategories } from '../api/categories';
import type { Category } from '../types';

export default function CategoriesPage() {
  const [categories, setCategories] = useState<Category[]>([]);
  const [error, setError] = useState('');

  useEffect(() => {
    fetchCategories()
      .then(setCategories)
      .catch((e) => setError(e instanceof Error ? e.message : 'Lỗi'));
  }, []);

  return (
    <div className="page">
      <h1>Danh mục</h1>
      {error && <div className="alert alert-error">{error}</div>}
      <ul className="category-list">
        {categories.map((c) => (
          <li key={c.categoryId ?? c.categoryName}>
            <strong>{c.categoryName}</strong>
            {c.description && <p>{c.description}</p>}
          </li>
        ))}
      </ul>
    </div>
  );
}
