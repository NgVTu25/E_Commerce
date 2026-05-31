import { Link } from 'react-router-dom';
import type { Product } from '../types';

interface ProductCardProps {
  product: Product;
}

export function ProductCard({ product }: ProductCardProps) {
  const price = Number(product.unitPrice).toLocaleString('vi-VN', {
    style: 'currency',
    currency: 'USD',
  });

  return (
    <article className="product-card">
      <div className="product-card-body">
        <h3>
          {product.id ? (
            <Link to={`/products/${product.id}`}>{product.productName}</Link>
          ) : (
            product.productName
          )}
        </h3>
        {product.quantityPerUnit && (
          <p className="muted">{product.quantityPerUnit}</p>
        )}
        <p className="price">{price}</p>
        <p className="stock">
          Còn <strong>{product.unitsInStock}</strong> trong kho
        </p>
      </div>
    </article>
  );
}
