import { FormEvent, useEffect, useState } from 'react';
import ProductCard from '../components/ProductCard';
import { fetchProducts, searchProducts } from '../api/products';
import type { Product } from '../types';

export default function HomePage() {
  const [products, setProducts] = useState<Product[]>([]);
  const [keyword, setKeyword] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  const load = async (q?: string) => {
    setLoading(true);
    setError('');
    try {
      const data = q?.trim() ? await searchProducts(q.trim()) : await fetchProducts();
      setProducts(data);
    } catch (e) {
      setError(e instanceof Error ? e.message : 'Không tải được sản phẩm');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    load();
  }, []);

  const onSearch = (e: FormEvent) => {
    e.preventDefault();
    load(keyword);
  };

  return (
    <div className="page">
      <h1>Sản phẩm</h1>
      <form className="search-form" onSubmit={onSearch}>
        <input
          className="input"
          value={keyword}
          onChange={(e) => setKeyword(e.target.value)}
          placeholder="Tìm sản phẩm..."
        />
        <button type="submit" className="btn btn-primary">
          Tìm
        </button>
        <button type="button" className="btn" onClick={() => { setKeyword(''); load(); }}>
          Xóa
        </button>
      </form>
      {error && <div className="alert alert-error">{error}</div>}
      {loading && <p className="muted">Đang tải...</p>}
      <div className="product-grid">
        {products.map((p) => (
          <ProductCard key={p.productId ?? p.productName} product={p} />
        ))}
      </div>
    </div>
  );
}
