import { apiRequest } from './client';
import type { AuthResponse, LoginRequest, RegisterRequest } from '../types';

export function login(data: LoginRequest): Promise<AuthResponse> {
  return apiRequest<AuthResponse>('/api/auth/login', {
    method: 'POST',
    body: JSON.stringify(data),
    auth: false,
  });
}

export function register(data: RegisterRequest): Promise<AuthResponse> {
  return apiRequest<AuthResponse>('/api/auth/register', {
    method: 'POST',
    body: JSON.stringify(data),
    auth: false,
  });
}
