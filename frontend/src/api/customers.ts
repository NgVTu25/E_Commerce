import { api } from './client';
import type { Customer } from '../types';

export function fetchCustomers() {
  return api<Customer[]>('/api/customer');
}

export function fetchCustomer(id: string) {
  return api<Customer>(`/api/customer/${id}`);
}

export function createCustomer(data: Customer) {
  return api<Customer>('/api/customer', { method: 'POST', body: data });
}

export function updateCustomer(id: string, data: Customer) {
  return api<Customer>(`/api/customer/${id}`, { method: 'PUT', body: data });
}

export function deleteCustomer(id: string) {
  return api<void>(`/api/customer/${id}`, { method: 'DELETE' });
}
