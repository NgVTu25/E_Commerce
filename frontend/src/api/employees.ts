import { api } from './client';
import type { Employee } from '../types';

export function fetchEmployees() {
  return api<Employee[]>('/api/employee');
}

export function fetchEmployee(id: number) {
  return api<Employee>(`/api/employee/${id}`);
}
