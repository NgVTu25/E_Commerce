import { api } from './client';
import type { Category } from '../types';

export function fetchCategories() {
  return api<Category[]>('/api/category', { auth: false });
}
