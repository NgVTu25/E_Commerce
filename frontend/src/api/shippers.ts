import { api } from './client';
import type { Shipper } from '../types';

export function fetchShippers() {
  return api<Shipper[]>('/api/shipper');
}

export function createShipper(data: Shipper) {
  return api<Shipper>('/api/shipper', { method: 'POST', body: data });
}
