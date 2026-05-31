import { api } from './client';
import type { Supplier } from '../types';

export function fetchSuppliers() {
  return api<Supplier[]>('/api/supplier');
}

export function fetchSupplier(id: number) {
  return api<Supplier>(`/api/supplier/${id}`);
}

export function createSupplier(data: Supplier) {
  return api<Supplier>('/api/supplier', { method: 'POST', body: data });
}

export function updateSupplier(id: number, data: Supplier) {
  return api<Supplier>(`/api/supplier/${id}`, { method: 'PUT', body: data });
}

export function deleteSupplier(id: number) {
  return api<string>(`/api/supplier/${id}`, { method: 'DELETE' });
}
