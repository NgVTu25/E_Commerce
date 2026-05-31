import { Link } from 'react-router-dom';
import type { Product } from '../types';

type Props = {
  product: Product;
};

export default function ProductCard({ product }: Props) {
  const id = product.productId;
  const price = Number(product.unitPrice).toLocaleString('vi-VN', {
    style: 'currency',
    currency: 'USD',
  });

  const body = (
    <>
      <h3>{product.productName}</h3>
      <p className="product-price">{price}</p>
      <p className="product-stock">Còn {product.unitsInStock} sp</p>
    </>
  );

  if (id) {
    return (
      <Link to={`/products/${id}`} className="product-card">
        {body}
      </Link>
    );
  }

  return <article className="product-card">{body}</article>;
}
