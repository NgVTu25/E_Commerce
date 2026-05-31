import { apiRequest } from './client';
import type { Product } from '../types';

export function fetchProducts(): Promise<Product[]> {
  return apiRequest<Product[]>('/api/product', { auth: false });
}

export function fetchProduct(id: number): Promise<Product> {
  return apiRequest<Product>(`/api/product/${id}`, { auth: false });
}

export function searchProducts(keyword: string): Promise<Product[]> {
  const params = new URLSearchParams({ keyword });
  return apiRequest<Product[]>(`/api/product/search?${params}`, { auth: false });
}
