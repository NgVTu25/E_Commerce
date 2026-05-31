import { useEffect, useState } from 'react';
import { fetchProducts, searchProducts } from '../api/products';
import { ProductCard } from '../components/ProductCard';
import type { Product } from '../types';

export function HomePage() {
  const [products, setProducts] = useState<Product[]>([]);
  const [keyword, setKeyword] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    let cancelled = false;
    setLoading(true);
    setError(null);
    fetchProducts()
      .then((data) => {
        if (!cancelled) setProducts(data);
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

  async function handleSearch(event: React.FormEvent) {
    event.preventDefault();
    setLoading(true);
    setError(null);
    try {
      const data = keyword.trim()
        ? await searchProducts(keyword.trim())
        : await fetchProducts();
      setProducts(data);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Lỗi tìm kiếm');
    } finally {
      setLoading(false);
    }
  }

  return (
    <section className="page">
      <div className="page-header">
        <div>
          <h1>Sản phẩm</h1>
          <p className="muted">Duyệt catalog Northwind từ API backend.</p>
        </div>
        <form className="search-form" onSubmit={handleSearch}>
          <input
            type="search"
            placeholder="Tìm theo tên sản phẩm..."
            value={keyword}
            onChange={(e) => setKeyword(e.target.value)}
          />
          <button type="submit" className="btn btn-primary">
            Tìm
          </button>
        </form>
      </div>

      {loading && <p className="status">Đang tải...</p>}
      {error && <p className="error-banner">{error}</p>}

      {!loading && !error && products.length === 0 && (
        <p className="status">Không có sản phẩm nào.</p>
      )}

      <div className="product-grid">
        {products.map((product) => (
          <ProductCard
            key={product.id ?? product.productName}
            product={product}
          />
        ))}
      </div>
    </section>
  );
}
