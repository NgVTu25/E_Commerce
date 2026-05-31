import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { fetchProduct } from '../api/products';
import type { Product } from '../types';

export function ProductDetailPage() {
  const { id } = useParams();
  const [product, setProduct] = useState<Product | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const productId = Number(id);
    if (!productId) {
      setError('ID sản phẩm không hợp lệ');
      setLoading(false);
      return;
    }

    let cancelled = false;
    fetchProduct(productId)
      .then((data) => {
        if (!cancelled) setProduct(data);
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
  }, [id]);

  if (loading) return <p className="status">Đang tải...</p>;
  if (error) return <p className="error-banner">{error}</p>;
  if (!product) return <p className="status">Không tìm thấy sản phẩm.</p>;

  const price = Number(product.unitPrice).toLocaleString('vi-VN', {
    style: 'currency',
    currency: 'USD',
  });

  return (
    <section className="page detail-page">
      <Link to="/" className="back-link">
        ← Quay lại danh sách
      </Link>
      <div className="detail-card">
        <h1>{product.productName}</h1>
        {product.quantityPerUnit && (
          <p className="muted">{product.quantityPerUnit}</p>
        )}
        <dl className="detail-list">
          <div>
            <dt>Giá</dt>
            <dd>{price}</dd>
          </div>
          <div>
            <dt>Tồn kho</dt>
            <dd>{product.unitsInStock}</dd>
          </div>
          <div>
            <dt>Danh mục</dt>
            <dd>{product.categoryId}</dd>
          </div>
          <div>
            <dt>Nhà cung cấp</dt>
            <dd>{product.supplierId}</dd>
          </div>
          <div>
            <dt>Trạng thái</dt>
            <dd>{product.discontinued ? 'Ngừng kinh doanh' : 'Đang bán'}</dd>
          </div>
        </dl>
      </div>
    </section>
  );
}
