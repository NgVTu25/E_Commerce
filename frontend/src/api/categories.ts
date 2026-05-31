import { apiRequest } from './client';
import type { Category } from '../types';

export function fetchCategories(): Promise<Category[]> {
  return apiRequest<Category[]>('/api/category', { auth: false });
}
