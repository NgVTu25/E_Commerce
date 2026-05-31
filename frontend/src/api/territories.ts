import { api } from './client';
import type { Territory } from '../types';

export function fetchTerritories() {
  return api<Territory[]>('/api/territory');
}

export function fetchTerritory(id: string) {
  return api<Territory>(`/api/territory/${id}`);
}
