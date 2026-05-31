import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { fetchProduct } from '../api/products';
import type { Product } from '../types';

export default function ProductDetailPage() {
  const { id } = useParams();
  const [product, setProduct] = useState<Product | null>(null);
  const [error, setError] = useState('');

  useEffect(() => {
    if (!id) return;
    fetchProduct(Number(id))
      .then(setProduct)
      .catch((e) => setError(e instanceof Error ? e.message : 'Lỗi'));
  }, [id]);

  if (error) return <div className="alert alert-error">{error}</div>;
  if (!product) return <p className="muted">Đang tải...</p>;

  return (
    <div className="page product-detail">
      <Link to="/">← Quay lại</Link>
      <h1>{product.productName}</h1>
      <p>
        Giá:{' '}
        {Number(product.unitPrice).toLocaleString('vi-VN', {
          style: 'currency',
          currency: 'USD',
        })}
      </p>
      <p>Tồn kho: {product.unitsInStock}</p>
      <p>Danh mục ID: {product.categoryId}</p>
      <p>Nhà cung cấp ID: {product.supplierId}</p>
      <p>{product.discontinued ? 'Ngừng kinh doanh' : 'Đang bán'}</p>
    </div>
  );
}
