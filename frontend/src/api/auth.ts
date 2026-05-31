import { api } from './client';
import type { AuthResponse, LoginRequest, RegisterRequest } from '../types';

export function login(data: LoginRequest) {
  return api<AuthResponse>('/api/auth/login', { method: 'POST', body: data, auth: false });
}

export function register(data: RegisterRequest) {
  return api<AuthResponse>('/api/auth/register', { method: 'POST', body: data, auth: false });
}
