import { api } from './client';
import type { Product } from '../types';

export function fetchProducts() {
  return api<Product[]>('/api/product', { auth: false });
}

export function fetchProduct(id: number) {
  return api<Product>(`/api/product/${id}`, { auth: false });
}

export function searchProducts(keyword: string) {
  return api<Product[]>(`/api/product/search?keyword=${encodeURIComponent(keyword)}`, {
    auth: false,
  });
}

export function createProduct(data: Product) {
  return api<Product>('/api/product', { method: 'POST', body: data });
}

export function updateProduct(id: number, data: Product) {
  return api<Product>(`/api/product/${id}`, { method: 'PUT', body: data });
}

export function deleteProduct(id: number) {
  return api<void>(`/api/product/${id}`, { method: 'DELETE' });
}
